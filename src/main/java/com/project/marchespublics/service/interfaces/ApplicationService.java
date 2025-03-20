package com.project.marchespublics.service.interfaces;

import com.project.marchespublics.dto.ApplicationDto;
import com.project.marchespublics.enums.ApplicationStatus;
import com.project.marchespublics.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationService {
    ApplicationDto apply(ApplicationDto applicationDto);
    Page<ApplicationDto> findAll(Pageable pageable);
    Page<Application> findByCompanyId(Long companyId, Pageable pageable);
    Page<Application> findByPublicationId(Long publicationId, Pageable pageable);

    Optional<ApplicationDto> findById(Long id);
    ApplicationDto updateStatus(Long id, ApplicationStatus status);
    void delete(Long id);
    boolean hasApplied(Long publicationId, Long companyId);
}
