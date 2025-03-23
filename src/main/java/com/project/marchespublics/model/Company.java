package com.project.marchespublics.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "companies")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "employees_number")
    private int employeesNumber;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public boolean belongsToUser(Long userId) {
        return user != null && user.getId().equals(userId);
    }
}