package com.api.wantedpreonboardingbackend.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    CONFLICT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버와 연결이 끊어졌습니다."),
    NOT_EXIST_JOB(HttpStatus.NOT_FOUND, "채용공고가 존재하지 않습니다."),
    NOT_EXIST_COMPANY(HttpStatus.NOT_FOUND, "해당 회사가 존재하지 않습니다."),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."),

    DUPLICATE_APPLY(HttpStatus.CONFLICT, "이미 해당 직무에 지원한 기록이 있습니다.");

    private final HttpStatus status;
    private final String description;
}
