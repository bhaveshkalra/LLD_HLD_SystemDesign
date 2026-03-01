from enum import Enum
from dataclasses import dataclass
from typing import List, Optional
import heapq


class Direction(Enum):
    UP = 1
    DOWN = -1

class ElevatorState(Enum):
    IDLE = 0
    MOVING = 1
    DOOR_OPEN = 2

@dataclass
class Request:
    floor: int
    direction: Direction


class ElevatorButton:
    def __init__(self, floor: int):
        self.floor = floor
        self.is_pressed = False

    def press(self, elevator):
        self.is_pressed = True
        elevator.add_stop(self.floor)


class FloorButton:
    def __init__(self, direction: Direction, floor_number: int, system):
        self.direction = direction
        self.floor_number = floor_number
        self.system = system
        self.is_pressed = False

    def press(self):
        self.is_pressed = True
        print(f"Floor {self.floor_number} {self.direction.name} button pressed")
        self.system.handle_external_request(Request(self.floor_number, self.direction))


class Floor:
    def __init__(self, floor_number: int, system):
        self.floor_number = floor_number
        self.up_button = FloorButton(Direction.UP, floor_number, system)
        self.down_button = FloorButton(Direction.DOWN, floor_number, system)


# Elevator (SCAN algorithm)
class Elevator:
    def __init__(self, eid: int, max_floor: int):
        self.id = eid
        self.current_floor = 0
        self.direction = Direction.UP
        self.state = ElevatorState.IDLE
        self.up_stops = []      # min heap
        self.down_stops = []    # max heap (store negative)
        # Internal buttons
        self.internal_buttons = {i: ElevatorButton(i) for i in range(max_floor + 1)}

    def press_inside(self, floor: int):
        self.internal_buttons[floor].press(self)

    def add_stop(self, floor: int):
        if floor > self.current_floor:
            heapq.heappush(self.up_stops, floor)
        elif floor < self.current_floor:
            heapq.heappush(self.down_stops, -floor)
        else:
            self.open_doors()

    def step(self):
        if not self.up_stops and not self.down_stops:
            self.state = ElevatorState.IDLE
            return

        self.state = ElevatorState.MOVING

        if self.direction == Direction.UP:
            if self.up_stops:
                target = self.up_stops[0]
                self.move_towards(target)
            else:
                self.direction = Direction.DOWN

        if self.direction == Direction.DOWN:
            if self.down_stops:
                target = -self.down_stops[0]
                self.move_towards(target)
            else:
                self.direction = Direction.UP

    def move_towards(self, target: int):
        if self.current_floor < target:
            self.current_floor += 1
        elif self.current_floor > target:
            self.current_floor -= 1
        if self.current_floor == target:
            self.open_doors()
            self.remove_stop(target)

    def remove_stop(self, floor: int):
        if floor > self.current_floor:
            heapq.heappop(self.up_stops)
        else:
            heapq.heappop(self.down_stops)

    def open_doors(self):
        self.state = ElevatorState.DOOR_OPEN
        print(f"Elevator {self.id} opened doors at floor {self.current_floor}")

    def is_idle(self):
        return self.state == ElevatorState.IDLE

    def distance_to(self, floor: int):
        return abs(self.current_floor - floor)

    def __repr__(self):
        return f"<Elevator {self.id} Floor:{self.current_floor} Dir:{self.direction.name}>"


# Scheduler (Dispatcher)
class Scheduler:
    def __init__(self, elevators: List[Elevator]):
        self.elevators = elevators

    def assign_request(self, request: Request):
        best = None
        min_distance = float("inf")
        for elevator in self.elevators:
            if elevator.is_idle():
                distance = elevator.distance_to(request.floor)
            else:
                if (elevator.direction == request.direction and
                    ((request.direction == Direction.UP and request.floor >= elevator.current_floor) or
                     (request.direction == Direction.DOWN and request.floor <= elevator.current_floor))):
                    distance = elevator.distance_to(request.floor)
                else:
                    continue

            if distance < min_distance:
                min_distance = distance
                best = elevator

        if best:
            best.add_stop(request.floor)
            print(f"Assigned Elevator {best.id} to floor {request.floor}")


# =========================
# Elevator System
# =========================

class ElevatorSystem:
    def __init__(self, num_elevators: int, num_floors: int):
        self.elevators = [Elevator(i, num_floors) for i in range(num_elevators)]
        self.scheduler = Scheduler(self.elevators)
        self.floors = [Floor(i, self) for i in range(num_floors + 1)]

    def handle_external_request(self, request: Request):
        self.scheduler.assign_request(request)

    def step(self):
        for elevator in self.elevators:
            elevator.step()

    def __repr__(self):
        return "\n".join(str(e) for e in self.elevators)



if __name__ == "__main__":
    system = ElevatorSystem(num_elevators = 3, num_floors = 10)

    # External floor button presses
    system.floors[5].up_button.press()
    system.floors[2].down_button.press()
    system.floors[8].up_button.press()

    # Simulate time steps
    for t in range(15):
        print(f"\nTime step {t}")
        system.step()
        print(system)