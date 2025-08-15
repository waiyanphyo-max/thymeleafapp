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
    public void saveAccounts() {
		Account account1 = new Account();
		account1.setAccountId("09776270898");
		account1.setAddress("Meiktila");
		account1.setUserName("aunglinhtun");
		account1.setPassword("135792");
		account1.setNrcNumber("5/MKKHA(N)329482");
		account1.setEmail("aunglinhtun@gmail.com");

		Account account2 = new Account();
		account2.setAccountId("09782140544");
		account2.setAddress("Kyaukpadaung");
		account2.setUserName("myintmyat");
		account2.setPassword("224680");
		account2.setNrcNumber("5/KYAUK(N)302849");
		account2.setEmail("aunglinhtun@gmail.com");

		Account account3 = new Account();
		account3.setAccountId("09953514037");
		account3.setAddress("Chauk");
		account3.setUserName("theinzawhlaing");
		account3.setPassword("293847");
		account3.setNrcNumber("8/CHAUK(N)183839");
		account3.setEmail("theinzawhlaing@gmail.com");

		LOGGER.info("{} is saved now .{} ",account1.getUserName(), accountService.saveAccount(account1));
		LOGGER.info("{} is saved now .{} ",account2.getUserName(), accountService.saveAccount(account2));
		LOGGER.info("{} is saved now .{} ",account3.getUserName(), accountService.saveAccount(account3));
	}

}
