package com.deepak.cabbooking;

import com.deepak.cabbooking.controller.CabController;
import com.deepak.cabbooking.controller.RiderController;
import com.deepak.cabbooking.service.CabService;
import com.deepak.cabbooking.service.RiderService;
import com.deepak.cabbooking.service.TripService;

public class CabBookingApplication {
	private static CabService cabService = new CabService();
	private static RiderService riderService = new RiderService();
	private static TripService tripService = new TripService(cabService, riderService);
	
	private static CabController cabController = new CabController(cabService, tripService);
	private static RiderController riderController = new RiderController(riderService, tripService);
	
	public static void main(String str[]) {
		riderController.registerRider(1, "Deepak"); // riderId, name
		riderController.registerRider(2, "Ayush");
		riderController.registerRider(3, "Piyush");
		
		cabController.registerCab(11, "Rajesh"); //cabId, name
		cabController.registerCab(12, "Mahesh");
		cabController.registerCab(13, "Suresh");
		cabController.registerCab(14, "Brajesh");
		
		cabController.updateCabLocation(11, 0.0, 0.0); // cabId, X-coordinate, Y-coordinate
		cabController.updateCabLocation(12, 1.0, 1.0);
		cabController.updateCabLocation(13, 50.0, 50.0);
		cabController.updateCabLocation(14, 51.0, 51.0);
		
		cabController.updateCabAvailability(14, false);
		
		riderController.book(1, 0.5, 0.5, 100.0, 100.0); //riderId, source X-coordinate, source Y-coordinate, destination X-coordinate, destination Y-coordinate
		riderController.book(2, 0.5, 0.5, 100.0, 100.0);
		
		System.out.println("--- Current trip status for User Deepak and Ayush ---");
		System.out.println(riderController.getHistory(1));
		System.out.println(riderController.getHistory(2));
		
		cabController.updateCabLocation(11, 30.0, 30.0);
		
		System.out.println("\n--- Current trip status for User Deepak and Ayush ---");
		System.out.println(riderController.getHistory(1));
		System.out.println(riderController.getHistory(2));
		
		cabController.endTrip(11);;
		
		System.out.println("\n--- Current trip status for User Deepak and Ayush ---");
		System.out.println(riderController.getHistory(1));
		System.out.println(riderController.getHistory(2));
		
		riderController.book(3, 45.0, 45.0, 100.0, 100.0);
		
		System.out.println("\n--- Current trip status for User Deepak, Ayush and Piyush ---");
		System.out.println(riderController.getHistory(1));
		System.out.println(riderController.getHistory(2));
		System.out.println(riderController.getHistory(3));
	}
}
