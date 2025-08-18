package com.talentprogram.batch_8.thymeleafapp.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="account")
public class Account {

    @Id
    private String accountId;

    @Column(name="userName",nullable = false)
    private String userName;

    @Column(name ="email",nullable = false)
    private String email;

    @Column(name ="password",nullable = false)
    private String password;

    @Column(name ="nrcNumber",nullable = false)
    private String nrcNumber;

    @Column(name ="address")
    private String address;

    @Column(name ="dateOfBirth")
    private LocalDate dateOfBirth;

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

    @Column(name = "balance", nullable = false)
    private double balance;

}
