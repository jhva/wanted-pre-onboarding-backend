package com.api.wantedpreonboardingbackend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import com.api.wantedpreonboardingbackend.config.QuerydslTestConfig;
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.repository.JobPostRepository;
import com.api.wantedpreonboardingbackend.repository.UserRepository;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("유저 등록")
    class createUser {
        private UserDto.SaveRequest saveRequest;

        @BeforeEach
        void setUp() {
            saveRequest = UserDto.SaveRequest.builder()
                .userName("김정훈님")
                .build();

        }

        @Test
        @DisplayName("정상적으로 유저 등록 성공")
        void successCreateCompany() {

            User user = UserDto.toEntity(saveRequest);
            // When
            when(userRepository.save(any(User.class))).thenReturn(user);
            UserService userService = new UserService(userRepository); //

            User saveUser = userService.createUser(saveRequest);

            Assertions.assertThat(saveUser.getUserName()).isEqualTo(saveRequest.getUserName());
        }

    }
}
