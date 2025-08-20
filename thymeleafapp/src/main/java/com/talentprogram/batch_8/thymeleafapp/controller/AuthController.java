package com.talentprogram.batch_8.thymeleafapp.controller;

import com.talentprogram.batch_8.thymeleafapp.dto.AccountDto;
import com.talentprogram.batch_8.thymeleafapp.model.Account;
import com.talentprogram.batch_8.thymeleafapp.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AccountService service;

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUpController(@ModelAttribute("accountDto") @Validated AccountDto accountDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please fill all fields with correct input.");
            return "signUp";
        }
        try {
            service.saveAccount(accountDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Please fill all fields with correct input.");
            return "signUp";
        }

        return "redirect:/";
    }

    @GetMapping
    public String signInPage() {
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam("userName") String userName,
                         @RequestParam("password") String password,
                         HttpSession session,
                         Model model) {

        Account account = service.findByUserName(userName);

        if (account == null || !account.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "Invalid Username or password.");
            return "signIn";
        }

        session.setAttribute("account", account);

        return "redirect:/sayhello";

    }
}
