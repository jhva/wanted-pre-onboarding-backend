package com.api.wantedpreonboardingbackend.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.wantedpreonboardingbackend.entity.ApplyHistory;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.exception.apply.DuplicateApplyException;
import com.api.wantedpreonboardingbackend.exception.job.JobNotExist;
import com.api.wantedpreonboardingbackend.exception.user.UserNotExist;
import com.api.wantedpreonboardingbackend.repository.ApplyRepository;
import com.api.wantedpreonboardingbackend.repository.JobPostRepository;
import com.api.wantedpreonboardingbackend.repository.UserRepository;
import com.api.wantedpreonboardingbackend.service.dto.ApplyHistoryDto;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ApplyHistoryService {

    private final ApplyRepository applyRepository;

    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;

    public ApplyHistory applyToUserFromJob(ApplyHistoryDto.SaveRequest saveRequestDto) {

        JobPost jobPost = jobPostRepository.findById(saveRequestDto.getJobId()).orElseThrow(JobNotExist::new);
        User user = userRepository.findById(saveRequestDto.getUserId()).orElseThrow(UserNotExist::new);

        boolean hasAlready = hasAlreadyApplied(user.getUserId(), jobPost.getJobId());

        if (hasAlready)
            throw new DuplicateApplyException();

        return applyRepository.save(ApplyHistory.builder()
            .jobPost(jobPost)
            .user(user)
            .build());
    }

    private boolean hasAlreadyApplied(UUID userId, UUID jobId) {
        return applyRepository.countByUserIdAndJobId(userId, jobId) >= 1;
    }

}
