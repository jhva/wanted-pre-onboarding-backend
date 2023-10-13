package com.api.wantedpreonboardingbackend.service;

import org.springframework.stereotype.Service;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company createCompany(CompanyDto.SaveRequest saveRequest) {

        Company company = CompanyDto.toEntity(saveRequest);
        return companyRepository.save(company);

    }
}
