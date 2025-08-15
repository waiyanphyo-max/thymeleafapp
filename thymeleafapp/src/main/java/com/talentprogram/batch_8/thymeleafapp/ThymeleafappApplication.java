package com.talentprogram.batch_8.thymeleafapp;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ThymeleafappApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafappApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(ThymeleafappApplication.class, args);

		LOGGER.info("My Spring boot Thymeleaf app is started now.");

	}
}
