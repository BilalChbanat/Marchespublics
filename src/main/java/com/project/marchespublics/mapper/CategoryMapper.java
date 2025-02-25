package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.CategoryDto;
import com.project.marchespublics.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
}
