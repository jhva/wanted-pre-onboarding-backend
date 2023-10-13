package com.api.wantedpreonboardingbackend.service.dto;

import java.util.UUID;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class SaveRequest {
        private String userName;
    }

    public static User toEntity(UserDto.SaveRequest saveRequest) {
        return User.builder()
            .userName(saveRequest.getUserName())
            .build();
    }
}
