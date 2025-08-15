package com.talentprogram.batch_8.thymeleafapp.controller;

import com.talentprogram.batch_8.thymeleafapp.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    private static final LocalTime fiveAM = LocalTime.of(5, 0);
    private static final LocalTime noon = LocalTime.of(12, 0);
    private static final LocalTime fivePM = LocalTime.of(17, 0);
    private static final LocalTime ninePM = LocalTime.of(21, 0);

    private Task task = new Task(1, "Homework1", "Homework for thymeleaf.", LocalDate.of(2025, 8, 15));
    private Task task2 = new Task(2, "Homework2", "Homework for Html.", LocalDate.of(2025, 8, 16));
    private Task task3 = new Task(3, "Homeowrk3", "Homeowrk for database.", LocalDate.of(2025, 8, 17));

    @GetMapping("/sayhello")
    public String helloPage(Model model) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();

        model.addAttribute("name", "Wai Yan Phyo");
        model.addAttribute("displayTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));

        String message;

        if (currentTime.isAfter(fiveAM) && currentTime.isBefore(noon)) {
            message = "Good Morning.";
        } else if (currentTime.isAfter(noon) && currentTime.isBefore(fivePM)) {
            message = "Good Afternoon";
        } else if (currentTime.isAfter(fivePM) && currentTime.isBefore(ninePM)) {
            message = "Good Evening";
        } else {
            message = "Good Night";
        }

        model.addAttribute("message", message);
        return "hello";
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
}
