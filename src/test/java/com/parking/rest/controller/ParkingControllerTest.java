package com.parking.rest.controller;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.parking.model.Level;
import com.parking.model.ParkingRecord;
import com.parking.model.Vehicle;
import com.parking.model.enums.VehicleType;
import com.parking.utils.TestDataSetupService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TestDataSetupService testDataSetupService;

	@LocalServerPort
	private int port;

	private long id;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Before
	public void setUp() {
		Level level = testDataSetupService.createLevel();
		id = level.getId();
	}

	@After
	public void tearDown() {
		testDataSetupService.clearRepositories();
	}

	@Test
	public void testParking() {
		Vehicle input = new Vehicle();
		input.setVehiclePlate("123-ABC-999");
		input.setVehicleType(VehicleType.CAR);

		ResponseEntity<ParkingRecord> postResponse = restTemplate
				.postForEntity(getRootUrl() + "/api/v1/parkings/" + id + "/park", input, ParkingRecord.class);
		ParkingRecord parkingRecord = postResponse.getBody();

		assertNotNull(parkingRecord);
		assertEquals(input.getVehiclePlate(), parkingRecord.getVehiclePlate());
	}

	@Test
	public void testParkingRefusal() {
		Vehicle input = new Vehicle();
		input.setVehiclePlate("123-ABC-999");
		input.setVehicleType(VehicleType.CAR);

		testDataSetupService.deleteAllSpots();
		ResponseEntity<Object> postResponse = restTemplate
				.postForEntity(getRootUrl() + "/api/v1/parkings/" + id + "/park", input, Object.class);
		HttpStatus res = postResponse.getStatusCode();

		assertEquals(406, res.value());
		assertEquals("Not Acceptable", res.getReasonPhrase());
	}

	@Test
	public void testunParking() {
		ParkingRecord p = testDataSetupService.createSimpleParking();
		Vehicle input = new Vehicle();
		input.setVehiclePlate(p.getVehiclePlate());
		input.setVehicleType(p.getVehicleType());

		ResponseEntity<ParkingRecord> postResponse = restTemplate
				.postForEntity(getRootUrl() + "/api/v1/parkings/unPark/", input, ParkingRecord.class);
		ParkingRecord parkingRecord = postResponse.getBody();

		assertNotNull(parkingRecord);
	}

}