package com.parking.repository;

import com.parking.model.Spot;
import com.parking.model.enums.VehicleType;
import com.parking.utils.TestDataSetupService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpotRepositoryTest {

	@Autowired
	SpotRepository spotRepository;

	@Autowired
	TestDataSetupService testDataSetupService;

	@Before
	public void before() {
		testDataSetupService.createLevel();
	}

	@After
	public void tearDown() throws Exception {
		testDataSetupService.clearRepositories();
	}

	@Test
	public void testAvailableSpots() {
		List<Spot> res = spotRepository.findByLevelIdAndVehicleTypeAndIsFreeTrue(testDataSetupService.getLevelId(),
				VehicleType.CAR);
		assertEquals(5, res.size());
	}
}
