package com.project.marchespublics.dto.authDto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterDto {
    private Long id;
    private String email;
    private String password;
    private String username;
    private LocalDate created_at;
    private String role;
}