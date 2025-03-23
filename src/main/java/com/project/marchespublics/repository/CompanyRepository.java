package com.project.marchespublics.repository;

import com.project.marchespublics.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findByNameContainingIgnoreCase(String name);
    Optional<Company> findByNameIgnoreCase(String name);
    Optional<Company> findByUserId(Long userId);

    @Query("SELECT c FROM Company c WHERE c.user.id = :userId")
    List<Company> findAllByUserId(@Param("userId") Long userId);
}
