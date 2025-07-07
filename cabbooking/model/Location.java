package com.deepak.cabbooking.model;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Location {
	private Double x;
	private Double y;
	
	public Location(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + "]";
	}
	
	public Double distance(Location location2) {
		return sqrt(pow(this.x - location2.x, 2) + pow(this.y - location2.y, 2));
	}
}
