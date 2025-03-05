package com.project.marchespublics.mapper;


import com.project.marchespublics.dto.DepartmentDto;
import com.project.marchespublics.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentDto departmentDto);
    DepartmentDto toDto(Department department);
}
