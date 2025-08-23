package com.talentprogram.batch_8.thymeleafapp.repository;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {

    @Query("select a from Account a where a.userName = :userName")
    Account findByUserName(@Param("userName") String userName);

    @Query("select a from Account a where a.deleteFlag=0")
    List<Account> findAllActiveAccount();
}
