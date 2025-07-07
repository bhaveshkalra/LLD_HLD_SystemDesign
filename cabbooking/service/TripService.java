package com.deepak.cabbooking.service;

import java.util.Map;
import java.util.stream.Collectors;

import com.deepak.cabbooking.model.Cab;
import com.deepak.cabbooking.model.Location;
import com.deepak.cabbooking.model.Rider;
import com.deepak.cabbooking.model.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TripService {
	public static final Double MAX_DISTANCE = 10.0;
	public static final Double PER_KM_RATE = 10.0;
	private Map<Integer, List<Trip>> trips = new HashMap<>();

	private CabService cabService;
	private RiderService riderService;
	
	public TripService(CabService cabService, RiderService riderService) {
		super();
		this.cabService = cabService;
		this.riderService = riderService;
	}
	
	public void createTrip(Rider rider, Location src, Location dst) {
		List<Cab> nearByCabs = cabService.getCabs(src, MAX_DISTANCE);
		
		List<Cab> nearByAvailableCabs = nearByCabs.stream().filter(cab -> cab.getCurrentTrip() == null).collect(Collectors.toList());
		
		//Cab finding strategy
		Cab selectedCab;
		if(nearByAvailableCabs.isEmpty()) {
			selectedCab = null;
			System.out.println("No Cabs Available");
			return;
		}else {
			selectedCab = nearByAvailableCabs.get(0);
		}
		
		//Pricing strategy
		Double price = src.distance(dst) * PER_KM_RATE;
		
		Trip newTrip = new Trip(rider, selectedCab, price, src, dst);
		if (!trips.containsKey(rider.getId())) {
		      trips.put(rider.getId(), new ArrayList<>());
		}
		trips.get(rider.getId()).add(newTrip);
		selectedCab.setCurrentTrip(newTrip);
	}
	
	public List<Trip> tripHistory(Integer riderId){
		return trips.get(riderId);
	}
	
	public void endTrip(Cab cab) {
		//do validation here
		
		cab.getCurrentTrip().endTrip();
		cab.setCurrentTrip(null);
		
	}
}
