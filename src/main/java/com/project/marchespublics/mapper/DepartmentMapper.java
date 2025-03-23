package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.DepartmentDto;
import com.project.marchespublics.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "userId", source = "user.id")
    DepartmentDto toDto(Department department);

    @Mapping(target = "user.id", source = "userId")
    Department toEntity(DepartmentDto departmentDto);
}