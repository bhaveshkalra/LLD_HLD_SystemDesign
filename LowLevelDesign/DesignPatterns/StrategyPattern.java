package LowLevelDesign.DesignPatterns;
import java.util.*;

abstract class DriveStrategy {
    public abstract void drive();
}

class NormalDrive extends DriveStrategy {
    public void drive() {
        System.out.println("Normal drive");
    }
}

class SpecialDrive extends DriveStrategy {
    public void drive() {
        System.out.println("fast/special drive");
    }
}

class Vehicle {
    private DriveStrategy ds;

    public Vehicle(DriveStrategy ds) {
        this.ds = ds;
    }

    public void drive() {
        ds.drive();
    }
}

class Sports extends Vehicle {
    public Sports() {
        super(new SpecialDrive());
    }
}

class Offroad extends Vehicle {
    public Offroad() {
        super(new SpecialDrive());
    }
}

class Passenger extends Vehicle {
    public Passenger() {
        super(new NormalDrive());
    }
}

public class StrategyPattern {
    public static void main(String[] args) {
        Offroad off = new Offroad();
        off.drive();

        Passenger pass = new Passenger();
        pass.drive();
    }
}