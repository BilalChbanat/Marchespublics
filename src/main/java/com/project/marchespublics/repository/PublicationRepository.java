package com.project.marchespublics.repository;

import com.project.marchespublics.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Integer> {
    List<Publication> findByDepartmentId(Long departmentId);

    @Query("SELECT p FROM Publication p WHERE p.department.user.id = :userId")
    List<Publication> findByUserDepartment(@Param("userId") Long userId);
    List<Publication> findByDepartment_Id(Long departmentId);

    List<Publication> findByDepartment_User_Id(Long userId);
}