package com.deepak.cabbooking.controller;

import com.deepak.cabbooking.model.Location;
import com.deepak.cabbooking.service.CabService;
import com.deepak.cabbooking.service.TripService;

public class CabController {
	private CabService cabService;
	private TripService tripService;
	
	public CabController(CabService cabService, TripService tripService) {
		super();
		this.cabService = cabService;
		this.tripService = tripService;
	}
	
	public void registerCab(Integer id, String name) {
		cabService.addCab(id, name);
	}
	
	public void updateCabLocation(Integer cabId, Double x, Double y) {
		cabService.updateCabLocation(cabId, new Location(x, y));
	}
	
	public void updateCabAvailability(Integer cabId, Boolean value) {
		cabService.updateCabAvailability(cabId, value);
	}
	
	public void endTrip(Integer cabId) {
		tripService.endTrip(cabService.getCab(cabId));
	}
}
