package com.api.wantedpreonboardingbackend.service.dto;

import java.util.UUID;

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

}
