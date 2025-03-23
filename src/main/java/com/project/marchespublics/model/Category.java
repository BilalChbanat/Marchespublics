package com.project.marchespublics.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference("category-publications")
    private List<Publication> publications;
}