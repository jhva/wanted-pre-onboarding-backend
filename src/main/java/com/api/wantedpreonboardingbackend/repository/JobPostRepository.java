package com.api.wantedpreonboardingbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.wantedpreonboardingbackend.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, UUID>, CustomJobPostRepository {

    @Query("select  t from  JobPost t join fetch t.companyId")
    List<JobPost> findAll();

}
