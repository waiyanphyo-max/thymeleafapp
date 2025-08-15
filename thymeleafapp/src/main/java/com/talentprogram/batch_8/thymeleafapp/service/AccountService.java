package com.talentprogram.batch_8.thymeleafapp.service;

import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    public  boolean saveAccount(Account account){

        try {
            accountRepository.save(account);
            return  true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return  false;

    }
}
