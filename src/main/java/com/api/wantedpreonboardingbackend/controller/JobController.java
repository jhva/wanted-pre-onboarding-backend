package com.api.wantedpreonboardingbackend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.wantedpreonboardingbackend.common.ApiResponse;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.JobService;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class JobController {
    private final JobService jobService;

    /**
     * 게시글을 생성
     *
     * @param saveRequestDto 생성할 게시글 정보
     * @return ApiResponse
     */
    @PostMapping(path = "/create-job")
    public ApiResponse<Object> createJob(@RequestBody @Valid final JobPostDto.SaveRequest saveRequestDto) {
        jobService.jobPostCreate(saveRequestDto);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.CREATED.value())
            .msg("success")
            .build();
    }

    @PatchMapping(path = "/update-job/{jobId}")
    public ApiResponse<Object> updateJob(@PathVariable final UUID jobId, @RequestBody final JobPostDto.SaveRequest saveRequestDto) {
        jobService.jobPostUpdate(jobId, saveRequestDto);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.OK.value())
            .msg("success")
            .build();
    }

    @DeleteMapping(path = "/delete-job/{jobId}")
    public ApiResponse<Object> deleteJob(@PathVariable final UUID jobId) {
        jobService.deleteJobPost(jobId);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.OK.value())
            .msg("success")
            .build();
    }

    @GetMapping(path = "/all-job")
    public ApiResponse<Object> getAllJob() {
        List<JobPostDto.JobAllPost> jobList = jobService.findAllJobPosts();
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.OK.value())
            .data(jobList)
            .msg("success")
            .build();
    }

    @GetMapping(path = "/search-job/{search}")
    public ApiResponse<Object> getSearchJob(@PathVariable final String search) {
        List<JobPostDto.JobAllPost> jobList = jobService.findJobSearchPost(search);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.OK.value())
            .data(jobList)
            .msg("success")
            .build();
    }

    @GetMapping(path = "/detail-job/{jobId}")
    public ApiResponse<Object> getDetailJob(@PathVariable final UUID jobId) {
        JobPostDto.JobDetailPost jobList = jobService.detailFindJobPost(jobId);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.OK.value())
            .data(jobList)
            .msg("success")
            .build();
    }
}
