package com.project.marchespublics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.marchespublics.enums.PublicationStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @NotNull
    private LocalDateTime publicationDate;

    @Future(message = "Deadline date must be in the future")
    private LocalDateTime deadlineDate;

    @NotNull(message = "Refernce number can not be null")
    private String referenceNumber;

    @PositiveOrZero(message = "Estimated budget must be a positive number or zero")
    private Double estimatedBudget;

    private PublicationStatus status;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

}