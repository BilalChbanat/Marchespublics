package com.project.marchespublics.controller;

import com.project.marchespublics.dto.CategoryDto;
import com.project.marchespublics.service.interfaces.CategoryInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryInterface categoryService;

    public CategoryController(CategoryInterface categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAllCategories(Pageable pageable) {
        Page<CategoryDto> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> getCategoriesByName(@RequestParam String name) {
        List<CategoryDto> categories = categoryService.findByName(name);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable int id,
            @Valid @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        CategoryDto updatedCategory = categoryService.update(categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}