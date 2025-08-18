package com.talentprogram.batch_8.thymeleafapp.service;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import com.talentprogram.batch_8.thymeleafapp.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    public  boolean saveAccount(Account account){

        try {
            accountRepository.save(account);
            return  true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return  false;

    }

    public  boolean addInitialBalance(String accountId,double initialBalance){

        try {
            Optional<Account> accountOptional= accountRepository.findById(accountId);
            if(accountOptional.isEmpty()){
                LOGGER.info("Your account is not exist.");
                return  false;
            }
            Account account = accountOptional.get();
            account.setBalance(initialBalance);
            accountRepository.save(account);

            return  true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  false;
        }
    }

    public Account updateBalance(String accountId, double amount, TransactionType transactionType, int deleteFlag){

        try {
            Optional<Account> accountOptional= accountRepository.findById(accountId);
            if(accountOptional.isEmpty()){
                LOGGER.info("Your account is not exist.");
            }
            Account account = accountOptional.get();

            double balance = account.getBalance();

            if (deleteFlag == 0) {
                if(TransactionType.expense == transactionType) {
                    balance -=amount;
                }
                else {
                    balance +=amount;
                }
                account.setBalance(balance);
            } else {
                if(TransactionType.expense == transactionType) {
                    balance +=amount;
                }
                else {
                    balance -=amount;
                }
                account.setBalance(balance);
            }

            return accountRepository.save(account);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  null;
        }
    }
}
