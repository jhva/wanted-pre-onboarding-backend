package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.wantedpreonboardingbackend.entity.ApplyHistory;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;

public interface ApplyRepository extends JpaRepository<ApplyHistory, UUID> {
    @Query("SELECT COUNT(apply) FROM ApplyHistory apply WHERE apply.user.userId = :userId AND apply.jobPost.jobId = :jobId")
    int countByUserIdAndJobId(@Param("userId") UUID userId, @Param("jobId") UUID jobId);

}
