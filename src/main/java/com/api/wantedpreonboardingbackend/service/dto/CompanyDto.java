package com.api.wantedpreonboardingbackend.service.dto;

import java.util.UUID;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CompanyDto {
    @AllArgsConstructor
    @Getter
    @Builder
    public static class SaveRequest {
        private String companyCountry;

        private String companyArea;

        private String companyName;
    }

    public static Company toEntity(SaveRequest saveRequest) {
        return Company.builder()
            .companyCountry(saveRequest.getCompanyCountry())
            .companyName(saveRequest.getCompanyName())
            .companyArea(saveRequest.getCompanyArea())
            .build();

    }

}
