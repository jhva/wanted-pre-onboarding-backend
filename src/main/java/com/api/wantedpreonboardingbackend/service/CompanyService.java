package com.api.wantedpreonboardingbackend.service;

import org.springframework.stereotype.Service;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void createCompany(CompanyDto.SaveRequest saveRequest) {
        Company company = Company.createCompany(saveRequest.getCompanyCountry(), saveRequest.getCompanyArea(),
            saveRequest.getCompanyName());

        companyRepository.save(company);

    }
}
