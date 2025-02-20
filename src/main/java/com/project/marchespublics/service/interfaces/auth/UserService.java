package com.project.marchespublics.service.interfaces.auth;


import com.project.marchespublics.dto.authDto.RegisterDto;
import com.project.marchespublics.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    RegisterDto create(RegisterDto userDto);
    Page<RegisterDto> findAll(Pageable pageable);
    User findById(Long id);
    RegisterDto update(Long id, RegisterDto userDto);
    void delete(Long id);
    Page<RegisterDto> findByRole(String role, Pageable pageable);
}
