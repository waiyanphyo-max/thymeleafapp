package com.talentprogram.batch_8.thymeleafapp.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name="transactionType")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name="transactionCategory")
    @Enumerated(EnumType.STRING)
    private TransactionCategory transactionCategory;

    @Column(name="amount")
    private double amount;

    @JsonProperty(value = "createdAt")
    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonProperty(value = "updatedAt")
    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonProperty(value = "deletedAt")
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name ="deleteFlag",nullable = false)
    private int deleteFlag=0;

    @Column(name="accountId")
    private String accountId;



}
