package com.talentprogram.batch_8.thymeleafapp.controller;

import com.talentprogram.batch_8.thymeleafapp.dto.TransactionDto;
import com.talentprogram.batch_8.thymeleafapp.dto.TransactionInput;
import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.model.Task;
import com.talentprogram.batch_8.thymeleafapp.model.Transaction;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionCategory;
import com.talentprogram.batch_8.thymeleafapp.model.enumType.TransactionType;
import com.talentprogram.batch_8.thymeleafapp.repository.TransactionRepository;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import com.talentprogram.batch_8.thymeleafapp.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HelloController {

    private static final LocalTime fiveAM = LocalTime.of(5, 0);
    private static final LocalTime noon = LocalTime.of(12, 0);
    private static final LocalTime fivePM = LocalTime.of(17, 0);
    private static final LocalTime ninePM = LocalTime.of(21, 0);

    private final TransactionService transactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    private Task task = new Task(1, "Homework1", "Homework for thymeleaf.", LocalDate.of(2025, 8, 15));
    private Task task2 = new Task(2, "Homework2", "Homework for Html.", LocalDate.of(2025, 8, 16));
    private Task task3 = new Task(3, "Homeowrk3", "Homeowrk for database.", LocalDate.of(2025, 8, 17));

    @GetMapping("/sayhello")
    public String helloPage(HttpSession session, Model model) {

        Account account = (Account) session.getAttribute("account");

        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();

        model.addAttribute("name", account.getUserName());
        model.addAttribute("displayTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        model.addAttribute("balance", account.getBalance());

        final var message = getString(currentTime, account);

        model.addAttribute("message", message);

        String accountId = account.getAccountId();
        List<TransactionDto> transactionDtos = transactionService.getAllTransaction(accountId);
        model.addAttribute("transactions", transactionDtos);

        model.addAttribute("transactionInput", new TransactionInput());

        model.addAttribute("categories", TransactionCategory.values());

        model.addAttribute("showForm", false);
        model.addAttribute("showEdit", false);
        model.addAttribute("showInput", false);

        return "hello";
    }

    private static String getString(LocalTime currentTime, Account account) {
        String message;

        if (currentTime.isAfter(fiveAM) && currentTime.isBefore(noon)) {
            message = "Good Morning %s".formatted(account.getUserName());
        } else if (currentTime.isAfter(noon) && currentTime.isBefore(fivePM)) {
            message = "Good Afternoon %s".formatted(account.getUserName());
        } else if (currentTime.isAfter(fivePM) && currentTime.isBefore(ninePM)) {
            message = "Good Evening %s".formatted(account.getUserName());
        } else {
            message = "Good Night %s".formatted(account.getUserName());
        }
        return message;
    }

    @GetMapping("/task")
    public String tasks(Model model) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);
        tasks.add(task3);
        model.addAttribute("tasks", tasks);
        return "task";
    }

    @GetMapping("/addIncome")
    public String addIncome(Model model) {
        model.addAttribute("type", TransactionType.income);
        model.addAttribute("transactionInput", new TransactionDto());
        model.addAttribute("categories", TransactionCategory.values());
        model.addAttribute("showForm", true);
        return "hello";
    }

    @GetMapping("/addExpense")
    public String addExpense(Model model) {
        model.addAttribute("type", TransactionType.expense);
        model.addAttribute("transactionInput", new TransactionDto());
        model.addAttribute("categories", TransactionCategory.values());
        model.addAttribute("showForm", true);
        return "hello";
    }

    @PostMapping("/addTransaction")
    public String addTransaction(@RequestParam("type") TransactionType type, @ModelAttribute("transactionInput") @Validated TransactionInput input, BindingResult result, HttpSession session, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "hello";
        }

        Account account = (Account) session.getAttribute("account");
        String accountId = account.getAccountId();

        transactionService.saveNewTransaction(accountId, type, input);
        Account account1 = accountService.updateBalance(accountId, input.getAmount(), type, 0);

        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();

        model.addAttribute("name", account1.getUserName());
        model.addAttribute("displayTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        model.addAttribute("balance", account1.getBalance());

        final var message = getString(currentTime, account1);

        model.addAttribute("message", message);

        List<TransactionDto> transactionDtos = transactionService.getAllTransaction(accountId);
        model.addAttribute("transactions", transactionDtos);

        return "hello";

    }

    @GetMapping("/deleteTransaction/{transactionId}")
    public String deleteTransaction(@PathVariable("transactionId") long transactionId, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        String accountId = account.getAccountId();

        Account account1 = transactionService.deleteTransaction(accountId, transactionId);

        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();

        model.addAttribute("name", account1.getUserName());
        model.addAttribute("displayTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        model.addAttribute("balance", account1.getBalance());

        final var message = getString(currentTime, account1);

        model.addAttribute("message", message);

        List<TransactionDto> transactionDtos = transactionService.getAllTransaction(accountId);
        model.addAttribute("transactions", transactionDtos);

        return "hello";

    }

    @GetMapping("/editTransaction/{transactionId}")
    public String editTransaction(@PathVariable("transactionId") long transactionId, Model model) {
        Optional<Transaction> transaction = transactionService.findById(transactionId);
        Transaction transaction1 = transaction.get();
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction1.getTransactionId());
        transactionDto.setAmount(transaction1.getAmount());
        transactionDto.setTransactionType(transaction1.getTransactionType());
        transactionDto.setTransactionCategory(transaction1.getTransactionCategory());
        model.addAttribute("transaction", transactionDto);
//        model.addAttribute("transactionDto", transactionDto);
//        model.addAttribute("transaction", transaction1);
        model.addAttribute("categories", TransactionCategory.values());
        model.addAttribute("types", TransactionType.values());
//        model.addAttribute("amount", transaction1.getAmount());
//        model.addAttribute("type", transaction1.getTransactionType());
//        model.addAttribute("category", transaction1.getTransactionCategory());
        model.addAttribute("showEdit", true);
        return "/hello";
    }

    @PostMapping("/editTransaction")
    public String editTransaction(Model model, @ModelAttribute("transaction") @Validated TransactionDto transaction, BindingResult result, HttpSession session) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "hello";
        }

        Account account = transactionService.editTransaction(transaction);

        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();

        model.addAttribute("name", account.getUserName());
        model.addAttribute("displayTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        model.addAttribute("balance", account.getBalance());

        final var message = getString(currentTime, account);

        model.addAttribute("message", message);

        List<TransactionDto> transactionDtos = transactionService.getAllTransaction(account.getAccountId());
        model.addAttribute("transactions", transactionDtos);

        return "hello";
    }

    @GetMapping("/getMonthlySummary")
    public String getMonthlySummary(@RequestParam("selectedYear") @Validated int year, @RequestParam("selectedMonth") @Validated int month, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        String accountId = account.getAccountId();

        LOGGER.info("AccountId in getMonthlySummary is {}", accountId);

        YearMonth yearMonth = YearMonth.of(year, month);

        List<Transaction> incomeTransactions = transactionService.getMonthlyIncomeSummary(accountId, yearMonth);
        List<Transaction> expenseTransactions = transactionService.getMonthlyExpenseSummary(accountId, yearMonth);

        model.addAttribute("incomes", incomeTransactions);
        model.addAttribute("expenses", expenseTransactions);

        return "hello";
    }

    @GetMapping("/inputMonth")
    public String getMonthAndType(Model model) {

        List<Integer> years = IntStream.rangeClosed(1990, 2100)
                .boxed()
                .toList();

        List<Integer> months = IntStream.rangeClosed(1, 12)
                .boxed()
                .toList();
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        model.addAttribute("years", years);
        model.addAttribute("months", months);
        model.addAttribute("selectedYear", currentYear);
        model.addAttribute("selectedMonth", currentMonth);
        model.addAttribute("showInput", true);
        return "hello";
    }

}
