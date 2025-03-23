package com.project.marchespublics.repository;

import com.project.marchespublics.model.Department;
import com.project.marchespublics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d JOIN d.users u WHERE u.id = :userId")
    Optional<Department> findByUserId(@Param("userId") Long userId);
}
