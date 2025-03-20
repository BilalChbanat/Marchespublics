package com.project.marchespublics.controller;

import com.project.marchespublics.dto.ApplicationDto;
import com.project.marchespublics.enums.ApplicationStatus;
import com.project.marchespublics.model.Application;
import com.project.marchespublics.service.interfaces.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDto> apply(@RequestBody ApplicationDto applicationDto) {
        return ResponseEntity.ok(applicationService.apply(applicationDto));
    }

    @GetMapping
    public ResponseEntity<Page<ApplicationDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(applicationService.findAll(pageable));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<Application>> getByCompany(@PathVariable Long companyId, Pageable pageable) {
        return ResponseEntity.ok(applicationService.findByCompanyId(companyId, pageable));
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
}