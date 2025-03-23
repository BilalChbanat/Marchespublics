package com.project.marchespublics.service.implementation;

import com.project.marchespublics.dto.DepartmentDto;
import com.project.marchespublics.mapper.DepartmentMapper;
import com.project.marchespublics.model.Department;
import com.project.marchespublics.model.User;
import com.project.marchespublics.repository.DepartmentRepository;
import com.project.marchespublics.repository.UserRepository;
import com.project.marchespublics.service.interfaces.DepartmentInterface;
import com.project.marchespublics.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentInterface {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final UserRepository userRepository; // Add UserRepository

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        // Validate and fetch the user
        User user = userRepository.findById(departmentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + departmentDto.getUserId()));

        // Map DTO to entity
        Department department = departmentMapper.toEntity(departmentDto);

        // Set the user
        department.setUser(user);

        // Save the department
        department = departmentRepository.save(department);

        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto) {
        // Validate input
        if (departmentDto.getId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null for update operation");
        }

        // Find existing department
        Department existingDepartment = departmentRepository.findById(departmentDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + departmentDto.getId() + " not found"));

        // Validate and fetch the user
        User user = userRepository.findById(departmentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + departmentDto.getUserId()));

        // Map DTO to entity
        Department department = departmentMapper.toEntity(departmentDto);

        // Set the user
        department.setUser(user);

        // Save the updated department
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public Optional<DepartmentDto> findById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDto);
    }

    @Override
    public Page<DepartmentDto> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));

        departmentRepository.delete(department);
    }

    @Override
    public Optional<DepartmentDto> findByUserId(Long userId) {
        return departmentRepository.findByUser_Id(userId)
                .map(departmentMapper::toDto);
    }
}