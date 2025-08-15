package com.talentprogram.batch_8.thymeleafapp.repository;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
}
