package com.project.marchespublics.service.interfaces;

import com.project.marchespublics.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryInterface {

    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto);
    Optional<CategoryDto> findById(int id);
    Page<CategoryDto> findAll(Pageable pageable);
    List<CategoryDto> findByName(String name);
    void delete(int id);
}