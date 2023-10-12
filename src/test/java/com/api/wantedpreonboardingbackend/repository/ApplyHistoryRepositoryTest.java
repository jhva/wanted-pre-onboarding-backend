package com.api.wantedpreonboardingbackend.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.api.wantedpreonboardingbackend.config.QuerydslConfig;
import com.api.wantedpreonboardingbackend.entity.ApplyHistory;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

@DataJpaTest
@ContextConfiguration(classes = {ApplyHistoryRepositoryTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = "com.api.wantedpreonboardingbackend.repository")
@EntityScan("com.api.wantedpreonboardingbackend.entity")
@Import(QuerydslConfig.class)
@EnableJpaAuditing
public class ApplyHistoryRepositoryTest {
    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobPostRepository jobPostRepository;

    @DisplayName("채용공고 id,사용자 id를 통해 채용공고에 등록")
    @Test
    public void applyToJobPost() {
        User user = UserDto.toEntity(UserDto.SaveRequest.builder()
            .userName("김정훈님").build());
        User saveUser = userRepository.save(user);

        JobPost newJobPost = jobPostRepository.save(JobPost.builder()
            .jobTech("Java")
            .build());

        ApplyHistory applyHistory = applyRepository.save(ApplyHistory.builder()
            .jobPost(newJobPost)
            .user(saveUser)
            .build());

        ApplyHistory getApplyHistory = applyRepository.findById(applyHistory.getApplyId()).orElse(null);

        Assertions.assertThat(getApplyHistory.getApplyId()).isEqualTo(applyHistory.getApplyId());
        Assertions.assertThat(getApplyHistory.getUser()).isEqualTo(applyHistory.getUser());
        Assertions.assertThat(getApplyHistory.getJobPost()).isEqualTo(applyHistory.getJobPost());
    }

    @DisplayName("이미 해당 채용공고에 등록한 사용자 데이터의 개수를 가져온다")
    @Test
    public void userAlreadyAppliedToJobPost() {
        User user = UserDto.toEntity(UserDto.SaveRequest.builder()
            .userName("김정훈님").build());
        User saveUser = userRepository.save(user);

        JobPost newJobPost = jobPostRepository.save(JobPost.builder()
            .jobTech("Java")
            .build());

        ApplyHistory applyHistory = applyRepository.save(ApplyHistory.builder()
            .jobPost(newJobPost)
            .user(saveUser)
            .build());
        int existingApplyHistory = applyRepository.countByUserIdAndJobId(applyHistory.getUser().getUserId(), applyHistory.getJobPost().getJobId());
        Assertions.assertThat(existingApplyHistory).isEqualTo(1);
    }

}
