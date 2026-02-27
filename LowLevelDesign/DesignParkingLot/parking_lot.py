from enum import Enum
from abc import ABC, abstractmethod
import time

class VehicleType(Enum):
    TWO_WHEELER = "TwoWheeler"
    FOUR_WHEELER = "FourWheeler"

class Vehicle:
    def __init__(self, vehicle_no: int, vehicle_type: VehicleType):
        self.vehicle_no = vehicle_no
        self.vehicle_type = vehicle_type

    def __repr__(self):
        return f"<Vehicle {self.vehicle_no} {self.vehicle_type.value}>"

class ParkingSpot:
    def __init__(self, spot_id: int, price: int):
        self.id = spot_id
        self.price = price
        self.vehicle = None
        self.is_empty = True

        # Optional fields for strategies
        self.distance_from_entry = spot_id
        self.distance_from_elevator = 100 - spot_id

    def get_price(self):
        return self.price

    def park_vehicle(self, vehicle: Vehicle):
        self.vehicle = vehicle
        self.is_empty = False

    def remove_vehicle(self):
        self.vehicle = None
        self.is_empty = True

    def __repr__(self):
        return f"<Spot {self.id}>"


class TwoWheelerParkingSpot(ParkingSpot):
    def get_price(self):
        return 10

class FourWheelerParkingSpot(ParkingSpot):
    def get_price(self):
        return 20


# Parking Strategy (Strategy Pattern)
class ParkingStrategy(ABC):#interafce
    @abstractmethod
    def choose_spot(self, candidate_spots):
        pass

class NearestSpotStrategy(ParkingStrategy):
    def choose_spot(self, candidate_spots):
        if not candidate_spots:
            return None
        return min(candidate_spots, key=lambda s: s.distance_from_entry)

class ElevatorNearbyStrategy(ParkingStrategy):
    def choose_spot(self, candidate_spots):
        if not candidate_spots:
            return None
        return min(candidate_spots, key=lambda s: s.distance_from_elevator)


# ParkingSpotManager
class ParkingSpotManager(ABC):#abstract class
    def __init__(self, spots, strategy: ParkingStrategy = None):
        self.spots = spots
        self.strategy = strategy or NearestSpotStrategy()

    @abstractmethod
    def find_parking_space(self):
        pass

    def park_vehicle(self, vehicle: Vehicle):
        spot = self.find_parking_space()
        if spot:
            spot.park_vehicle(vehicle)
        return spot

    def remove_vehicle(self, vehicle: Vehicle):
        for spot in self.spots:
            if spot.vehicle == vehicle:
                spot.remove_vehicle()
                return


class TwoWheelerManager(ParkingSpotManager):
    def find_parking_space(self):
        candidates = [
            s for s in self.spots
            if isinstance(s, TwoWheelerParkingSpot) and s.is_empty
        ]
        return self.strategy.choose_spot(candidates)

class FourWheelerManager(ParkingSpotManager):
    def find_parking_space(self):
        candidates = [
            s for s in self.spots
            if isinstance(s, FourWheelerParkingSpot) and s.is_empty
        ]
        return self.strategy.choose_spot(candidates)


# Factory Pattern
class ParkingSpotManagerFactory:
    def get_parking_spot_manager(self, vehicle_type, spots):
        if vehicle_type == VehicleType.TWO_WHEELER:
            return TwoWheelerManager(spots)
        elif vehicle_type == VehicleType.FOUR_WHEELER:
            return FourWheelerManager(spots)
        return None


class Ticket:
    def __init__(self, entry_time, parking_spot, vehicle):
        self.entry_time = entry_time
        self.parking_spot = parking_spot
        self.vehicle = vehicle

    def __repr__(self):
        return f"<Ticket {self.vehicle.vehicle_no} Spot:{self.parking_spot.id}>"


class EntranceGate:
    def __init__(self, factory: ParkingSpotManagerFactory):
        self.factory = factory

    def process_vehicle(self, vehicle, spots):
        manager = self.factory.get_parking_spot_manager(
            vehicle.vehicle_type,
            spots
        )
        spot = manager.park_vehicle(vehicle)

        if not spot:
            print("No space available")
            return None

        return Ticket(time.time(), spot, vehicle)


class ExitGate:
    def __init__(self, factory: ParkingSpotManagerFactory, spots):
        self.factory = factory
        self.spots = spots

    def remove_vehicle(self, ticket: Ticket):
        manager = self.factory.get_parking_spot_manager(
            ticket.vehicle.vehicle_type,
            self.spots
        )
        manager.remove_vehicle(ticket.vehicle)


if __name__ == "__main__":

    spots = []
    for i in range(1, 101):
        if i <= 50:
            spots.append(TwoWheelerParkingSpot(i, 10))
        else:
            spots.append(FourWheelerParkingSpot(i, 20))

    factory = ParkingSpotManagerFactory()

    entrance_gate = EntranceGate(factory)
    exit_gate = ExitGate(factory, spots)

    vehicle = Vehicle(123, VehicleType.TWO_WHEELER)

    ticket = entrance_gate.process_vehicle(vehicle, spots)

    """
    EntranceGate
         ↓
    manager.park_vehicle(vehicle)
         ↓
    find_parking_space()   ← subclass decides
         ↓
    strategy.choose_spot()
         ↓
    spot.park_vehicle()
        ↓
    Ticket Generated
        ↓
    ExitGate.remove_vehicle(ticket)    
    """
    print("Ticket Generated:", ticket)

    # Vehicle leaves
    exit_gate.remove_vehicle(ticket)

    print("Vehicle exited")