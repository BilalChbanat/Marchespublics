package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.PublicationDto;
import com.project.marchespublics.model.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "department", ignore = true)
    Publication toEntity(PublicationDto publicationDto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "department.id", target = "departmentId")
    PublicationDto toDto(Publication publication);
}