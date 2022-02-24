package com.parking.repository;

import com.parking.model.Spot;
import com.parking.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {

	@Query(" Select s from Spot s " + " join fetch s.level as l " + " where l.id = :levelId "
			+ " and s.vehicleType = :vehicleType " + " and s.isFree = TRUE ")
	List<Spot> findEmptySpotsByLevelIdAndVehicleType(@Param("levelId") Integer levelId,
			@Param("vehicleType") VehicleType vehicleType);

	List<Spot> findByLevelIdAndVehicleTypeAndIsFreeTrue(Integer levelId, VehicleType type);

	List<Spot> findByLevelIdAndIsFreeTrue(Integer levelId);

	List<Spot> findByLevelIdAndIsFreeFalse(Integer levelId);

}
