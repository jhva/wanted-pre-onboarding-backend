package com.api.wantedpreonboardingbackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.stereotype.Repository;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.QCompany;
import com.api.wantedpreonboardingbackend.entity.QJobPost;
import com.api.wantedpreonboardingbackend.repository.CustomJobPostRepository;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CustomJobPostRepositoryImpl implements CustomJobPostRepository {

    private final JPAQueryFactory queryFactory;
    private final QJobPost qJobPost = QJobPost.jobPost;
    private final QCompany qCompany = QCompany.company;

    @Override
    public List<JobPost> findByJobPost(String jobSearchQuery) {
        List<JobPost> jobPost = queryFactory.selectFrom(qJobPost) // 수정: 대문자 시작
            .leftJoin(qJobPost.companyId, qCompany) // 수정: 대문자 시작
            .where(
                qJobPost.jobTech.contains(jobSearchQuery)
                    .or(qJobPost.jobDescription.contains(jobSearchQuery))
                    .or(qJobPost.jobPosition.contains(jobSearchQuery))
                    .or(qJobPost.companyId.companyName.contains(jobSearchQuery))
                    .or(qJobPost.companyId.companyArea.contains(jobSearchQuery))
                    .or(qJobPost.companyId.companyCountry.contains(jobSearchQuery))
            )
            .fetch();
        return jobPost;
    }

    @Override
    public JobPostDto.JobDetailPost findByDetailJob(UUID uuid) {
        JobPost mainJobPost = queryFactory
            .selectFrom(QJobPost.jobPost)
            .leftJoin(QJobPost.jobPost.companyId, QCompany.company)
            .where(QJobPost.jobPost.jobId.eq(uuid))
            .fetchOne();

        if (mainJobPost == null) {
            return null;
        }

        Company companyId = mainJobPost.getCompanyId();

        List<UUID> otherCompanyJobs = queryFactory.select(QJobPost.jobPost.jobId)
            .from(QJobPost.jobPost)
            .where(QJobPost.jobPost.companyId.eq(companyId))
            .fetch();

        return JobPostDto.JobDetailPost.builder()
            .id(mainJobPost.getJobId())
            .jobCompensation(mainJobPost.getJobCompensation())
            .jobDescription(mainJobPost.getJobDescription())
            .jobPosition(mainJobPost.getJobPosition())
            .jobTech(mainJobPost.getJobTech())
            .companyCountry(mainJobPost.getCompanyId().getCompanyCountry())
            .companyArea(mainJobPost.getCompanyId().getCompanyArea())
            .companyName(mainJobPost.getCompanyId().getCompanyName())
            .otherJobDocs(otherCompanyJobs)
            .build();
    }

}
