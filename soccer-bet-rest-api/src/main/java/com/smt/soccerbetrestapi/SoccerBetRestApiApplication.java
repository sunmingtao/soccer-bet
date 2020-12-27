package com.smt.soccerbetrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SoccerBetRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerBetRestApiApplication.class, args);
	}

}
