package com.parking.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parking.exception.ResourceNotFoundException;
import com.parking.model.Spot;
import com.parking.repository.LevelRepository;
import com.parking.repository.SpotRepository;

@RestController
@RequestMapping(path = "/api/v1/")
public class SpotController {

	@Autowired
	private SpotRepository spotRepository;

	@Autowired
	private LevelRepository levelRepository;

	@PostMapping("/spots/{levelId}")
	@ResponseStatus(HttpStatus.CREATED)
	public Spot createSpot(@RequestBody Spot spot, @PathVariable Integer levelId) {
		levelRepository.findById(levelId).map(level -> {
			spot.setLevel(level);
			return spotRepository.save(spot);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Level with id = " + levelId));
		spotRepository.save(spot);
		return spot;
	}

	@GetMapping("/spots/{levelId}/freeSpot")
	@ResponseStatus(HttpStatus.OK)
	public Integer getFreeSpots(@PathVariable Integer levelId) {
		levelRepository.findById(levelId)
				.orElseThrow(() -> new ResourceNotFoundException("Level not exist with id :" + levelId));

		List<Spot> freeSpots = spotRepository.findByLevelIdAndIsFreeTrue(levelId);

		return freeSpots.size();
	}

	@GetMapping("/spots/{levelId}/")
	@ResponseStatus(HttpStatus.OK)
	public Integer getOccupiedSpots(@PathVariable Integer levelId) {
		levelRepository.findById(levelId)
				.orElseThrow(() -> new ResourceNotFoundException("Level not exist with id :" + levelId));

		List<Spot> occupiedSpots = spotRepository.findByLevelIdAndIsFreeFalse(levelId);

		return occupiedSpots.size();
	}

}
