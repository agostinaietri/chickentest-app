package com.accenture.chickentest_app;

import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.FarmerRepository;
import com.accenture.chickentest_app.service.FarmerService;
import com.accenture.chickentest_app.service.impl.FarmerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@ContextConfiguration()
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY)
public class ChickentestAppApplicationTests {

	@Autowired
	private FarmerRepository farmerRepository;
	@Autowired
	private FarmerService farmerService;
	@Autowired
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		farmerRepository = mock(FarmerRepository.class);
		farmerService = new FarmerServiceImpl();
	}

	/*
	@Test
	void contextLoads() {
		Mockito.when(farmerRepository.findById(1L).thenReturn(Optional.of(Data.Farmer01)));
	}
	*/
}
