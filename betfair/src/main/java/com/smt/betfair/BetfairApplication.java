package com.smt.betfair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BetfairApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetfairApplication.class, args);
	}

}
