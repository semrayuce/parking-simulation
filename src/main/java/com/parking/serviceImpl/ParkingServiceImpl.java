package com.parking.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.parking.exception.NoAvailableSpaceException;
import com.parking.model.Level;
import com.parking.model.ParkingRecord;
import com.parking.model.Spot;
import com.parking.model.Vehicle;
import com.parking.model.enums.VehicleType;
import com.parking.repository.LevelRepository;
import com.parking.repository.ParkingRepository;
import com.parking.repository.SpotRepository;
import com.parking.service.ParkingService;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {
	@Autowired
	private SpotRepository spotRepository;

	@Autowired
	LevelRepository levelRepository;

	@Autowired
	ParkingRepository parkingRepository;

	public ParkingRecord park(Integer levelId, Vehicle vehicle) throws Exception {
		String vehiclePlate = vehicle.getVehiclePlate();
		VehicleType vehicleType = vehicle.getVehicleType();

		// check if there is an onoing parking for this vehicle
		if (parkingRepository.findOneByVehiclePlate(vehiclePlate) != null) {
			throw new IllegalArgumentException("A vehicle with this plate is already parked!");
		}

		// get a suitable and available parking space
		List<Spot> availableSpots = spotRepository.findByLevelIdAndVehicleTypeAndIsFreeTrue(levelId, vehicleType);
		if (CollectionUtils.isEmpty(availableSpots)) {
			throw new NoAvailableSpaceException("No available spot found for the given vehicle type");
		}

		// use the first available space
		Spot spot = availableSpots.iterator().next();

		// create a new parking
		ParkingRecord newParking = new ParkingRecord();
		newParking.setLevelId(levelId);
		newParking.setSpotId(spot.getId());
		newParking.setVehiclePlate(vehiclePlate);
		newParking.setVehicleType(vehicleType);

		spot.setIsFree(Boolean.FALSE);
		spotRepository.save(spot);

		return parkingRepository.save(newParking);
	}

	public ParkingRecord unPark(Vehicle vehicle) {
		ParkingRecord parkingRecord = parkingRepository.findOneByVehiclePlate(vehicle.getVehiclePlate());
		if (parkingRecord == null) {
			throw new EntityNotFoundException("No parking found for this vehicle:  " + vehicle.getVehiclePlate());
		}
		// get spot
		Optional<Spot> spaceOptional = spotRepository.findById(parkingRecord.getSpotId());
		if (!spaceOptional.isPresent()) {
			throw new EntityNotFoundException("No spot found for this id:  " + parkingRecord.getSpotId());
		}
		Spot spot = spaceOptional.get();
		// set the spot as free
		spot.setIsFree(Boolean.TRUE);
		spotRepository.save(spot);

		return parkingRecord;
	}

	public Boolean isFull() {
		List<Level> allLevels = levelRepository.findAll();
		for (Level level : allLevels) {
			List<Spot> freeSpots = spotRepository.findByLevelIdAndIsFreeTrue(level.getId());
			if (freeSpots.size() > 0) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;
	}

}
