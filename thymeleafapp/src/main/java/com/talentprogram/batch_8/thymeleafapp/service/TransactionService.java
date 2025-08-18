package com.talentprogram.batch_8.thymeleafapp.service;

import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final Logger LOGGER= LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    public  boolean saveNewTransaction(Transaction saveTransaction){

        try {
            if(saveTransaction.getTransactionId() != null){
                LOGGER.info("Your transaction is already exist.");
                return  false;
            }
                transactionRepository.save(saveTransaction);
                return  true;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  false;
        }
    }

    public  boolean deleteTransaction(long saveTransactionId){

        try {
            Optional<Transaction> transactionOptional = transactionRepository.findById(saveTransactionId);
            if(transactionOptional.isEmpty()){
                LOGGER.info("Your transaction is not exist.");
                return  false;
            }
            Transaction saveTransaction = transactionOptional.get();
            saveTransaction.setDeleteFlag(1);
            saveTransaction.setDeletedAt(LocalDateTime.now());

            accountService.updateBalance(saveTransaction.getAccountId(), saveTransaction.getAmount(), saveTransaction.getTransactionType(), saveTransaction.getDeleteFlag());

            transactionRepository.save(saveTransaction);
            return  true;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  false;
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

    public List<Transaction> getAllTransaction(String accountId) {
        try {
            return transactionRepository.findAllTransaction(accountId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
