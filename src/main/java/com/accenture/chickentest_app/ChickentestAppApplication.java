package com.accenture.chickentest_app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.sql.*;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.accenture.chickentest_app.model"})
public class ChickentestAppApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ChickentestAppApplication.class, args);
		System.out.println("Hello world");

		/*
		try{
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/chickentest_schema",
					"root",
					"123456"
			);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from farmers");

			while(resultSet.next()) {
				System.out.println(resultSet.getString("name"));
				System.out.println(resultSet.getString("balance"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		*/
	}

}
