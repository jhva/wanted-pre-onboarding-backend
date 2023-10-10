package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.wantedpreonboardingbackend.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, UUID>, CustomJobPostRepository {

}
