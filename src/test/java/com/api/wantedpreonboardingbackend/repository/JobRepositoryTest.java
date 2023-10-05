package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @MockBean
    private CompanyRepositoryTest companyRepositorytest;

    private Company company;

    @BeforeEach
    @DisplayName("채용공고를 등록하기 전 회사를 등록에 성공")
    @Test
    public void setupSaveCompanyTest(){
      company=  companyRepositorytest.saveCompanyTest();
    }

    @DisplayName("채용공고 등록에 성공")
    @Test
    public void saveJobTest(){
        UUID uuid = UUID.randomUUID();

        JobPost jobPost = JobPost.builder()
            .jobId(uuid)
            .jobTech("Python")
            .jobCompensation(1000000)
            .jobPosition("백엔드 주니어 개발자")
            .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
            .company(company)
            .build();

       JobPost newJobPost= jobRepository.save(jobPost);

        Assertions.assertThat(jobPost.getJobId()).isEqualTo(newJobPost.getJobId());
        Assertions.assertThat(jobPost.getJobTech()).isEqualTo(newJobPost.getJobTech());
        Assertions.assertThat(jobPost.getJobCompensation()).isEqualTo(newJobPost.getJobCompensation());
        Assertions.assertThat(jobPost.getJobPosition()).isEqualTo(newJobPost.getJobPosition());
        Assertions.assertThat(jobPost.getJobDescription()).isEqualTo(newJobPost.getJobDescription());
        Assertions.assertThat(jobPost.getCompany()).isEqualTo(newJobPost.getCompany());



    }

}
