package com.talentprogram.batch_8.thymeleafapp;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import com.talentprogram.batch_8.thymeleafapp.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.YearMonth;

@SpringBootTest
class ThymeleafappApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafappApplicationTests.class);

	@Autowired
	AccountService accountService;

	@Autowired
	TransactionService transactionService;

	@Test
	void contextLoads() {
	}

	//@Test
    public void saveAccounts() {
		Account account1 = new Account();
		account1.setAccountId("09776270898");
		account1.setAddress("Meiktila");
		account1.setUserName("aunglinhtun");
		account1.setPassword("135792");
		account1.setNrcNumber("5/MKKHA(N)329482");
		account1.setEmail("aunglinhtun@gmail.com");
		account1.setDateOfBirth(LocalDate.of(2005, 9, 12));
		account1.setBalance(0);

		Account account2 = new Account();
		account2.setAccountId("09782140544");
		account2.setAddress("Kyaukpadaung");
		account2.setUserName("myintmyat");
		account2.setPassword("224680");
		account2.setNrcNumber("5/KYAUK(N)302849");
		account2.setEmail("aunglinhtun@gmail.com");
		account1.setDateOfBirth(LocalDate.of(2004, 12, 23));
		account2.setBalance(0);

		Account account3 = new Account();
		account3.setAccountId("09953514037");
		account3.setAddress("Chauk");
		account3.setUserName("theinzawhlaing");
		account3.setPassword("293847");
		account3.setNrcNumber("8/CHAUK(N)183839");
		account3.setEmail("theinzawhlaing@gmail.com");
		account1.setDateOfBirth(LocalDate.of(1997, 3, 6));
		account3.setBalance(0);

		LOGGER.info("{} is saved now .{} ", account1.getUserName(), accountService.saveAccount(account1));
		LOGGER.info("{} is saved now .{} ", account2.getUserName(), accountService.saveAccount(account2));
		LOGGER.info("{} is saved now .{} ", account3.getUserName(), accountService.saveAccount(account3));
	}

	//@Test
	void testAddInitialBalance(){

		double initialBalance =100000;
		String accountId= "09776270898";
		boolean isAdd = accountService.addInitialBalance(accountId,initialBalance);
		if(isAdd){
			LOGGER.info("Your account's initial balance is added now.");
		}
		else{
			LOGGER.error("Please check your input!");
		}

	}

//	//@Test
//	void testAddTransaction(){
//        try {
//            Transaction transaction=new Transaction();
//
//            transaction.setTransactionCategory(TransactionCategory.PH_BILL);
//            transaction.setTransactionType(TransactionType.expense);
//            transaction.setAmount(5000);
//            transaction.setAccountId("09776270898");
//
//            transactionService.saveNewTransaction(transaction);
//
//            Account updatedAccount = accountService.updateBalance(transaction.getAccountId(),transaction.getAmount(),transaction.getTransactionType(), transaction.getDeleteFlag());
//
//            LOGGER.info(updatedAccount.toString());
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//
//    }

	//@Test
	void deleteTransaction() {
        try {
            transactionService.deleteTransaction("09776270898", 3);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

	//@Test
	void testGetAllExpenses(){
		String accountId= "09953514037";

		LOGGER.info("Your all expense is : {}",
				transactionService.getAllExpenseByTransactionCategory(accountId,TransactionCategory.PH_BILL));
	}

	//@Test
	void testGetAllIncome(){
		String accountId= "09953514037";

        try {
            LOGGER.info("Your all income is : {}",
                    transactionService.getAllIncomeByTransactionCategory(accountId,TransactionCategory.SALARY));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

	@Test
	void getMonthlyExpenseSummary() {

		String accountId= "09782140544";
		YearMonth yearMonth = YearMonth.of(2025, 8);

        try {
            LOGGER.info("Expense Summary of accountId {} in {} is {}",accountId , yearMonth, transactionService.getMonthlyExpenseSummary(accountId, yearMonth));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

	//@Test
	void getMonthlyIncomeSummary() {

		String accountId= "09953514037";
		YearMonth yearMonth = YearMonth.of(2025, 8);

        try {
            LOGGER.info("Income Summary of accountId {} in {} is {}",accountId , yearMonth, transactionService.getMonthlyIncomeSummary(accountId, yearMonth));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

	//@Test
	void getAllTransaction() {

		String accountId= "09953514037";
        try {
            LOGGER.info("All Transaction of accountId {} is {}",accountId, transactionService.getAllTransaction(accountId));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
