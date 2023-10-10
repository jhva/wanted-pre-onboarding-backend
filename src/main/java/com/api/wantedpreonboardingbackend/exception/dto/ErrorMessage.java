package com.api.wantedpreonboardingbackend.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    CONFLICT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버와 연결이 끊어졌습니다."),
    NOT_EXIST_JOB(HttpStatus.NOT_FOUND, "채용공고가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String description;
}
