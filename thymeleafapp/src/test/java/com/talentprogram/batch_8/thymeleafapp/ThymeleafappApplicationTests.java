package com.talentprogram.batch_8.thymeleafapp;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThymeleafappApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafappApplicationTests.class);

	@Autowired
	AccountService accountService;

	@Test
	void contextLoads() {
	}

	@Test
	void testAccountServiceSave(){
		Account account = new Account();
		account.setAccountId("0975344459");
		account.setAddress("KMD");
		account.setUserName("saunghninphyu");
		account.setPassword("saung@123");
		account.setNrcNumber("99876666");
		account.setEmail("hhh@gmail.com");

		LOGGER.info("Your Account is saved now .{} ", accountService.saveAccount(account));
	}

}
