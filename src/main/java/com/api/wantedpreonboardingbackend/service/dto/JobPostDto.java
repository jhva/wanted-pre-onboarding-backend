package com.api.wantedpreonboardingbackend.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JobPostDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class SaveRequest {

        private String jobTech;
        private String jobPosition;
        private String jobDescription;
        private int jobCompensation;
        private UUID company;

    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class JobAllPost {

        private String jobTech;
        private String jobPosition;
        private String jobDescription;
        private int jobCompensation;
        private String companyName;
        private String companyArea;
        private String companyCountry;

    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class SaveResponseJobDto {

        private String jobTech;
        private UUID id;
        private String jobPosition;
        private String jobDescription;
        private int jobCompensation;
        private UUID company_id;

    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class JobDetailPost {

        private UUID id;
        private String jobTech;
        private String jobPosition;
        private String jobDescription;
        private int jobCompensation;
        private String companyName;
        private String companyArea;
        private String companyCountry;

        private List<UUID> otherJobDocs;

    }

    public static JobPost toEntity(SaveRequest saveRequest, Company company) {
        return JobPost.builder()
            .jobTech(saveRequest.getJobTech())
            .jobDescription(saveRequest.getJobDescription())
            .jobPosition(saveRequest.getJobPosition())
            .company(company)
            .jobCompensation(saveRequest.getJobCompensation()).build();

    }

    public static JobPostDto.SaveResponseJobDto toResponseDtoFromEntity(JobPost jobPost) {
        return SaveResponseJobDto.builder()
            .id(jobPost.getJobId())
            .jobTech(jobPost.getJobTech())
            .jobDescription(jobPost.getJobDescription())
            .jobPosition(jobPost.getJobPosition())
            .company_id(jobPost.getCompanyId().getCompanyId())
            .jobCompensation(jobPost.getJobCompensation()).build();

    }

    public static JobPost fromEntityDtoToDto(JobPostDto.JobDetailPost jobDetailPost) {
        Company company = Company.builder()
            .companyArea(jobDetailPost.getCompanyArea())
            .companyName(jobDetailPost.getCompanyName())
            .companyCountry(jobDetailPost.getCompanyCountry()).build();
        return JobPost.builder()
            .jobId(jobDetailPost.getId())
            .company(company)
            .jobTech(jobDetailPost.getJobTech())
            .jobCompensation(jobDetailPost.getJobCompensation())
            .jobPosition(jobDetailPost.getJobPosition())
            .build();

    }

}
