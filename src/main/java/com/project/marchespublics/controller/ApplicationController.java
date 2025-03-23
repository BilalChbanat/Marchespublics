package com.project.marchespublics.controller;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final PublicationRepository publicationRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;


    @PostMapping
    public ResponseEntity<ApplicationDto> apply(@RequestBody ApplicationDto applicationDto) {
        Publication publication = publicationRepository.findById(Math.toIntExact(applicationDto.getPublicationId()))
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));

        Company company;
        try {
            company = companyRepository.findById(Math.toIntExact(applicationDto.getCompanyId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        } catch (ResourceNotFoundException e) {
            company = new Company();
            company.setName("Test Company");
            company = companyRepository.save(company);
            applicationDto.setCompanyId(company.getId());
        }

        if (applicationRepository.existsByPublicationIdAndCompanyId(
                applicationDto.getPublicationId(), applicationDto.getCompanyId())) {
            throw new IllegalStateException("This company has already applied for this publication");
        }

        Application application = applicationMapper.toEntity(applicationDto);
        application.setPublication(publication);
        application.setCompany(company);
        application.setSubmissionDate(LocalDateTime.now());
        application.setStatus(String.valueOf(ApplicationStatus.PENDING));

        application = applicationRepository.save(application);
        return ResponseEntity.ok(applicationMapper.toDto(application));
    }

    @GetMapping
    public ResponseEntity<Page<ApplicationDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(applicationService.findAll(pageable));
    }


    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<Page<Application>> getByPublication(@PathVariable Long publicationId, Pageable pageable) {
        return ResponseEntity.ok(applicationService.findByPublicationId(publicationId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getById(@PathVariable Long id) {
        return applicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTMENT')")
    public ResponseEntity<ApplicationDto> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasApplied(
            @RequestParam Long publicationId,
            @RequestParam Long companyId) {
        return ResponseEntity.ok(applicationService.hasApplied(publicationId, companyId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<ApplicationDto>> getByCompany(@PathVariable Long companyId, Pageable pageable) {
        Page<Application> applications = applicationService.findByCompanyId(companyId, pageable);
        Page<ApplicationDto> applicationDtos = applications.map(applicationMapper::toDto);
        return ResponseEntity.ok(applicationDtos);
    }
}