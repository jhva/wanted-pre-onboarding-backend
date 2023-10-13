package com.api.wantedpreonboardingbackend.service.dto;

import java.util.UUID;

import com.api.wantedpreonboardingbackend.entity.ApplyHistory;
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ApplyHistoryDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class SaveRequest {
        private UUID jobId;

        private UUID userId;

    }

}
