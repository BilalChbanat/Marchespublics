package com.project.marchespublics.model;

import com.project.marchespublics.enums.PublicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "publications")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    @Column(name = "estimated_budget")
    private Double estimatedBudget;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PublicationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}