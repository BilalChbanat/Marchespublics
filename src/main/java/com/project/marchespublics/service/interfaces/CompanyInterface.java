package com.project.marchespublics.service.interfaces;

import com.project.marchespublics.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyInterface {

    CompanyDto save(CompanyDto companyDto);
    CompanyDto update(CompanyDto companyDto);
    Optional<CompanyDto> findById(Long id);
    Page<CompanyDto> findAll(Pageable pageable);
    List<CompanyDto> findByName(String name);
    Optional<CompanyDto> findByUserId(Long userId);
    void delete(int id);
    List<CompanyDto> findAllByUserId(Long userId);

}
