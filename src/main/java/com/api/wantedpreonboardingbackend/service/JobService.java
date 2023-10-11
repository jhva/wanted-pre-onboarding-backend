package com.api.wantedpreonboardingbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.QCompany;
import com.api.wantedpreonboardingbackend.entity.QJobPost;
import com.api.wantedpreonboardingbackend.exception.job.JobNotExist;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.repository.CustomJobPostRepository;
import com.api.wantedpreonboardingbackend.repository.CustomJobPostRepositoryImpl;
import com.api.wantedpreonboardingbackend.repository.JobPostRepository;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class JobService {
    private final JobPostRepository jobPostRepository;

    private final CompanyRepository companyRepository;

    private Company getCompany(UUID id) {

        return companyRepository.findById(id).orElseThrow(JobNotExist::new);
    }

    private JobPost getJobPost(UUID id) {

        return jobPostRepository.findById(id).orElseThrow(JobNotExist::new);
    }

    public JobPostDto.SaveResponseJobDto jobPostCreate(JobPostDto.SaveRequest saveRequest) {
        if (saveRequest == null) {
            throw new NoSuchElementException("saveRequest cannot be null");
        }
        Company company = getCompany(saveRequest.getCompany());

        JobPost newJobPost = JobPostDto.toEntity(saveRequest, company);

        JobPost savePost = jobPostRepository.save(newJobPost);

        return JobPostDto.toResponseDtoFromEntity(savePost);
    }

    public JobPost jobPostUpdate(UUID jobPostId, JobPostDto.SaveRequest updateDto) {
        JobPost getJobPost = getJobPost(jobPostId);

        getJobPost.updateJobPost(updateDto.getJobPosition(), updateDto.getJobCompensation(), updateDto.getJobTech(),
            updateDto.getJobDescription());

        return jobPostRepository.save(getJobPost);
    }

    public void deleteJobPost(UUID jobId) {
        JobPost jobpost = getJobPost(jobId);
        jobPostRepository.deleteById(jobpost.getJobId());
    }

    public List<JobPostDto.JobAllPost> findAllJobPosts() {

        List<JobPost> jobsAll = jobPostRepository.findAll();
        List<JobPostDto.JobAllPost> jobsAllPost = jobsAll.stream()
            .map(i -> {
                Company company = i.getCompanyId();
                return JobPostDto.JobAllPost.builder()
                    .jobTech(i.getJobTech())
                    .jobCompensation(i.getJobCompensation())
                    .jobPosition(i.getJobPosition())
                    .companyCountry(company.getCompanyCountry())
                    .companyName(company.getCompanyName())
                    .jobDescription(i.getJobDescription())
                    .companyArea(company.getCompanyArea())
                    .build();
            })
            .collect(Collectors.toList());

        return jobsAllPost;
    }

    public List<JobPostDto.JobAllPost> findJobSearchPost(final String search) {

        List<JobPost> jobsAll = jobPostRepository.findByJobPost(search);
        List<JobPostDto.JobAllPost> jobsAllPost = jobsAll.stream()
            .map(i -> {
                Company company = i.getCompanyId();
                return JobPostDto.JobAllPost.builder()
                    .jobTech(i.getJobTech())
                    .jobCompensation(i.getJobCompensation())
                    .jobPosition(i.getJobPosition())
                    .companyCountry(company.getCompanyCountry())
                    .companyName(company.getCompanyName())
                    .jobDescription(i.getJobDescription())
                    .companyArea(company.getCompanyArea())
                    .build();
            })
            .collect(Collectors.toList());

        return jobsAllPost;
    }

    public JobPostDto.JobDetailPost detailFindJobPost(final UUID searchUUID) {
        JobPost getJobPost = getJobPost(searchUUID);
        return jobPostRepository.findByDetailJob(getJobPost.getJobId());

    }
}
