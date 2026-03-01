from enum import Enum
from datetime import datetime
import uuid
import random


class SeatStatus(Enum):
    AVAILABLE = "AVAILABLE"
    LOCKED = "LOCKED"
    BOOKED = "BOOKED"

class SeatType(Enum):
    SILVER = 1.0
    GOLD = 1.5
    PLATINUM = 2.0

class PaymentStatus(Enum):
    PENDING = "PENDING"
    SUCCESS = "SUCCESS"
    FAILED = "FAILED"

class BookingStatus(Enum):
    CREATED = "CREATED"
    CONFIRMED = "CONFIRMED"
    CANCELLED = "CANCELLED"


class User:
    def __init__(self, user_id, name):
        self.user_id = user_id
        self.name = name

class Movie:
    def __init__(self, movie_id, name, duration):
        self.movie_id = movie_id
        self.name = name
        self.duration = duration

class Seat:
    def __init__(self, seat_id, base_price, seat_type: SeatType):
        self.seat_id = seat_id
        self.base_price = base_price
        self.seat_type = seat_type
        self.status = SeatStatus.AVAILABLE

    def get_price(self):
        return self.base_price * self.seat_type.value

class Show:
    def __init__(self, show_id, movie, start_time):
        self.show_id = show_id
        self.movie = movie
        self.start_time = start_time
        self.seats = {}

    def add_seat(self, seat):
        self.seats[seat.seat_id] = seat

class Theatre:
    def __init__(self, theatre_id, name):
        self.theatre_id = theatre_id
        self.name = name
        self.shows = {}

    def add_show(self, show):
        self.shows[show.show_id] = show


class Payment:
    def __init__(self, amount):
        self.payment_id = str(uuid.uuid4())
        self.amount = amount
        self.status = PaymentStatus.PENDING
        self.timestamp = datetime.now()

    def process_payment(self):
        # Simulating random success/failure
        if random.choice([True, False]):
            self.status = PaymentStatus.SUCCESS
        else:
            self.status = PaymentStatus.FAILED
        return self.status


class Booking:
    def __init__(self, user, show, seats):
        self.booking_id = str(uuid.uuid4())
        self.user = user
        self.show = show
        self.seats = seats
        self.total_amount = sum(seat.get_price() for seat in seats)
        self.status = BookingStatus.CREATED
        self.payment = None
        self.timestamp = datetime.now()


# ---------------- MANAGER ----------------
class BookingManager:
    def __init__(self):
        self.theatres = {}
        self.bookings = {}

    def add_theatre(self, theatre):
        self.theatres[theatre.theatre_id] = theatre

    def show_available_seats(self, theatre_id, show_id):
        theatre = self.theatres[theatre_id]
        show = theatre.shows[show_id]
        for seat in show.seats.values():
            if seat.status == SeatStatus.AVAILABLE:
                print(f"{seat.seat_id} | {seat.seat_type.name} | ₹{seat.get_price()}")

    def create_booking(self, user, theatre_id, show_id, seat_ids):
        theatre = self.theatres.get(theatre_id)
        show = theatre.shows.get(show_id)
        selected_seats = []
        # Lock seats
        for seat_id in seat_ids:
            seat = show.seats.get(seat_id)
            if seat.status != SeatStatus.AVAILABLE:
                raise Exception(f"Seat {seat_id} not available")
            seat.status = SeatStatus.LOCKED
            selected_seats.append(seat)
        booking = Booking(user, show, selected_seats)
        self.bookings[booking.booking_id] = booking
        return booking

    def make_payment(self, booking: Booking):
        payment = Payment(booking.total_amount)
        booking.payment = payment
        status = payment.process_payment()
        if status == PaymentStatus.SUCCESS:
            booking.status = BookingStatus.CONFIRMED
            for seat in booking.seats:
                seat.status = SeatStatus.BOOKED
        else:
            booking.status = BookingStatus.CANCELLED
            for seat in booking.seats:
                seat.status = SeatStatus.AVAILABLE
        return status


# ---------------- MAIN ----------------

if __name__ == "__main__":
    manager = BookingManager()
    movie = Movie("m1", "Inception", 150)
    theatre = Theatre("t1", "PVR Delhi")
    show = Show("s1", movie, "7 PM")

    # Add seats with types
    for i in range(50):
        show.add_seat(Seat("Si" + str(i), 200, SeatType.SILVER))
    for i in range(20):
        show.add_seat(Seat("Go" + str(i), 300, SeatType.GOLD))
    for i in range(10):
        show.add_seat(Seat("Pl" + str(i), 400, SeatType.PLATINUM))

    theatre.add_show(show)
    manager.add_theatre(theatre)
    user = User("u1", "Bhavesh")

    print("\nAvailable Seats:")
    manager.show_available_seats("t1", "s1")

    print("\nCreating Booking...")
    booking = manager.create_booking(user, "t1", "s1", ["Go1", "Go2"])

    print("Total Amount:", booking.total_amount)

    print("\nProcessing Payment...")
    status = manager.make_payment(booking)

    print("Payment Status:", status.value)
    print("Booking Status:", booking.status.value)

    print("\nAvailable Seats After Booking:")
    manager.show_available_seats("t1", "s1")

    
"""
Q: What about concurrency?
    Need seat-level locking
    Optimistic locking
    Redis distributed lock

Q: How to scale?
    Shard by theatre
    Cache seat availability in Redis
    Event driven booking confirmation

Q: Prevent double booking? 
    DB row-level locking
    Optimistic locking with version field
    Redis distributed lock
"""