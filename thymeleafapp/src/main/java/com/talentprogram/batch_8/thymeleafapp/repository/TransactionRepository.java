package com.talentprogram.batch_8.thymeleafapp.repository;

import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
}
