package com.project.marchespublics.service.implementation;

import com.project.marchespublics.dto.CompanyDto;
import com.project.marchespublics.mapper.CompanyMapper;
import com.project.marchespublics.model.Company;
import com.project.marchespublics.repository.CompanyRepository;
import com.project.marchespublics.service.interfaces.CompanyInterface;
import com.project.marchespublics.util.DuplicateResourceException;
import com.project.marchespublics.util.InvalidDataException;
import com.project.marchespublics.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Setter
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyInterface {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        if (companyDto.getName() == null || companyDto.getName().trim().isEmpty()) {
            throw new InvalidDataException("Company name cannot be empty");
        }

        if (companyDto.getRegistrationDate() == null) {
            throw new InvalidDataException("Registration date is required");
        }

        if (companyRepository.findByNameIgnoreCase(companyDto.getName()).isPresent()) {
            throw new DuplicateResourceException("Company with name '" + companyDto.getName() + "' already exists");
        }

        Company company = companyMapper.toEntity(companyDto);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) {
        if (companyDto.getId() == 0) {
            throw new InvalidDataException("Company ID cannot be null or zero");
        }

        if (!companyRepository.existsById(Math.toIntExact(companyDto.getId()))) {
            throw new ResourceNotFoundException("Company with ID " + companyDto.getId() + " not found");
        }

        Optional<Company> existingCompanyWithSameName = companyRepository.findByNameIgnoreCase(companyDto.getName());
        if (existingCompanyWithSameName.isPresent() && existingCompanyWithSameName.get().getId() != companyDto.getId()) {
            throw new DuplicateResourceException("Company with name '" + companyDto.getName() + "' already exists");
        }

        Company company = companyMapper.toEntity(companyDto);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Override
    public Optional<CompanyDto> findById(int id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new ResourceNotFoundException("Company with ID " + id + " not found");
        }

        return company.map(companyMapper::toDto);
    }

    @Override
    public Page<CompanyDto> findAll(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        return companies.map(companyMapper::toDto);
    }

    @Override
    public List<CompanyDto> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Search name cannot be empty");
        }

        List<Company> companies = companyRepository.findByNameContainingIgnoreCase(name);

        if (companies.isEmpty()) {
            throw new ResourceNotFoundException("No companies found with name containing '" + name + "'");
        }

        return companies.stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company with ID " + id + " not found");
        }
        companyRepository.deleteById(id);
    }
}