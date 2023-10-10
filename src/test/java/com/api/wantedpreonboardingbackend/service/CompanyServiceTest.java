package com.api.wantedpreonboardingbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;

import org.assertj.core.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Nested
    @DisplayName("회사 등록")
    class createCompany {
        private CompanyDto.SaveRequest saveRequest;
        private Company company;

        @BeforeEach
        void setUp() {
            saveRequest = CompanyDto.SaveRequest.builder()
                .companyCountry("한국")
                .companyName("원티드랩")
                .companyArea("판교").build();

            company = Company.createCompany(saveRequest.getCompanyCountry(), saveRequest.getCompanyArea(), saveRequest.getCompanyName())
            ;
        }

        @Test
        @DisplayName("정상적으로 회사 등록 성공")
        void successCreateCompany() {

            // When
            when(companyRepository.save(any(Company.class))).thenReturn(company);
            CompanyService companyService = new CompanyService(companyRepository); //

            Company savedCompany = companyService.createCompany(saveRequest);

            // Then
            verify(companyRepository, times(1)).save(any(Company.class));
            Assertions.assertThat(savedCompany.getCompanyName()).isEqualTo("원티드랩");
            Assertions.assertThat(savedCompany.getCompanyArea()).isEqualTo("판교");
            Assertions.assertThat(savedCompany.getCompanyCountry()).isEqualTo("한국");
        }

    }
}
