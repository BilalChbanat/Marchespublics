package com.project.marchespublics.repository;

import com.project.marchespublics.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByPublicationId(Long publicationId, Pageable pageable);
    Page<Application> findByCompanyId(Long companyId, Pageable pageable);
    boolean existsByPublicationIdAndCompanyId(Long publicationId, Long companyId);
}