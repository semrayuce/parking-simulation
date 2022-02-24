package com.parking.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.ParkingRecord;
import com.parking.model.Vehicle;
import com.parking.service.ParkingService;

@RestController
@RequestMapping(path = "/api/v1/")
public class ParkingController {

	private static final Logger LOG = LoggerFactory.getLogger(ParkingController.class);

	@Autowired
	private ParkingService parkingService;

	@PostMapping("parkings/{levelId}/park")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ParkingRecord park(@PathVariable Integer levelId, @RequestBody Vehicle vehicle) throws Exception {
		ParkingRecord parkingRecord = parkingService.park(levelId, vehicle);
		LOG.debug("createParking result {}", parkingRecord);
		return parkingRecord;
	}

	@PostMapping("parkings/unPark")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	public ParkingRecord unPark(@RequestBody Vehicle vehicle) {
		ParkingRecord parkingRecord = parkingService.unPark(vehicle);
		return parkingRecord;
	}

	@PostMapping("/parkings/isFull")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Boolean isFull() {
		return parkingService.isFull();

	}

}
