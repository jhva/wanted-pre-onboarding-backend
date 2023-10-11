package com.api.wantedpreonboardingbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.api.wantedpreonboardingbackend.common.ApiResponse;
import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 게시글을 생성
     *
     * @param saveRequestDto 생성할 게시글 정보
     * @return ApiResponse
     */
    @PostMapping(path = "/create-company")
    public ApiResponse<Object> createPost(@RequestBody final CompanyDto.SaveRequest saveRequestDto) {
        companyService.createCompany(saveRequestDto);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.CREATED.value())
            .msg("success")
            .build();
    }

}
