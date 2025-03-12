package com.project.marchespublics;



import com.project.marchespublics.dto.CategoryDto;
import com.project.marchespublics.mapper.CategoryMapper;
import com.project.marchespublics.model.Category;
import com.project.marchespublics.repository.CategoryRepository;
import com.project.marchespublics.service.implementation.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        // Setup test data
        category = Category.builder()
                .id(1)
                .name("Test Category")
                .build();

        categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("Test Category");
    }

    @Test
    void testSaveCategory_Success() {
        // Arrange
        CategoryDto inputDto = new CategoryDto();
        inputDto.setName("New Category");

        when(categoryMapper.toEntity(inputDto)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categoryService.save(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Category", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_Success() {
        // Arrange
        CategoryDto updateDto = new CategoryDto();
        updateDto.setId(1);
        updateDto.setName("Updated Category");

        Category updatedCategory = Category.builder()
                .id(1)
                .name("Updated Category")
                .build();

        CategoryDto updatedDto = new CategoryDto();
        updatedDto.setId(1);
        updatedDto.setName("Updated Category");

        when(categoryMapper.toEntity(updateDto)).thenReturn(updatedCategory);
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(updatedDto);

        // Act
        CategoryDto result = categoryService.update(updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Category", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        Optional<CategoryDto> result = categoryService.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Category", result.get().getName());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<CategoryDto> result = categoryService.findById(999);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        Page<Category> categoryPage = new PageImpl<>(categories);

        Pageable pageable = PageRequest.of(0, 10);
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        Page<CategoryDto> result = categoryService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Category", result.getContent().get(0).getName());
    }

    @Test
    void testFindByName() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        when(categoryRepository.findByNameContainingIgnoreCase("Test")).thenReturn(categories);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        List<CategoryDto> result = categoryService.findByName("Test");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(categoryRepository.existsById(1)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1);

        // Act & Assert (no exception should be thrown)
        assertDoesNotThrow(() -> categoryService.delete(1));
        verify(categoryRepository, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(categoryRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoryService.delete(999));
        assertTrue(exception.getMessage().contains("not found"));
        verify(categoryRepository, never()).deleteById(anyInt());
    }
}