package com.project.marchespublics.controller;

import com.project.marchespublics.dto.CategoryDto;
import com.project.marchespublics.dto.CompanyDto;
import com.project.marchespublics.service.implementation.CompanyServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyServiceImpl companyService;

    @PostMapping("/save")
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        CompanyDto createdCompany = companyService.save(companyDto);
        return ResponseEntity.ok(createdCompany);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyDto>> getAllCompanies(Pageable pageable) {
        Page<CompanyDto> allCompanies = companyService.findAll(pageable);
        return ResponseEntity.ok(allCompanies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyDto companyDto) {
        companyDto.setId(id);
        CompanyDto updatedCompany = companyService.update(companyDto);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDto> deleteCompany(@PathVariable int id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyDto>> searchCompany(@RequestParam String name, Pageable pageable) {
        companyService.findByName(name);
        Page<CompanyDto> allCompanies = companyService.findAll(pageable);
        return ResponseEntity.ok(allCompanies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable int id) {

        return companyService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
