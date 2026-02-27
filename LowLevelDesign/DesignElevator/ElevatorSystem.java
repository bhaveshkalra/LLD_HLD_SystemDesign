package LowLevelDesign.LLD_HLD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

enum Direction {
    UP, DOWN
}

enum ElevatorState {
    IDLE, MOVING, DOOR_OPEN, MAINTENANCE
}

class ElevatorButton {
    int floor;
    boolean isPressed = false;

    public ElevatorButton(int floor) {
        this.floor = floor;
    }

    public void press(Elevator elevator) {
        isPressed = true;
        elevator.addDestination(floor);
    }

    public void reset() {
        isPressed = false;
    }
}


class FloorButton {
    Direction direction;
    boolean isPressed = false;

    public FloorButton(Direction direction) {
        this.direction = direction;
    }

    public void press(int floorNumber) {
        isPressed = true;
        ElevatorSystem.getInstance().handleExternalRequest(floorNumber, direction);
    }

    public void reset() {
        isPressed = false;
    }
}


class Floor {
    int floorNumber;
    FloorButton upButton;
    FloorButton downButton;

    public Floor(int number) {
        this.floorNumber = number;
        this.upButton = new FloorButton(Direction.UP);
        this.downButton = new FloorButton(Direction.DOWN);
    }

    public void pressUp() {
        upButton.press(floorNumber);
    }

    public void pressDown() {
        downButton.press(floorNumber);
    }
}


public class ElevatorSystem {
    List<Elevator> elevators;
    Scheduler scheduler;
    static ElevatorSystem elevatorSystemObj;

    public ElevatorSystem(int numElevators) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i));
        }
        scheduler = new Scheduler(elevators);
    }

    public static ElevatorSystem getInstance() {
        if (elevatorSystemObj == null) {
            elevatorSystemObj = new ElevatorSystem(3);
        }
        return elevatorSystemObj;
        
    }

    public void handleExternalRequest(int floor, Direction direction) {
        scheduler.assignElevator(floor, direction);
    }

    public void step() {
        for (Elevator e : elevators) {
            e.step();
        }
    }

    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = new ElevatorSystem(3); // 3 elevators

        // External Requests
        elevatorSystem.handleExternalRequest(4, Direction.UP);
        elevatorSystem.handleExternalRequest(1, Direction.DOWN);

        // Simulate system ticks
        for (int i = 0; i < 10; i++) {
            System.out.println("Time step: " + i);
            elevatorSystem.step(); // moves elevators per logic
        }
    }
}

class Elevator {
    private static final int MAX_FLOORS = 10;
    int elevatorId;
    int currentFloor = 0;
    ElevatorState state = ElevatorState.IDLE;
    Direction direction = Direction.UP;
    TreeSet<Integer> destinationFloors = new TreeSet<>();
    Map<Integer, ElevatorButton> internalButtons;

    public Elevator(int id) {
        this.elevatorId = id;
        this.internalButtons = new HashMap<>();
        for (int i = 0; i <= MAX_FLOORS; i++) {
            internalButtons.put(i, new ElevatorButton(i));
        }
    }

    public void pressButtonInside(int floor) {
        internalButtons.get(floor).press(this);
    }

    public void addDestination(int floor) {
        destinationFloors.add(floor);
    }

    public void step() {
        if (destinationFloors.isEmpty()) {
            state = ElevatorState.IDLE;
            return;
        }

        int targetFloor = direction == Direction.UP ? destinationFloors.ceiling(currentFloor) :
                                                      destinationFloors.floor(currentFloor);

        if (targetFloor == currentFloor) {
            openDoors();
            destinationFloors.remove(currentFloor);
        } else {
            moveTowards(targetFloor);
        }
    }

    private void moveTowards(int targetFloor) {
        state = ElevatorState.MOVING;
        if (targetFloor > currentFloor) currentFloor++;
        else if (targetFloor < currentFloor) currentFloor--;
    }

    private void openDoors() {
        state = ElevatorState.DOOR_OPEN;
        // simulate open/close
    }
}

//✅ Scheduler (Dispatcher)
class Scheduler {
    List<Elevator> elevators;

    public Scheduler(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void assignElevator(int floor, Direction direction) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.state == ElevatorState.IDLE ||
               (e.direction == direction && ((direction == Direction.UP && floor >= e.currentFloor) ||
                                            (direction == Direction.DOWN && floor <= e.currentFloor)))) {
                int distance = Math.abs(e.currentFloor - floor);
                if (distance < minDistance) {
                    minDistance = distance;
                    best = e;
                }
            }
        }

        if (best != null) {
            best.addDestination(floor);
        }
    }
}

