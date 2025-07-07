package LowLevelDesign.LLD_HLD;

import java.time.LocalDate;
import java.util.List;

class RoomType {
    String typeId;
    String name; // e.g. "Deluxe", "Suite"
    int totalRooms;
    double price;
}

class RoomInventory {
    String hotelId;
    String typeId;
    LocalDate date;
    int availableRooms;

    public synchronized boolean reserve(int count) {
        if (availableRooms >= count) {
            availableRooms -= count;
            return true;
        }
        return false;
    }

    public synchronized void release(int count) {
        availableRooms += count;
    }
}


class Hotel {
    String hotelId;
    String name;
    String city;
    List<RoomType> roomTypes;
}


class User {
    String userId;
    String name;
    String email;
    List<Booking> bookings;
}

class Booking {
    String bookingId;
    User user;
    Hotel hotel;
    RoomType roomType;
    int roomCount;
    LocalDate fromDate;
    LocalDate toDate;
    BookingStatus status;
}
enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED
}

class BookingService {

    public Booking bookRoom(User user, String hotelId, String roomTypeId,
                            LocalDate from, LocalDate to, int roomCount) {

        // Step 1: Lock and check availability
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            RoomInventory inventory = DB.getInventory(hotelId, roomTypeId, date);
            synchronized (inventory) {
                if (!inventory.reserve(roomCount)) {
                    // rollback all previous dates
                    releaseRooms(hotelId, roomTypeId, from, date.minusDays(1), roomCount);
                    throw new RuntimeException("Not enough rooms available");
                }
            }
        }

        // Step 2: Create Booking
        Booking booking = new Booking(UUID.randomUUID().toString(), ...);
        DB.save(booking);

        // Step 3: Return booking, payment logic follows
        return booking;
    }

    private void releaseRooms(...) {
        // Loop and call inventory.release(roomCount)
    }
}


List<Hotel> searchHotels(String city, LocalDate from, LocalDate to, RoomType type) {
    // 1. Filter hotels by city
    // 2. For each hotel, check RoomInventory for all dates
    // 3. Return those with availability
}


public class HotelBookingSystem {
    
}
