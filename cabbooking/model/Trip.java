package com.deepak.cabbooking.model;

import static com.deepak.cabbooking.model.TripStatus.IN_PROGRESS;
import static com.deepak.cabbooking.model.TripStatus.FINISHED;

enum TripStatus {
	IN_PROGRESS, FINISHED
}

public class Trip {
	private Rider rider;
	private Cab cab;
	private TripStatus status;
	private Double price;
	private Location src;
	private Location dst;
	
	public Trip(Rider rider, Cab cab, Double price, Location src, Location dst) {
		super();
		this.rider = rider;
		this.cab = cab;
		this.price = price;
		this.src = src;
		this.dst = dst;
		this.status = IN_PROGRESS;
	}
	
	public void endTrip() {
		this.status = FINISHED;
	}

	@Override
	public String toString() {
		return "Trip [rider=" + rider + ", cab=" + cab + ", status=" + status + ", price=" + price + ", src=" + src
				+ ", dst=" + dst + "]";
	}
	
	
}
