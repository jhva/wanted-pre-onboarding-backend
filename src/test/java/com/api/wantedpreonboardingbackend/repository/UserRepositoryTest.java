package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

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
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

@DataJpaTest
@ContextConfiguration(classes = {UserRepositoryTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = "com.api.wantedpreonboardingbackend.repository")
@EntityScan("com.api.wantedpreonboardingbackend.entity")
@Import(QuerydslConfig.class)
@EnableJpaAuditing
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobPostRepository jobPostRepository;

    @DisplayName("사용자 등록")
    @Test
    public void saveUserTest() {

        User user = User.builder()
            .userName("김정훈님").build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser.getUserName()).isEqualTo(saveUser.getUserName());

    }

}
