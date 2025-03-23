package com.project.marchespublics.service.interfaces;

import com.project.marchespublics.dto.DepartmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentInterface {

    DepartmentDto save(DepartmentDto departmentDto);
    DepartmentDto update(DepartmentDto departmentDto);
    Optional<DepartmentDto> findById(Long id);
    Page<DepartmentDto> findAll(Pageable pageable);
    void delete(Long id);
    Optional<DepartmentDto> findByUserId(Long userId);
}
