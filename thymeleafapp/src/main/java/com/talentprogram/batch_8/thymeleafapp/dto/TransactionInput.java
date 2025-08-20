package com.talentprogram.batch_8.thymeleafapp.dto;

import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionInput {

    private TransactionType transactionType;
    private TransactionCategory transactionCategory;
    private double amount;
}
