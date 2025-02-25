package com.project.marchespublics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 50)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Publication> publications;
}