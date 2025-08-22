package com.talentprogram.batch_8.thymeleafapp.service;

import com.talentprogram.batch_8.thymeleafapp.dto.AccountDto;
import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import com.talentprogram.batch_8.thymeleafapp.repository.AccountRepository;
import com.talentprogram.batch_8.thymeleafapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public  boolean saveAccount(Account account){

        try {
            accountRepository.save(account);
            return  true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return  false;

    }

    public boolean saveAccount(AccountDto accountDto) {
        try {
            Account account = new Account();
            account.setAccountId(accountDto.getAccountId());
            account.setAddress(accountDto.getAddress());
            account.setUserName(accountDto.getUserName());
            account.setPassword(accountDto.getPassword());
            account.setNrcNumber(accountDto.getNrcNumber());
            account.setEmail(accountDto.getEmail());
            account.setBalance(0);
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
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

    public Account findByUserName(String userName) {
        return accountRepository.findByUserName(userName);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Optional<Account> findById(String accountId) {
        try {
            return accountRepository.findById(accountId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Account editAccount(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getAccountId()).orElse(null);
        assert account != null;
        account.setAddress(accountDto.getAddress());
        account.setUserName(accountDto.getUserName());
        account.setPassword(accountDto.getPassword());
        account.setNrcNumber(accountDto.getNrcNumber());
        account.setEmail(accountDto.getEmail());
        account.setDateOfBirth(accountDto.getDateOfBirth());
        account.setAccountId(accountDto.getAccountId());

        return accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        assert account != null;
        account.setDeletedAt(LocalDateTime.now());
        account.setDeleteFlag(1);
        accountRepository.save(account);

        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        Transaction[] transactionArray = transactions.toArray(new Transaction[0]);

        for (Transaction transaction : transactionArray) {
            transaction.setDeletedAt(LocalDateTime.now());
            transaction.setDeleteFlag(1);
            transactionRepository.save(transaction);
        }

    }
}
