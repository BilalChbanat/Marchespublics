package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.ApplicationDto;
import com.project.marchespublics.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "publication.id", target = "publicationId")
    @Mapping(source = "publication.title", target = "publicationTitle")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    ApplicationDto toDto(Application application);

    @Mapping(target = "publication.id", source = "publicationId")
    @Mapping(target = "company.id", source = "companyId")
    Application toEntity(ApplicationDto applicationDto);
}
