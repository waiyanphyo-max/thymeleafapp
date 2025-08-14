package com.talentprogram.batch_8.thymeleafapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/sayHello")
    public String helloPage(Model model) {
        model.addAttribute("name", "Wai Yan Phyo");
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("items", List.of("Apple", "Banana", "Orange"));
        return "hello";
    }
}
