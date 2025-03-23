package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.CompanyDto;
import com.project.marchespublics.model.Company;
import com.project.marchespublics.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "userId", source = "user.id")
    CompanyDto toDto(Company company);

    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    Company toEntity(CompanyDto companyDto);

    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    void updateEntityFromDto(CompanyDto companyDto, @MappingTarget Company company);

    @Named("userIdToUser")
    default User userIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}