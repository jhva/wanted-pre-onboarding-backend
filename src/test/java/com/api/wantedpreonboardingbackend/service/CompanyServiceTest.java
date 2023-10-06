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
    private CompanyService companyService;

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
        @DisplayName("정상적으로 회사 등록에 성공")
        void successCreateCompany() {
            //when
            companyService.createCompany(saveRequest);
            companyRepository.save(company);

            //then
            verify(companyService, times(1)).createCompany(saveRequest);
            Assertions.assertThat(company.getCompanyName()).isEqualTo("원티드랩");
            Assertions.assertThat(company.getCompanyArea()).isEqualTo("판교");
            Assertions.assertThat(company.getCompanyCountry()).isEqualTo("한국");
        }

    }
}
