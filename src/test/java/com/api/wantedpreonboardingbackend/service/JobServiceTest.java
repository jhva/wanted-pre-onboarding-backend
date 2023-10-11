package com.api.wantedpreonboardingbackend.service;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.api.wantedpreonboardingbackend.config.QuerydslConfig;
import com.api.wantedpreonboardingbackend.config.QuerydslTestConfig;
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.QJobPost;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.repository.CustomJobPostRepository;
import com.api.wantedpreonboardingbackend.repository.JobPostRepository;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

@ExtendWith(MockitoExtension.class)
@EnableJpaRepositories(basePackages = "com.api.wantedpreonboardingbackend.repository")
@Import(QuerydslTestConfig.class)
@EnableJpaAuditing
public class JobServiceTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobService jobService;
    private Company company;

    @BeforeEach
    void setUp() {
        jobService = new JobService(jobPostRepository, companyRepository);

    }

    @Nested
    @DisplayName("채용공고를 등록")
    class createJob {
        private JobPostDto.SaveRequest saveRequest;
        private JobPost jobPost;

        @BeforeEach
        void setUp() {
            company = Company.builder()
                .companyCountry("한국")
                .companyName("원티드랩")
                .companyArea("강남").build();

        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessJobPost {
            @Test
            @DisplayName("채용공고 등록 성공")
            void CreateJobPostSuccess() {
                Company originCompany = Company.builder()
                    .companyId(UUID.randomUUID())
                    .companyCountry("한국")
                    .companyName("원티드랩")
                    .companyArea("강남").build();

                JobPost jobpost1 = JobPost.builder()
                    .company(originCompany)
                    .jobTech("Python")
                    .jobPosition("백엔드 주니어 개발자")
                    .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
                    .jobCompensation(1000000)
                    .build();
                when(jobPostRepository.save(jobpost1)).thenReturn(jobpost1);
                //then
                JobPost save = jobPostRepository.save(jobpost1);
                Assertions.assertThat(save.getJobDescription()).isEqualTo("원티드랩에서 백엔드 주니어 개발자를 채용합니다.");
                Assertions.assertThat(save.getJobCompensation()).isEqualTo(1000000);
                Assertions.assertThat(save.getJobTech()).isEqualTo("Python");
                Assertions.assertThat(save.getJobPosition()).isEqualTo("백엔드 주니어 개발자");

            }

        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailJobPost {
            @Test
            @DisplayName("채용공고 등록 실패")
            void CreateJobPostFail() {
                Exception exception = assertThrows(NoSuchElementException.class, () -> {
                    jobService.jobPostCreate(null);
                });
                Assertions.assertThat(exception.getMessage()).isEqualTo("saveRequest cannot be null"); // 예외 메시지 일치시킴

            }

        }
    }

    @Nested
    @DisplayName("채용공고를 수정")
    class modifyJob {
        private UUID jobId = UUID.randomUUID();
        private JobPost jobPost;
        private JobPostDto.SaveRequest saveRequest;

        @Nested
        @DisplayName("정상 케이스")
        class JobPostSuccess {
            @BeforeEach
            void setup() {
                jobPost = JobPost.builder()
                    .jobId(jobId)
                    .jobTech("JavaScript")
                    .build();

                saveRequest = JobPostDto.SaveRequest.builder()
                    .jobTech("Python")
                    .build();
            }

            @Test
            @DisplayName("채용공고 수정 성공")
            void modifyJobSuccess() {

                when(jobPostRepository.save(any(JobPost.class))).thenReturn(jobPost);
                when(jobPostRepository.findById(jobPost.getJobId())).thenReturn(Optional.of(jobPost));

                JobPost updateJobPost = jobService.jobPostUpdate(jobPost.getJobId(), saveRequest);

                Assertions.assertThat(updateJobPost.getJobTech()).isEqualTo(saveRequest.getJobTech());

            }

        }

        @Nested
        @DisplayName("비정상 케이스")
        class JobPostFail {

            @BeforeEach
            void setup() {
                jobId = UUID.randomUUID();
                jobPost = JobPost.builder()
                    .jobId(jobId)
                    .jobTech("JavaScript")
                    .build();

                saveRequest = JobPostDto.SaveRequest.builder()
                    .jobTech("Python")
                    .build();
            }

            @Test
            @DisplayName("해당되는 id 없을 경우 수정 실패")
            void modifyJobFail() {
                UUID random = UUID.randomUUID();
                when(jobPostRepository.findById(random)).thenThrow(
                    new NoSuchElementException(String.format("not found jobPost %s", random.toString())));

                Exception exception = assertThrows(NoSuchElementException.class, () -> {
                    jobService.jobPostUpdate(random, saveRequest);

                });
                Assertions.assertThat(exception.getMessage()).isEqualTo(String.format("not found jobPost %s", random.toString()));
            }

        }

    }

    @Nested
    @DisplayName("채용공고를 삭제")
    class deleteJob {
        private JobPostDto.SaveRequest saveRequest;
        private JobPost jobPost;
        private UUID uuid = UUID.randomUUID();

        @BeforeEach
        void setUp() {

        }

        @Nested
        @DisplayName("정상 케이스")
        class DeleteJobPostSuccess {

            @Test
            @DisplayName("채용공고 삭제 성공")
            void deleteJobSuccess() {

                // 생성된 JobPost 객체
                Company originCompany = Company.builder()
                    .companyId(UUID.randomUUID())
                    .companyCountry("한국")
                    .companyName("원티드랩")
                    .companyArea("강남").build();

                JobPost jobpost1 = JobPost.builder()
                    .jobId(UUID.randomUUID())
                    .company(originCompany)
                    .jobTech("Python")
                    .jobPosition("백엔드 주니어 개발자")
                    .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
                    .jobCompensation(1000000)
                    .build();

                when(jobPostRepository.findById(jobpost1.getJobId())).thenReturn(Optional.of(jobpost1));
                jobService.deleteJobPost(jobpost1.getJobId());

                verify(jobPostRepository, times(1)).deleteById(jobpost1.getJobId());

            }

        }

        @Nested
        @DisplayName("비정상 케이스")
        class DeleteJobPostFail {

            @Test
            @DisplayName("해당되는 id 없을 경우 삭제 실패")
            void deleteJobFail() {
                when(jobPostRepository.findById(uuid)).thenThrow(
                    new NoSuchElementException(String.format("not found jobPost %s", uuid.toString())));

                assertThrows(NoSuchElementException.class, () -> {
                    jobService.deleteJobPost(uuid);
                });

                verify(jobPostRepository, never()).deleteById(any());
            }

        }

    }

    @Nested
    @DisplayName("채용공고 목록 조회")
    class JobPostFind {
        @DisplayName("채용공고를 모두 조회 성공")
        @Test
        void findAllJobPost() {
            List<JobPost> jobPostList = Arrays.asList(
                createJobPost("Python", 1000000, "백엔드 주니어 개발자"),
                createJobPost("Java", 1200000, "풀스택 개발자"),
                createJobPost("JavaScript", 900000, "프론트엔드 개발자")
            );

            when(jobPostRepository.findAll()).thenReturn(jobPostList);

            List<JobPostDto.JobAllPost> result = jobService.findAllJobPosts();

            // Then
            assertEquals(jobPostList.size(), result.size());

            assertAll(
                () -> assertEquals(jobPostList.get(0).getJobTech(), result.get(0).getJobTech()),
                () -> assertEquals(jobPostList.get(1).getJobTech(), result.get(1).getJobTech()),
                () -> assertEquals(jobPostList.get(2).getJobTech(), result.get(2).getJobTech())
            );
        }

    }

    @Nested
    @DisplayName("채용공고 검색")
    class JobPostSearch {
        private JobPostDto.SaveRequest saveRequest;
        private JobPost jobPost;

        @Nested
        @DisplayName("정상 케이스")
        class JobPostSearchSuccess {

            @BeforeEach
            void setUp() {
                company = Company.builder()
                    .companyId(UUID.randomUUID())
                    .companyCountry("한국")
                    .companyName("원티드랩")
                    .companyArea("강남").build();

                saveRequest = JobPostDto.SaveRequest.builder()
                    .jobTech("Python")
                    .jobCompensation(1000000)
                    .jobPosition("백엔드 주니어 개발자")
                    .company(company.getCompanyId())
                    .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
                    .build();
                jobPost = JobPostDto.toEntity(saveRequest, company);
            }

            @DisplayName("Python 채용공고 검색 성공")
            @Test
            void searchJobSuccess() {
                String searchKeyword = "Python";
                JobService jobService = new JobService(jobPostRepository, companyRepository);

                // 목 객체의 동작을 정의
                JobPost jobPost = createJobPost("Python", 1000000, "백엔드 주니어 개발자");
                when(jobPostRepository.findByJobPost(searchKeyword)).thenReturn(Collections.singletonList(jobPost));

                // findJobSearchPost 메서드 테스트
                List<JobPostDto.JobAllPost> result = jobService.findJobSearchPost(searchKeyword);

                Assertions.assertThat(result.get(0).getJobTech()).isEqualTo("Python");

            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class NotFoundSearchJobPost {

            @DisplayName("검색 결과가 없을 경우")
            @Test
            void searchNotFound() {
                // 검색 결과가 없을 때 검색을 수행합니다.
                String searchKeyword = "Python";
                JobService jobService = new JobService(jobPostRepository, companyRepository);

                // 목 객체의 동작을 정의
                JobPost jobPost = createJobPost("Java", 1000000, "백엔드 주니어 개발자");
                when(jobPostRepository.findByJobPost(searchKeyword)).thenReturn(Collections.singletonList(jobPost));

                // findJobSearchPost 메서드 테스트
                List<JobPostDto.JobAllPost> result = jobService.findJobSearchPost(searchKeyword);

                Assertions.assertThat(result.get(0).getJobTech()).isNotEqualTo(searchKeyword);
            }
        }
    }

    @Nested
    @DisplayName("상세 채용 공고")
    class JobPostDetail {

        @Nested
        @DisplayName("정상 케이스")
        class JobDetailSuccess {
            private UUID id = UUID.randomUUID();
            private UUID uuid1 = UUID.randomUUID();
            private UUID uuid2 = UUID.randomUUID();
            private UUID uuid3 = UUID.randomUUID();

            private JobPostDto.JobDetailPost detailPost;

            @BeforeEach
            void setUp() {
                detailPost = JobPostDto.JobDetailPost.builder()
                    .jobTech("Java")
                    .id(id)
                    .jobPosition("Software Engineer")
                    .jobDescription("Full Stack Developer")
                    .jobCompensation(100000)
                    .companyName("Example Inc.")
                    .companyArea("Technology")
                    .companyCountry("United States")
                    .otherJobDocs(List.of(uuid1, uuid2, uuid3))
                    .build();

            }

            @Test
            @DisplayName("uuid 를 통해 상세목록을 성공적으로 가져옴")
            void testGetJobDetailSuccess() {
                JobPost job = JobPostDto.fromEntityDtoToDto(detailPost);

                when(jobPostRepository.findByDetailJob(job.getJobId())).thenReturn(detailPost);

                JobPostDto.JobDetailPost jobDetailPost1 = jobPostRepository.findByDetailJob(job.getJobId());

                Assertions.assertThat(jobDetailPost1.getJobDescription()).isEqualTo(detailPost.getJobDescription());

            }

        }

    }

    private JobPost createJobPost(String tech, int compensation, String position) {
        Company newCompany = Company.builder()
            .companyCountry("한국")
            .companyName("원티드랩")
            .companyArea("강남")
            .build();

        return JobPost.builder()
            .jobTech(tech)

            .jobCompensation(compensation)
            .jobPosition(position)
            .company(newCompany)
            .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
            .build();
    }
}
