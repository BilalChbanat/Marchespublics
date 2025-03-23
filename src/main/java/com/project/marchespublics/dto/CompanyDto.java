package com.project.marchespublics.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;

    @NotBlank(message = "Company name is required")
    private String name;

    private String address;

    @Min(value = 1, message = "Number of employees must be at least 1")
    private int employeesNumber;

    @NotNull(message = "Registration date is required")
    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDate registrationDate;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "User error")
    private Long userId;
}