
package com.parking.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.model.Level;
import com.parking.model.ParkingRecord;
import com.parking.model.Spot;
import com.parking.model.enums.VehicleType;
import com.parking.repository.LevelRepository;
import com.parking.repository.ParkingRepository;
import com.parking.repository.SpotRepository;

@Service
@Transactional
public class TestDataSetupService {

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private ParkingRepository parkingRepository;

	@Autowired
	private SpotRepository spotRepository;

	public ParkingRecord createSimpleParking() {
		ParkingRecord parkingRecord = new ParkingRecord();
		parkingRecord.setSpotId(getFreeSpace(VehicleType.CAR));
		parkingRecord.setLevelId(getLevelId());
		parkingRecord.setVehiclePlate("123-ABC-99");
		parkingRecord.setVehicleType(VehicleType.CAR);
		return parkingRepository.save(parkingRecord);
	}

	private long getFreeSpace(VehicleType type) {
		List<Spot> spots = spotRepository.findEmptySpotsByLevelIdAndVehicleType(getLevelId(), type);
		if (spots.isEmpty())
			return 0;
		return spots.get(0).getId();
	}

	public Integer getLevelId() {
		List<Level> all = levelRepository.findAll();
		if (all.isEmpty()) {
			return 0;
		}
		return all.get(0).getId();
	}

	public Level createLevel() {
		Level level = levelRepository.save(new Level());

		// spot for level
		Set<Spot> spots = new HashSet<>();
		for (int i = 0; i < 5; i++) {
			Spot s = new Spot();
			s.setVehicleType(VehicleType.CAR);
			s.setLevel(level);
			s.setIsFree(Boolean.TRUE);
			spotRepository.save(s);
			spots.add(s);
		}
		for (int i = 0; i < 5; i++) {
			Spot s = new Spot();
			s.setVehicleType(VehicleType.MOTORBIKE);
			s.setLevel(level);
			s.setIsFree(Boolean.TRUE);
			spotRepository.save(s);
			spots.add(s);
		}

		level.setSpots(spots);

		return level;
	}

	public void deleteAllSpots() {
		spotRepository.deleteAll();
	}

	public void clearRepositories() {
		parkingRepository.deleteAll();
		spotRepository.deleteAll();
		levelRepository.deleteAll();
	}

}
