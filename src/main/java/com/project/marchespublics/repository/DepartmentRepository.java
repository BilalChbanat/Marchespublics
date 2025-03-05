package com.project.marchespublics.repository;

import com.project.marchespublics.model.Department;
import com.project.marchespublics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
