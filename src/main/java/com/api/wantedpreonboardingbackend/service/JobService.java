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
import com.api.wantedpreonboardingbackend.repository.CustomJobPostRepository;
import com.api.wantedpreonboardingbackend.repository.CustomJobPostRepositoryImpl;
import com.api.wantedpreonboardingbackend.repository.JobPostRepository;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class JobService {
    private final JobPostRepository jobPostRepository;
    private final CustomJobPostRepository customJobPostRepository;

    public JobPostDto.SaveResponseJobDto jobPostCreate(JobPostDto.SaveRequest saveRequest) {
        if (saveRequest == null) {
            throw new NoSuchElementException("saveRequest cannot be null");
        }

        JobPost newJobPost = JobPostDto.toEntity(saveRequest);

        JobPost savePost = jobPostRepository.save(newJobPost);

        return JobPostDto.toResponseDtoFromEntity(savePost);
    }

    public JobPost jobPostUpdate(UUID jobPostId, JobPostDto.SaveRequest updateDto) {
        JobPost getJobPost = jobPostRepository.findById(jobPostId).orElseThrow(() -> new NoSuchElementException(
            String.format("not found jobPost %s", jobPostId)
        ));

        getJobPost.updateJobPost(updateDto.getJobPosition(), updateDto.getJobCompensation(), updateDto.getJobTech(),
            updateDto.getJobDescription());

        return jobPostRepository.save(getJobPost);
    }

    public void deleteJobPost(UUID jobId) {
        JobPost getJobPost = jobPostRepository.findById(jobId).orElseThrow(() -> new NoSuchElementException(
            String.format("not found jobPost %s", jobId)
        ));
        jobPostRepository.deleteById(getJobPost.getJobId());
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
        JobPost getJobPost = jobPostRepository.findById(searchUUID).orElseThrow(() -> new NoSuchElementException(
            String.format("not found jobPost %s", searchUUID)
        ));

        return jobPostRepository.findByDetailJob(getJobPost.getJobId());

    }
}
