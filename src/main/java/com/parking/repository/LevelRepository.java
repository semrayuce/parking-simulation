package com.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Level;

public interface LevelRepository extends JpaRepository<Level, Integer> {
	Optional<Level> findById(Integer levelId);
}
