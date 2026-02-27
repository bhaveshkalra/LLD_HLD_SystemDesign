package com.deepak.cabbooking.service;

import java.util.HashMap;
import java.util.Map;
import com.deepak.cabbooking.model.Rider;

public class RiderService {
	//In Memory database
	Map<Integer, Rider> riders = new HashMap<>();
	
	public void addRider(Integer id, String name) {
		//do validation here
		riders.put(id, new Rider(id, name));
	}
	
	public Rider getRider(Integer id) {
		//do validation here
		return riders.get(id);
	}
	
}
