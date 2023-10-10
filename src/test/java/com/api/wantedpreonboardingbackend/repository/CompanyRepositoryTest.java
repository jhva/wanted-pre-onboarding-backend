package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.api.wantedpreonboardingbackend.config.QuerydslConfig;
import com.api.wantedpreonboardingbackend.entity.Company;

@DataJpaTest
@ContextConfiguration(classes = {CompanyRepositoryTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = "com.api.wantedpreonboardingbackend.repository")
@EntityScan("com.api.wantedpreonboardingbackend.entity")
@Import(QuerydslConfig.class)
@EnableJpaAuditing
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @DisplayName("회사 등록")
    @Test
    public void saveCompanyTest() {

        Company company = Company.createCompany("한국", "서울", "원티드랩");

        Company newCompany = companyRepository.save(company);

        Assertions.assertThat(newCompany.getCompanyId()).isEqualTo(company.getCompanyId());
        Assertions.assertThat(newCompany.getCompanyArea()).isEqualTo("서울");
        Assertions.assertThat(newCompany.getCompanyCountry()).isEqualTo("한국");

    }

}
