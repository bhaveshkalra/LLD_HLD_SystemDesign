package com.deepak.cabbooking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.deepak.cabbooking.model.Cab;
import com.deepak.cabbooking.model.Location;
import java.util.List;

public class CabService {
	//In memory
	Map<Integer, Cab> cabs = new HashMap<>();
	
	public void addCab(Integer id, String name) {
		//do validation here
		cabs.put(id, new Cab(id, name));
	}
	
	public Cab getCab(Integer id) {
		//do validation here
		return cabs.get(id);
	}
	
	public void updateCabLocation(Integer id, Location newLocation) {
		// do validation here
		cabs.get(id).setCurrLocation(newLocation);
	}
	
	public void updateCabAvailability(Integer id, boolean newAvailable) {
		//do validation here
		cabs.get(id).setIsAvailable(newAvailable);
	}
	
	public List<Cab> getCabs(Location src, Double distance){
		List<Cab> res = new ArrayList<Cab>();
		
		for(Cab cab : cabs.values()) {
			if(cab.getIsAvailable() && cab.getCurrLocation().distance(src) < distance) {
				res.add(cab);
			}
		}
		return res;
	}
}
