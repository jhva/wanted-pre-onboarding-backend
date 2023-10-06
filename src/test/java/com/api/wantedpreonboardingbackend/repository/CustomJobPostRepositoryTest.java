package com.api.wantedpreonboardingbackend.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import com.api.wantedpreonboardingbackend.config.QuerydslTestConfig;
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.QCompany;
import com.api.wantedpreonboardingbackend.entity.QJobPost;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {CustomJobPostRepositoryTest.class})
@EnableJpaRepositories(basePackages = "com.api.wantedpreonboardingbackend.repository")
@EntityScan("com.api.wantedpreonboardingbackend.entity")
@Import(QuerydslConfig.class)
@EnableJpaAuditing
public class CustomJobPostRepositoryTest {

    @Autowired
    private JobPostRepository jobPostRepository;

    @MockBean
    private CompanyRepositoryTest companyRepositorytest;

    private static Company company;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private CompanyRepository companyRepository;

    private JobPost returnJobPostMethod() {
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

    public UUID getJobPostUUID() {
        UUID uuid = returnJobPostUUID();
        JobPost findByJobPostId = jobPostRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("found no job post"));
        assertEquals(uuid, findByJobPostId.getJobId());
        return findByJobPostId.getJobId();
    }

    private UUID returnJobPostUUID() {
        UUID companyUUID = UUID.randomUUID();
        String[] tempJobDescription = new String[] {"원티드랩에서 백엔드 주니어 개발자를 채용합니다.", "원티드랩에서 프론트 주니어 개발자를 채용합니다.", "프론트 주니어 개발자를 채용합니다."};
        String[] tempJobPosition = new String[] {"백엔드 주니어 개발자", "Python 주니어 개발자", "풀스택 주니어 개발자"};
        UUID saveUUID = null;
        Company newCompany = Company.createCompany(companyUUID, "한국", "핀교", "원티드랩");
        Company saveCompany = companyRepository.save(newCompany);
        for (int i = 0; i < 3; i++) {
            UUID uuid = UUID.randomUUID();

            JobPost jobPost = JobPost.builder()
                .jobId(uuid)
                .jobTech("Python")
                .jobCompensation(1000000)
                .jobPosition(tempJobPosition[i])
                .jobDescription(tempJobDescription[i])
                .company(saveCompany)
                .build();
            JobPost jobpost = jobPostRepository.save(jobPost);

            saveUUID = jobpost.getJobId();
        }

        return saveUUID;
    }

    private void givenReturnMultipleJobPost() {
        UUID uuid = UUID.randomUUID();
        UUID companyUUID = UUID.randomUUID();
        String[] tempJobDescription = new String[] {"원티드랩에서 백엔드 주니어 개발자를 채용합니다.", "원티드랩에서 프론트 주니어 개발자를 채용합니다.", "프론트 주니어 개발자를 채용합니다."};
        String[] tempJobPosition = new String[] {"백엔드 주니어 개발자", "Python 주니어 개발자", "풀스택 주니어 개발자"};
        String[] companyCountry = new String[] {"한국", "태국", "중국"};
        for (int i = 0; i < 3; i++) {
            Company newCompany = Company.createCompany(companyUUID, companyCountry[i], "핀교", "원티드랩");
            Company saveCompany = companyRepository.save(newCompany);

            JobPost jobPost = JobPost.builder()
                .jobId(uuid)
                .jobTech("Python")
                .jobCompensation(1000000)
                .jobPosition(tempJobPosition[i])
                .jobDescription(tempJobDescription[i])
                .company(saveCompany)
                .build();
            jobPostRepository.save(jobPost);
        }
    }

    @BeforeEach
    @DisplayName("채용공고를 등록하기 전 회사 등록 성공")
    @Test
    public void setupSaveCompanyTest() {
        companyRepositorytest.saveCompanyTest();
    }

