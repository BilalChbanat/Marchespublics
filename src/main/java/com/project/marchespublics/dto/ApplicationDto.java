package com.project.marchespublics.dto;

import com.project.marchespublics.enums.ApplicationStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ApplicationDto {
    private Long id;

    @NotNull(message = "Publication ID is required")
    private Long publicationId;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    private String companyName;

    private String publicationTitle;

    @Past(message = "Submission date must be in the past or present")
    private LocalDateTime submissionDate;

    @Size(max = 500, message = "Cover letter path cannot exceed 500 characters")
    private String coverLetterPath;

    @DecimalMin(value = "0.0", inclusive = true, message = "Proposed budget must be a positive number")
    private BigDecimal proposedBudget;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}