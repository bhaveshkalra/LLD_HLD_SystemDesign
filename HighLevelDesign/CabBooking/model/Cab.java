package com.deepak.cabbooking.model;

public class Cab {
	Integer id;
	String driverName;
	Boolean isAvailable;
	Trip currentTrip;
	Location CurrLocation;
	
	public Cab(Integer id, String driverName) {
		super();
		this.id = id;
		this.driverName = driverName;
		this.isAvailable = true;
		this.currentTrip = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Trip getCurrentTrip() {
		return currentTrip;
	}

	public void setCurrentTrip(Trip currentTrip) {
		this.currentTrip = currentTrip;
	}

	public Location getCurrLocation() {
		return CurrLocation;
	}

	public void setCurrLocation(Location currLocation) {
		CurrLocation = currLocation;
	}

	@Override
	public String toString() {
		return "Cab [id=" + id + ", driverName=" + driverName + ", isAvailable=" + isAvailable + ", CurrLocation="
				+ CurrLocation + "]";
	}

		
	
}