    @DisplayName("채용공고 등록에 성공")
    @Test
    public void saveJobTest() {

        JobPost newJobPost = jobPostRepository.save(returnJobPostMethod());

        Assertions.assertThat(newJobPost.getJobId()).isEqualTo(newJobPost.getJobId());
        Assertions.assertThat(newJobPost.getJobTech()).isEqualTo(newJobPost.getJobTech());
        Assertions.assertThat(newJobPost.getJobCompensation()).isEqualTo(newJobPost.getJobCompensation());
        Assertions.assertThat(newJobPost.getJobPosition()).isEqualTo(newJobPost.getJobPosition());
        Assertions.assertThat(newJobPost.getJobDescription()).isEqualTo(newJobPost.getJobDescription());
        Assertions.assertThat(newJobPost.getCompanyId()).isEqualTo(newJobPost.getCompanyId());

    }

    @DisplayName("등록된 채용공고를 수정")
    @Test
    public void saveJobModify() {

        JobPost oldPost = returnJobPostMethod();

        JobPost savePost = jobPostRepository.save(oldPost);

        savePost.updateJobPost("프론트 주니어 개발자", 1500000, "Python", "원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다.");

        JobPost newUpdatePost = jobPostRepository.save(savePost);

        Assertions.assertThat(oldPost.getJobTech()).isEqualTo(newUpdatePost.getJobTech());
        Assertions.assertThat(oldPost.getJobPosition()).isNotEqualTo(newUpdatePost.getJobPosition());
        Assertions.assertThat(oldPost.getJobCompensation()).isNotEqualTo(newUpdatePost.getJobCompensation());
        Assertions.assertThat(oldPost.getJobDescription()).isNotEqualTo(newUpdatePost.getJobDescription());

    }

    @DisplayName("등록된 채용공고를 삭제")
    @Test
    public void deleteJob() {
        JobPost oldPost = returnJobPostMethod();

        JobPost savePost = jobPostRepository.save(oldPost);

        jobPostRepository.deleteById(savePost.getJobId());

        Assertions.assertThat(jobPostRepository.findById(savePost.getJobId())).isEmpty();
    }

    @DisplayName("채용공고 목록 조회")
    @Test
    public void findAllJob() {

        for (int i = 0; i < 3; i++) {
            JobPost jobPost = returnJobPostMethod();
            jobPostRepository.save(jobPost);
        }
        List<JobPost> jobPostList = jobPostRepository.findAll();

        Assertions.assertThat(jobPostList).isNotEmpty();
        Assertions.assertThat(jobPostList.size()).isEqualTo(4);
    }

    @DisplayName("채용공고 검색")
    @Test
    public void searchJobPosts() {

        givenReturnMultipleJobPost();

        //when

        List<JobPost> jobPost = jpaQueryFactory.selectFrom(QJobPost.jobPost)
            .leftJoin(QJobPost.jobPost.companyId, QCompany.company)
            .fetchJoin()
            .where(
                QJobPost.jobPost.jobDescription.contains("풀스택").or
                        (QJobPost.jobPost.jobTech.contains("풀스택"))
                    .or(QJobPost.jobPost.jobPosition.contains("풀스택"))
                    .or(
                        QJobPost.jobPost.companyId.companyName.contains("풀스택")).or(
                        QJobPost.jobPost.companyId.companyArea.contains("풀스택")).or(
                        QJobPost.jobPost.companyId.companyCountry.contains("풀스택")))
            .fetch();

        //then
        Assertions.assertThat(jobPost.size()).isEqualTo(1);

    }

    @DisplayName("채용회사 id를 통해 상세 페이지를 조회")
    @Test
    public void getDetailJob() {
        UUID uuid = getJobPostUUID();

        List<JobPost> jobPosts = jpaQueryFactory
            .select(QJobPost.jobPost)
            .from(QJobPost.jobPost)
            .leftJoin(QJobPost.jobPost.companyId, QCompany.company)
            .where(QJobPost.jobPost.jobId.eq(uuid))
            .fetchJoin()
            .fetch();
        //when
        List<UUID> otherCompanyJobs = new ArrayList<>();
        for (JobPost jobPost : jobPosts) {
            otherCompanyJobs = jpaQueryFactory.select(QJobPost.jobPost.jobId)
                .from(QJobPost.jobPost)
                .where(QJobPost.jobPost.companyId.eq(jobPost.getCompanyId()))
                .fetch();
        }

        //then
        Assertions.assertThat(jobPosts.size()).isEqualTo(1);
        Assertions.assertThat(otherCompanyJobs.size()).isEqualTo(3);
    }

}
