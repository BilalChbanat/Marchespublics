package com.project.marchespublics.mapper;

import com.project.marchespublics.dto.authDto.RegisterDto;
import com.project.marchespublics.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterDto userDto);
    RegisterDto toDto(User user);
}
