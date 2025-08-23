package com.talentprogram.batch_8.thymeleafapp.controller.restController;

import com.talentprogram.batch_8.thymeleafapp.dto.AccountDto;
import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRestController.class);

    @GetMapping
    public ResponseEntity<Object> getAllAccounts() {
        List<Account> accounts =  accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<Object> saveAccount(@RequestBody AccountDto accountDto) {

        if (accountService.findById(accountDto.getAccountId()).isPresent()) {
            return ResponseEntity.ok("Account already exists.");
        }
        boolean success = accountService.saveAccount(accountDto);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Object> editAccount(@PathVariable String accountId, @RequestBody AccountDto accountDto) {

        if (accountService.findById(accountId).isEmpty()) {
            return ResponseEntity.ok("Account does not exist.");
        }

        try {
            Account account = accountService.editAccount(accountDto);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable("id") String id) {

        if (accountService.findById(id).isEmpty()) {
            return ResponseEntity.ok("Account does not exist.");
        }
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        Account account = accountService.findByUserName(name);
        if (account == null) {
            return ResponseEntity.ok("Account with that username is not exist");
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        Account account = accountService.findById(id).orElse(null);
        return ResponseEntity.ok(Objects.requireNonNullElse(account, "Account doesn't exist"));
    }
}
