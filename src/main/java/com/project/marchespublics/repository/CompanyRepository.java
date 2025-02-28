package com.project.marchespublics.repository;

import com.project.marchespublics.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findByNameContainingIgnoreCase(String name);
    Optional<Company> findByNameIgnoreCase(String name);
}
