package com.accenture.chickentest_app;

import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.FarmerRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
//@ContextConfiguration()
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY)
public class ChickentestAppApplicationTests {

	@Autowired
	private FarmerRepository underTest;
	@Autowired
	private ModelMapper modelMapper;
	@Test
	void itShouldCheckIfFarmerExistsName() {
		//given
		String farmerName = "Micah Bell";
		FarmerDTO farmerTest = new FarmerDTO(farmerName, 100, 50);
		Farmer farmer = modelMapper.map(farmerTest, Farmer.class);
		underTest.save(farmer);
		//when
		boolean result = underTest.existsByName(farmerName);
		//then
		assertThat(result).isEqualTo(farmerName);
	}
}
