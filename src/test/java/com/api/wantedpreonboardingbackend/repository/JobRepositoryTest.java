package com.api.wantedpreonboardingbackend.repository;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
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

    private static Company company;

    private static JobPost returnJobPostMethod() {
        UUID uuid = UUID.randomUUID();

        JobPost jobPost = JobPost.builder()
            .jobId(uuid)
            .jobTech("Python")
            .jobCompensation(1000000)
            .jobPosition("백엔드 주니어 개발자")
            .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
            .company(company)
            .build();
        return jobPost;

    }

    @BeforeEach
    @DisplayName("채용공고를 등록하기 전 회사를 등록에 성공")
    @Test
    public void setupSaveCompanyTest() {
        company = companyRepositorytest.saveCompanyTest();
    }

    @BeforeEach
    @DisplayName("채용공고 등록에 성공")
    @Test
    public void saveJobTest() {

        JobPost newJobPost = jobRepository.save(returnJobPostMethod());

        Assertions.assertThat(newJobPost.getJobId()).isEqualTo(newJobPost.getJobId());
        Assertions.assertThat(newJobPost.getJobTech()).isEqualTo(newJobPost.getJobTech());
        Assertions.assertThat(newJobPost.getJobCompensation()).isEqualTo(newJobPost.getJobCompensation());
        Assertions.assertThat(newJobPost.getJobPosition()).isEqualTo(newJobPost.getJobPosition());
        Assertions.assertThat(newJobPost.getJobDescription()).isEqualTo(newJobPost.getJobDescription());
        Assertions.assertThat(newJobPost.getCompany()).isEqualTo(newJobPost.getCompany());

    }

    @DisplayName("등록된 채용공고를 수정")
    @Test
    public void saveJobModify() {

        JobPost oldPost = returnJobPostMethod();

        JobPost savePost = jobRepository.save(oldPost);

        savePost.updateJobPost("프론트 주니어 개발자", 1500000, "Python", "원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다.");

        JobPost newUpdatePost = jobRepository.save(savePost);

        Assertions.assertThat(oldPost.getJobTech()).isEqualTo(newUpdatePost.getJobTech());
        Assertions.assertThat(oldPost.getJobPosition()).isNotEqualTo(newUpdatePost.getJobPosition());
        Assertions.assertThat(oldPost.getJobCompensation()).isNotEqualTo(newUpdatePost.getJobCompensation());
        Assertions.assertThat(oldPost.getJobDescription()).isNotEqualTo(newUpdatePost.getJobDescription());

    }

    @DisplayName("등록된 채용공고를 삭제")
    @Test
    public void deleteJob() {
        JobPost oldPost = returnJobPostMethod();

        JobPost savePost = jobRepository.save(oldPost);

        jobRepository.deleteById(savePost.getJobId());

        Assertions.assertThat(jobRepository.findById(savePost.getJobId())).isEmpty();
    }

    @DisplayName("채용공고 목록 조회")
    @Test
    public void findAllJob() {

        for (int i = 0; i < 3; i++) {
            JobPost jobPost = returnJobPostMethod();
            jobRepository.save(jobPost);
        }
        List<JobPost> jobPostList = jobRepository.findAll();

        Assertions.assertThat(jobPostList).isNotEmpty();
        Assertions.assertThat(jobPostList.size()).isEqualTo(4);
    }

}
