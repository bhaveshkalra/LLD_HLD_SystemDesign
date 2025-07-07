import java.util.ArrayList;
import java.util.List;

// Enums
enum VehicleType { TwoWheeler, FourWheeler }

// Vehicle class
class Vehicle {
    int vehicleNo;
    VehicleType vehicleType;

    Vehicle(int vehicleNo, VehicleType vehicleType) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
    }
}

// ParkingSpot class
class ParkingSpot {
    int id;
    boolean isEmpty;
    Vehicle vehicle;
    int price;

    ParkingSpot(int id, int price) {
        this.id = id;
        this.isEmpty = true;
        this.vehicle = null;
        this.price = price;
    }

    int getPrice() {
        return price;
    } 

    void parkVehicle(Vehicle v) {
        this.vehicle = v;
        this.isEmpty = false;
    }

    void removeVehicle() {
        this.vehicle = null;
        this.isEmpty = true;
    }
}

//2-wheeler ParkingSpot
class TwoWheelerParkingSpot extends ParkingSpot {
    TwoWheelerParkingSpot(int id, int price) {
        super(id, price);
    }

    @Override
    int getPrice() {
        return 10;
    }
}

//4-wheeler ParkingSpot
class FourWheelerParkingSpot extends ParkingSpot {
    FourWheelerParkingSpot(int id, int price) {
        super(id, price);
    }

    @Override
    int getPrice() {
        return 20;
    }
}

// ParkingSpotManager class
abstract class ParkingSpotManager {
    List<ParkingSpot> spots;

    ParkingSpotManager(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    abstract ParkingSpot findParkingSpace();//Parking stategy

    void parkVehicle(Vehicle v) {
        ParkingSpot spot = findParkingSpace();
        spot.parkVehicle(v);
    }

    void removeVehicle(Vehicle v) {
        for (ParkingSpot spot : spots) {
            if (spot.vehicle != null && spot.vehicle.equals(v)) {
                spot.removeVehicle();
                break;
            }
        }
    }
}

// TwoWheelerManager and FourWheelerManager classes
class TwoWheelerManager extends ParkingSpotManager {
    TwoWheelerManager(List<ParkingSpot> spots) {
        super(spots);
    }

    @Override
    ParkingSpot findParkingSpace() {
        // Implementation to find nearest parking spot for Two Wheelers
        //has a parking strategy like nearest entrance or near elevataor and entrance
        return null;
    }
}

class FourWheelerManager extends ParkingSpotManager {
    FourWheelerManager(List<ParkingSpot> spots) {
        super(spots);
    }

    @Override
    ParkingSpot findParkingSpace() {
        // Implementation to find nearest parking spot for Four Wheelers
        return null;
    }
}

// ParkingSpotManagerFactory class
class ParkingSpotManagerFactory {
    ParkingSpotManager getParkingSpotManager(VehicleType vehicleType, List<ParkingSpot> spots) {
        if (vehicleType == VehicleType.TwoWheeler)
            return new TwoWheelerManager(spots);
        else if (vehicleType == VehicleType.FourWheeler)
            return new FourWheelerManager(spots);
        else
            return null; // Handle error
    }
}

// Ticket class
class Ticket {
    long entryTime;
    ParkingSpot parkingSpot;
    Vehicle vehicle;

    Ticket(long entryTime, ParkingSpot parkingSpot, Vehicle vehicle) {
        this.entryTime = entryTime;
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
    }
}

// EntranceGate class
class EntranceGate {
    ParkingSpotManagerFactory factory;

    EntranceGate(ParkingSpotManagerFactory factory) {
        this.factory = factory;
    }

    ParkingSpot findParkingSpace(VehicleType vehicleType, List<ParkingSpot> spots) {
        ParkingSpotManager manager = factory.getParkingSpotManager(vehicleType, spots);
        return manager.findParkingSpace();
    }

    Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        // Implementation to generate ticket
        return null;
    }
}

// ExitGate class
class ExitGate {
    ParkingSpotManagerFactory factory;

    ExitGate(ParkingSpotManagerFactory factory) {
        this.factory = factory;
    }

    void removeVehicle(Ticket ticket) {
        ParkingSpotManager manager = factory.getParkingSpotManager(ticket.vehicle.vehicleType, new ArrayList<>());
        manager.removeVehicle(ticket.vehicle);
    }
}


public class ParkingLotSystem {
    public static void main(String[] args) {
        // Initialize parking spots
        List<ParkingSpot> spots = new ArrayList<>();
        for (int i = 1; i <= 100; ++i) {
            if (i <= 50)
                spots.add(new ParkingSpot(i, 10));
            else
                spots.add(new ParkingSpot(i, 20));
        }

        // Create ParkingSpotManagerFactory
        ParkingSpotManagerFactory factory = new ParkingSpotManagerFactory();

        // Create EntranceGate and ExitGate objects
        EntranceGate entranceGate = new EntranceGate(factory);
        ExitGate exitGate = new ExitGate(factory);

        // Example usage
        Vehicle vehicle = new Vehicle(123, VehicleType.TwoWheeler);
        ParkingSpot spot = entranceGate.findParkingSpace(vehicle.vehicleType, spots);
        Ticket ticket = entranceGate.generateTicket(vehicle, spot);

        // Vehicle leaves
        exitGate.removeVehicle(ticket);
    }
}


interface ParkingStrategy {
    ParkingSpot chooseSpot(List<ParkingSpot> candidateSpots);
}

class NearestSpotStrategy implements ParkingStrategy {
    public ParkingSpot chooseSpot(List<ParkingSpot> candidateSpots) {
        return candidateSpots.stream()
                .min(Comparator.comparingInt(spot -> spot.distanceFromEntry))
                .orElse(null);
    }
}

class ElevatorNearbyStrategy implements ParkingStrategy {
    public ParkingSpot chooseSpot(List<ParkingSpot> candidateSpots) {
        return candidateSpots.stream()
                .min(Comparator.comparingInt(spot -> spot.distanceFromElevator))
                .orElse(null);
    }
}

