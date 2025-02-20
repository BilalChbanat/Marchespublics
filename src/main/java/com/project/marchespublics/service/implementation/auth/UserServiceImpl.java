package com.project.marchespublics.service.implementation.auth;


import com.project.marchespublics.dto.authDto.RegisterDto;
import com.project.marchespublics.enums.UserRole;
import com.project.marchespublics.mapper.UserMapper;
import com.project.marchespublics.model.User;
import com.project.marchespublics.repository.UserRepository;
import com.project.marchespublics.service.interfaces.auth.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Transactional
@RequiredArgsConstructor
@Service
@Setter
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public RegisterDto create(RegisterDto userDto) {
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        String roleStr = userDto.getRole();
        if (roleStr != null) {
            try {
                UserRole role = UserRole.valueOf(roleStr.toUpperCase());
                User user = userMapper.toEntity(userDto);
                user.setRole(role);
                user = userRepository.save(user);
                return userMapper.toDto(user);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role. Must be one of: " +
                        Arrays.toString(UserRole.values()));
            }
        } else {
            User user = userMapper.toEntity(userDto);
            user.setRole(UserRole.USER);
            user = userRepository.save(user);
            return userMapper.toDto(user);
        }
    }

    @Override
    public Page<RegisterDto> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDto);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public RegisterDto update(Long id, RegisterDto userDto) {
        User user = findById(id);

        User usertoUpdate = userMapper.toEntity(userDto);
        usertoUpdate.setId(id);

        User updatedUser = userRepository.save(usertoUpdate);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        userRepository.delete(user);
    }

    @Override
    public Page<RegisterDto> findByRole(String role, Pageable pageable) {
        Page<User> users = userRepository.findByRole(role, pageable);
        return users.map(userMapper::toDto);
    }

}
