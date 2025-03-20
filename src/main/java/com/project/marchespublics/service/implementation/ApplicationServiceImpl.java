package com.project.marchespublics.service.implementation;

import com.project.marchespublics.dto.ApplicationDto;
import com.project.marchespublics.enums.ApplicationStatus;
import com.project.marchespublics.mapper.ApplicationMapper;
import com.project.marchespublics.model.Application;
import com.project.marchespublics.model.Company;
import com.project.marchespublics.model.Publication;
import com.project.marchespublics.repository.ApplicationRepository;
import com.project.marchespublics.repository.CompanyRepository;
import com.project.marchespublics.repository.PublicationRepository;
import com.project.marchespublics.service.interfaces.ApplicationService;
import com.project.marchespublics.util.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PublicationRepository publicationRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public ApplicationDto apply(ApplicationDto applicationDto) {
        Publication publication = publicationRepository.findById(Math.toIntExact(applicationDto.getPublicationId()))
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));

        Company company = companyRepository.findById(Math.toIntExact(applicationDto.getCompanyId()))
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if (applicationRepository.existsByPublicationIdAndCompanyId(
                applicationDto.getPublicationId(), applicationDto.getCompanyId())) {
            throw new IllegalStateException("This company has already applied for this publication");
        }

        Application application = applicationMapper.toEntity(applicationDto);
        application.setPublication(publication);
        application.setCompany(company);
        application.setSubmissionDate(LocalDateTime.now());
        application.setStatus(String.valueOf(ApplicationStatus.PENDING));

        // Save and return
        application = applicationRepository.save(application);
        return applicationMapper.toDto(application);
    }

    @Override
    public Page<ApplicationDto> findAll(Pageable pageable) {
        return applicationRepository.findAll(pageable)
                .map(applicationMapper::toDto);
    }

    @Override
    public Page<Application> findByCompanyId(Long companyId, Pageable pageable) {
        return (Page<Application>) applicationRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public Page<Application> findByPublicationId(Long publicationId, Pageable pageable) {
        return (Page<Application>) applicationRepository.findByPublicationId(publicationId, pageable);
    }

    @Override
    public Optional<ApplicationDto> findById(Long id) {
        return applicationRepository.findById(id)
                .map(applicationMapper::toDto);
    }

    @Override
    public ApplicationDto updateStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        application.setStatus(String.valueOf(status));
        application = applicationRepository.save(application);
        return applicationMapper.toDto(application);
    }

    @Override
    public void delete(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found");
        }
        applicationRepository.deleteById(id);
    }

    @Override
    public boolean hasApplied(Long publicationId, Long companyId) {
        return applicationRepository.existsByPublicationIdAndCompanyId(publicationId, companyId);
    }
}