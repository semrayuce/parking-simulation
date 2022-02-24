package com.parking.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.Level;
import com.parking.repository.LevelRepository;

@RestController
@RequestMapping(path = "/api/v1/")
public class LevelController {

	@Autowired
	private LevelRepository levelRepository;

	@PostMapping("/levels")
	@ResponseStatus(HttpStatus.CREATED)
	public Level createLevel(@RequestBody Level level) {
		levelRepository.save(level);
		return level;
	}
}
