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
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.UserService;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/create-user")
    public ApiResponse<Object> createUser(@RequestBody final UserDto.SaveRequest saveRequestDto) {
        userService.createUser(saveRequestDto);
        return ApiResponse.<Object>builder()
            .statusCode(HttpStatus.CREATED.value())
            .msg("success")
            .build();
    }

}
