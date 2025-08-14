package com.talentprogram.batch_8.thymeleafapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThymeleafappApplication {

	private static Logger LOGGER  = LoggerFactory.getLogger(ThymeleafappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafappApplication.class, args);

		LOGGER.info("Thymeleaf App is started.");

	}

}
