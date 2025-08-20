package com.talentprogram.batch_8.thymeleafapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionDto {

    private Long transactionId;
    private TransactionType transactionType;
    private TransactionCategory transactionCategory;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String accountId;

    public static List<TransactionDto> getTransactions(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionId(transaction.getTransactionId());
            transactionDto.setTransactionType(transaction.getTransactionType());
            transactionDto.setTransactionCategory(transaction.getTransactionCategory());
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setCreatedAt(transaction.getCreatedAt());
            transactionDto.setUpdatedAt(transaction.getUpdatedAt());
            transactionDto.setAccountId(transaction.getAccountId());
            transactionDtos.add(transactionDto);
        }
        return transactionDtos;
    }
}
