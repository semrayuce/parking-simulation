package com.parking.service;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parking.model.ParkingRecord;
import com.parking.model.Spot;
import com.parking.model.Vehicle;
import com.parking.model.enums.VehicleType;
import com.parking.repository.ParkingRepository;
import com.parking.repository.SpotRepository;
import com.parking.utils.TestDataSetupService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingServiceTest {

	@Autowired
	ParkingService parkingService;

	@Autowired
	SpotRepository spotRepository;

	@Autowired
	private TestDataSetupService testDataSetupService;

	@Before
	public void before() {
		testDataSetupService.clearRepositories();
		testDataSetupService.createLevel();
	}

	@After
	public void tearDown() throws Exception {
		testDataSetupService.clearRepositories();
	}

	@Test
	public void contextLoadsTest() {
		Assert.notNull(parkingService);
	}

	@Test
	public void parkingTest() throws Exception {

		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleType(VehicleType.CAR);
		vehicle.setVehiclePlate("34-ABC-999");

		ParkingRecord res = parkingService.park(testDataSetupService.getLevelId(), vehicle);

		// check the spot is occupied
		Optional<Spot> spot = spotRepository.findById(res.getSpotId());
		assertFalse(spot.get().getIsFree());
	}

	@Test
	public void unParkingTest() throws Exception {
		ParkingRecord p = testDataSetupService.createSimpleParking();
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleType(p.getVehicleType());
		vehicle.setVehiclePlate(p.getVehiclePlate());
		parkingService.unPark(vehicle);

		// check the spot is free
		Optional<Spot> spot = spotRepository.findById(p.getSpotId());
		assertTrue(spot.get().getIsFree());
	}

}
