package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.api.wantedpreonboardingbackend.entity.Company;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @DisplayName("회사 등록")
    @Test
    public void saveCompanyTest(){

        UUID uuid = UUID.randomUUID();

        Company company = Company.createCompany(uuid,"한국","판교");

       Company newCompany= companyRepository.save(company);

        Assertions.assertThat(newCompany.getCompanyId()).isEqualTo(company.getCompanyId());
        Assertions.assertThat(newCompany.getCompanyArea()).isEqualTo("판교");
        Assertions.assertThat(newCompany.getCompanyCountry()).isEqualTo("한국");

    }
}
