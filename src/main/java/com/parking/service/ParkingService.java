package com.parking.service;

import com.parking.model.ParkingRecord;
import com.parking.model.Vehicle;

public interface ParkingService {
	ParkingRecord park(Integer levelId, Vehicle vehicle) throws Exception;

	ParkingRecord unPark(Vehicle vehicle);

	Boolean isFull();
}
