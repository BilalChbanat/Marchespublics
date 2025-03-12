package com.project.marchespublics.service.implementation;

import com.project.marchespublics.dto.PublicationDto;
import com.project.marchespublics.enums.PublicationStatus;
import com.project.marchespublics.mapper.PublicationMapper;
import com.project.marchespublics.model.Category;
import com.project.marchespublics.model.Department;
import com.project.marchespublics.model.Publication;
import com.project.marchespublics.repository.CategoryRepository;
import com.project.marchespublics.repository.DepartmentRepository;
import com.project.marchespublics.repository.PublicationRepository;
import com.project.marchespublics.service.interfaces.PublicationInterface;
import com.project.marchespublics.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicationServiceImpl implements PublicationInterface {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public PublicationDto save(PublicationDto publicationDto) {
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMM"));
        String randomSuffix = String.format("%04d", new Random().nextInt(10000));
        String referenceNumber = "PUB-" + yearMonth + "-" + randomSuffix;

        publicationDto.setReferenceNumber(referenceNumber);
        publicationDto.setStatus(PublicationStatus.DRAFT);
        publicationDto.setPublicationDate(LocalDateTime.now());

        if (publicationDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }

        if (publicationDto.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department ID is required");
        }

        Publication publication = publicationMapper.toEntity(publicationDto);

        Category category = categoryRepository.findById(publicationDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + publicationDto.getCategoryId()));
        publication.setCategory(category);

        Department department = departmentRepository.findById(publicationDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + publicationDto.getDepartmentId()));
        publication.setDepartment(department);

        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    @Override
    public PublicationDto update(PublicationDto publicationDto) {
        if (publicationDto.getId() == null) {
            throw new IllegalArgumentException("Publication ID cannot be null");
        }

        Publication existingPublication = publicationRepository.findById(publicationDto.getId().intValue())
                .orElseThrow(() -> new ResourceNotFoundException("Publication with ID " + publicationDto.getId() + " not found"));

        if (publicationDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }

        if (publicationDto.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department ID is required");
        }

        Publication publication = publicationMapper.toEntity(publicationDto);

        publication.setPublicationDate(existingPublication.getPublicationDate());
        publication.setReferenceNumber(existingPublication.getReferenceNumber());

        Category category = categoryRepository.findById(publicationDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + publicationDto.getCategoryId()));
        publication.setCategory(category);

        Department department = departmentRepository.findById(publicationDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + publicationDto.getDepartmentId()));
        publication.setDepartment(department);

        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }


    @Override
    public Optional<PublicationDto> findById(Long id) {
        return publicationRepository.findById(Math.toIntExact(id))
                .map(publicationMapper::toDto);
    }

    @Override
    public Page<PublicationDto> findAll(Pageable pageable) {
        return publicationRepository.findAll(pageable)
                .map(publicationMapper::toDto);
    }


    @Override
    public void delete(Long id) {
        if (!publicationRepository.existsById(Math.toIntExact(id))) {
            throw new ResourceNotFoundException("Publication with ID " + id + " not found");
        }
        publicationRepository.deleteById(Math.toIntExact(id));
    }
}