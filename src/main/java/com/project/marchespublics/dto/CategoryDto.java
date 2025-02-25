package com.project.marchespublics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private int id;

    @NotBlank(message = "Category name is required")
    @NotNull
    private String name;
}
