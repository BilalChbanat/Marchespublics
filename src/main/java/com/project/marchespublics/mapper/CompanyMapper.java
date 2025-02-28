package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.CategoryDto;
import com.project.marchespublics.dto.CompanyDto;
import com.project.marchespublics.model.Category;
import com.project.marchespublics.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyDto companyDto);
    CompanyDto toDto(Company company);
}
