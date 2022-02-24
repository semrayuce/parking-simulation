package com.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.ParkingRecord;

public interface ParkingRepository extends JpaRepository<ParkingRecord, Integer> {

	ParkingRecord findOneByVehiclePlate(String vehiclePlate);

	// Boolean isFull();
}
