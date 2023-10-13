package com.api.wantedpreonboardingbackend.service.dto;

import java.util.UUID;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CompanyDto {
    @AllArgsConstructor
    @Getter
    @Builder
    public static class SaveRequest {
        @NotBlank(message = "회사 국가를 입력해주세요.")
        private String companyCountry;

        @NotBlank(message = "회사 지역을 입력해주세요.")
        private String companyArea;

        @NotBlank(message = "회사 이름을 입력해주세요.")
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
