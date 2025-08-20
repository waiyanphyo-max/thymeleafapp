package com.talentprogram.batch_8.thymeleafapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDto {

    @NotBlank(message = "Phone number is required")
    private String accountId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "NRC Number is required")
    private String nrcNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private LocalDate dateOfBirth;
}
