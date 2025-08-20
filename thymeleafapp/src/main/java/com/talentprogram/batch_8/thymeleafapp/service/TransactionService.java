package com.talentprogram.batch_8.thymeleafapp.service;

import com.talentprogram.batch_8.thymeleafapp.dto.TransactionDto;
import com.talentprogram.batch_8.thymeleafapp.dto.TransactionInput;
import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import com.talentprogram.batch_8.thymeleafapp.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final Logger LOGGER= LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    public  boolean saveNewTransaction(String accountId, TransactionType type, TransactionInput input){

        try {

            Transaction transaction = new Transaction();
            transaction.setAccountId(accountId);
            transaction.setTransactionType(type);
            transaction.setTransactionCategory(input.getTransactionCategory());
            transaction.setAmount(input.getAmount());

            transactionRepository.save(transaction);
            return  true;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  false;
        }
    }

//    public boolean editTransaction(long transactionId) {
//
//        try {
//            Optional<Transaction> transaction = transactionRepository.findById(transactionId);
//            if (transaction.isEmpty()) {
//                LOGGER.info("Your transaction doesn't not exist");
//                return false;
//            }
//            transactionRepository.save(transaction.get());
//            return true;
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//            return false;
//        }
//    }

    public Account editTransaction(TransactionDto transactionDto){

        try {
            Optional<Transaction> transaction=  transactionRepository.findById(transactionDto.getTransactionId());
            if(transaction.isEmpty()){
                LOGGER.info("Your transaction doesn't not exist");
                return  null;
            }

            Transaction transaction2 = transaction.get();

            Account oldAccount = accountService.updateBalance(transaction2.getAccountId(), transaction2.getAmount(), transaction2.getTransactionType(), 1);

            Transaction transaction1 = transaction.get();
            transaction1.setTransactionCategory(transactionDto.getTransactionCategory());
            transaction1.setAmount(transactionDto.getAmount());
            transaction1.setTransactionType(transactionDto.getTransactionType());
            transactionRepository.save(transaction1);

            return accountService.updateBalance(transaction1.getAccountId(), transaction1.getAmount(), transaction1.getTransactionType(), 0);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  null;
        }
    }

    public Account deleteTransaction(String accountId, long saveTransactionId){

        try {
            Optional<Transaction> transactionOptional = transactionRepository.findByIdAndAccountId(accountId, saveTransactionId);
            if(transactionOptional.isEmpty()){
                LOGGER.info("Your transaction is not exist.");
                return  null;
            }
            Transaction saveTransaction = transactionOptional.get();
            saveTransaction.setDeleteFlag(1);
            saveTransaction.setDeletedAt(LocalDateTime.now());

            Account account = accountService.updateBalance(saveTransaction.getAccountId(), saveTransaction.getAmount(), saveTransaction.getTransactionType(), saveTransaction.getDeleteFlag());

            transactionRepository.save(saveTransaction);
            return  account;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  null;
        }
    }

    public double getAllExpenseByTransactionCategory(String accountId, TransactionCategory transactionCategory){
        try {
            return transactionRepository.findAllExpenseByCategory(accountId,transactionCategory);
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return  0;
        }
    }

    public double getAllIncomeByTransactionCategory(String accountId, TransactionCategory transactionCategory){
        try {
            return transactionRepository.findAllIncomeCategory(accountId,transactionCategory);
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return  0;
        }
    }

    public List<Transaction> getMonthlyExpenseSummary(String accountId, YearMonth yearMonth) {
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.plusMonths(1).atDay(1).atStartOfDay();

        try {
            return transactionRepository.findMonthlyExpenseSummary(accountId, start, end);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Transaction> getMonthlyIncomeSummary(String accountId, YearMonth yearMonth) {
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.plusMonths(1).atDay(1).atStartOfDay();

        try {
            return transactionRepository.findMonthlyIncomeSummary(accountId, start, end);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<TransactionDto> getAllTransaction(String accountId) {
        try {
            List<Transaction> transactions = transactionRepository.findAllTransaction(accountId);
            return TransactionDto.getTransactions(transactions);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public Optional<Transaction> findById(long transactionId) {
        return transactionRepository.findById(transactionId);
    }
}
