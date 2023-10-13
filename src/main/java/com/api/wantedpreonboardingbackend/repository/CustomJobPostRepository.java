package com.api.wantedpreonboardingbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;
import com.querydsl.core.Tuple;

public interface CustomJobPostRepository {
    List<JobPost> findByJobPost(String jobSearchQuery);

    JobPostDto.JobDetailPost findByDetailJob(UUID uuid);

}
