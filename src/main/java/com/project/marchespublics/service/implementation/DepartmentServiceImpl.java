package com.project.marchespublics.service.implementation;

import com.project.marchespublics.dto.DepartmentDto;
import com.project.marchespublics.mapper.DepartmentMapper;
import com.project.marchespublics.model.Department;
import com.project.marchespublics.repository.DepartmentRepository;
import com.project.marchespublics.service.interfaces.DepartmentInterface;
import com.project.marchespublics.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Setter
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentInterface {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = departmentMapper.toEntity(departmentDto);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto) {
        if (departmentDto.getId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null for update operation");
        }

        if (!departmentRepository.existsById(departmentDto.getId())) {
            throw new ResourceNotFoundException("Department with ID " + departmentDto.getId() + " not found");
        }

        Department department = departmentMapper.toEntity(departmentDto);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public Optional<DepartmentDto> findById(Integer id) {
        return departmentRepository.findById(id.longValue())
                .map(departmentMapper::toDto);
    }

    @Override
    public Page<DepartmentDto> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toDto);
    }

    @Override
    public void delete(int id) {
        if (!departmentRepository.existsById((long) id)) {
            throw new ResourceNotFoundException("Department with ID " + id + " not found");
        }
        departmentRepository.deleteById((long) id);
    }
}