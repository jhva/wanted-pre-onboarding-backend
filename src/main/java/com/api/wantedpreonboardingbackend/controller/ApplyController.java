package com.api.wantedpreonboardingbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.wantedpreonboardingbackend.common.ApiResponse;
import com.api.wantedpreonboardingbackend.service.ApplyHistoryService;
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.dto.ApplyHistoryDto;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ApplyController {

    private final ApplyHistoryService applyHistoryService;

    @PostMapping(path = "/apply")
    public ApiResponse<Object> createApplyOrDuplicate(@RequestBody final ApplyHistoryDto.SaveRequest saveRequestDto) {
        applyHistoryService.applyToUserFromJob(saveRequestDto);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.CREATED.value())
            .msg("success")
            .build();
    }
}
