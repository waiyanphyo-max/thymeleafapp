package com.talentprogram.batch_8.thymeleafapp.controller.restController;

import com.talentprogram.batch_8.thymeleafapp.dto.TransactionDto;
import com.talentprogram.batch_8.thymeleafapp.dto.TransactionInput;
import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import com.talentprogram.batch_8.thymeleafapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransByUserId(@PathVariable String id) {
        List<TransactionDto> transactionList = transactionService.getAllTransaction(id);
        return ResponseEntity.ok(transactionList);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<Object> addNewTransaction(
            @PathVariable String accountId,
            @RequestParam TransactionType type,
            @RequestBody TransactionInput input) {

        Optional<Account> optionalAccount = accountService.findById(accountId);
        optionalAccount.orElseThrow();

        try {
            transactionService.saveNewTransaction(accountId, type, input);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        Account account1 = null;
        try {
            account1 = accountService.updateBalance(accountId, input.getAmount(), type, 0);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(account1);
    }

    @PutMapping("/{tran_id}")
    public ResponseEntity<Object> updateTransaction(@PathVariable Long tran_id,
                                                    @RequestBody TransactionInput input) {
        Optional<Transaction> optionalTransaction = transactionService.findById(tran_id);

        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();

            long transactionId = transaction.getTransactionId();

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionId(transactionId);
            transactionDto.setTransactionType(input.getTransactionType());
            transactionDto.setAmount(input.getAmount());
            transactionDto.setTransactionCategory(input.getTransactionCategory());

            transactionService.editTransaction(transactionDto);
            return ResponseEntity.ok(transactionDto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tran_id}/{accountId}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable Long tran_id,
                                                    @PathVariable String accountId) {

        Optional<Transaction> optionalTransaction = transactionService.findById(tran_id);

        if (optionalTransaction.isPresent()) {

            try {
                Account account = transactionService.deleteTransaction(accountId, tran_id);
                return ResponseEntity.ok(account.getBalance());
            } catch (Exception e) {
                return ResponseEntity.ok("Transaction doesn't exist or already deleted");
            }
        }
        return ResponseEntity.ok("Transaction not found");
    }

    @GetMapping("/monthly/{accountId}")
    public ResponseEntity<Object> getMonthlySummary(@PathVariable String accountId,
                                                      @RequestParam int year,
                                                      @RequestParam int month) {
        YearMonth yearMonth = YearMonth.of(year, month);

        List<Transaction> incomeTransactions = transactionService.getMonthlyIncomeSummary(accountId, yearMonth);
        List<Transaction> expenseTransactions = transactionService.getMonthlyExpenseSummary(accountId, yearMonth);

        List<TransactionDto> incomesDto = TransactionDto.getTransactions(incomeTransactions);
        List<TransactionDto> expensesDto = TransactionDto.getTransactions(expenseTransactions);

        return ResponseEntity.ok(new List[]{incomesDto, expensesDto});
    }

}
