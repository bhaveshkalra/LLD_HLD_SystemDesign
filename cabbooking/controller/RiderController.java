package com.deepak.cabbooking.controller;

import java.util.List;
import com.deepak.cabbooking.model.Location;
import com.deepak.cabbooking.model.Trip;
import com.deepak.cabbooking.service.RiderService;
import com.deepak.cabbooking.service.TripService;

public class RiderController {
	private RiderService riderService;
	private TripService tripService;
	
	public RiderController(RiderService riderService, TripService tripService) {
		super();
		this.riderService = riderService;
		this.tripService = tripService;
	}
	
	public void registerRider(Integer id, String name) {
		riderService.addRider(id, name);
	}
	
	public void book(Integer id, Double srcX, Double srcY, Double dstX, Double dstY) {
		tripService.createTrip(riderService.getRider(id), new Location(srcX, srcY), new Location(dstX, dstY));
	}
	
	public List<Trip> getHistory(Integer id){
		return tripService.tripHistory(id);
	}
}
