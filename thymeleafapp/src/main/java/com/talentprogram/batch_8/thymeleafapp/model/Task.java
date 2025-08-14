package com.talentprogram.batch_8.thymeleafapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    private int id;
    private String name;
    private String description;
    private LocalDate deadline;
}
