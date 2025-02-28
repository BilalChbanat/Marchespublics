package com.project.marchespublics.service.implementation;

import com.project.marchespublics.dto.CategoryDto;
import com.project.marchespublics.mapper.CategoryMapper;
import com.project.marchespublics.model.Category;
import com.project.marchespublics.model.User;
import com.project.marchespublics.repository.CategoryRepository;
import com.project.marchespublics.service.interfaces.CategoryInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
@Setter
public class CategoryServiceImpl implements CategoryInterface {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public Optional<CategoryDto> findById(int id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::toDto);
    }

    @Override
    public List<CategoryDto> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}