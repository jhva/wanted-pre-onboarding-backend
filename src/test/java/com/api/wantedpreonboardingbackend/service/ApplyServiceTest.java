package com.api.wantedpreonboardingbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import com.api.wantedpreonboardingbackend.config.QuerydslTestConfig;
import com.api.wantedpreonboardingbackend.entity.ApplyHistory;
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.exception.apply.DuplicateApplyException;
import com.api.wantedpreonboardingbackend.repository.ApplyRepository;
import com.api.wantedpreonboardingbackend.repository.JobPostRepository;
import com.api.wantedpreonboardingbackend.repository.UserRepository;
import com.api.wantedpreonboardingbackend.service.dto.ApplyHistoryDto;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

@ExtendWith(MockitoExtension.class)
@EnableJpaRepositories(basePackages = "com.api.wantedpreonboardingbackend.repository")
@Import(QuerydslTestConfig.class)
@EnableJpaAuditing
public class ApplyServiceTest {

    @Mock
    private ApplyRepository applyRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JobPostRepository jobPostRepository;

    @InjectMocks
    private ApplyHistoryService applyHistoryService;

    private UUID userId;
    private UUID jobId;
    private JobPost jobPost;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        jobId = UUID.randomUUID();
        jobPost = JobPost.builder().jobId(jobId).build();
        user = User.builder().id(userId).build();

        when(jobPostRepository.findById(jobId)).thenReturn(Optional.of(jobPost));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("정상적으로 채용 공고 등록에 성공한다")
    @Transactional
    void successApplyToUserFromJobPost() {
        ApplyHistory applyHistory = ApplyHistory.builder()
            .user(user)
            .jobPost(jobPost)
            .build();

        when(applyRepository.save(any(ApplyHistory.class))).thenReturn(applyHistory);
        ApplyHistoryService applyHistoryService = new ApplyHistoryService(applyRepository, jobPostRepository, userRepository);

        ApplyHistory saveApply = applyHistoryService.applyToUserFromJob(
            ApplyHistoryDto.SaveRequest.builder()
                .userId(user.getUserId()).jobId(jobPost.getJobId()).build()
        );
        Assertions.assertThat(saveApply.getUser().getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    @DisplayName("이미 지원한 채용 공고에 등록시 실패")
    @Transactional
    void failsWhenApplyingToAlreadyAppliedJobPost() {

        //given
        ApplyHistory applyHistory = ApplyHistory.builder()
            .user(user)
            .jobPost(jobPost)
            .build();

        when(applyRepository.save(any(ApplyHistory.class)))
            .thenReturn(applyHistory)
            .thenReturn(applyHistory)
            .thenThrow(DuplicateApplyException.class);

        ApplyHistoryService applyHistoryService = new ApplyHistoryService(applyRepository, jobPostRepository, userRepository);

        applyHistoryService.applyToUserFromJob(
            ApplyHistoryDto.SaveRequest.builder()
                .userId(user.getUserId())
                .jobId(jobPost.getJobId())
                .build()
        );

        applyHistoryService.applyToUserFromJob(
            ApplyHistoryDto.SaveRequest.builder()
                .userId(user.getUserId())
                .jobId(jobPost.getJobId())
                .build()
        );

        //when
        when(applyRepository.countByUserIdAndJobId(user.getUserId(), jobPost.getJobId())).thenThrow(
            new DuplicateApplyException());

        //then
        Exception exception = assertThrows(DuplicateApplyException.class, () -> {
            applyHistoryService.applyToUserFromJob(
                ApplyHistoryDto.SaveRequest.builder()
                    .userId(user.getUserId())
                    .jobId(jobPost.getJobId())
                    .build()
            );
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo(new DuplicateApplyException().getMessage());
    }
}
