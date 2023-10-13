package com.api.wantedpreonboardingbackend.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ApiResponse<T> {

    private String msg;
    private Integer statusCode;

    private T data;

    /**
     * 응답 객체 생성.
     *
     * @param <T>        데이터 타입.
     * @param statusCode 응답 코드.
     * @param msg        응답 메시지.
     * @return ApiResponse 객체.
     */
    public static <T> ApiResponse<T> res(final Integer statusCode, final String msg) {
        return ApiResponse.<T>builder()
            .statusCode(statusCode)
            .msg(msg)
            .build();
    }

    /**
     * 응답 객체 생성.
     *
     * @param <T>        데이터 타입.
     * @param statusCode 응답 코드.
     * @param msg        응답 메시지.
     * @param data       데이터.
     * @return ApiResponse 객체.
     */
    public static <T> ApiResponse<T> res(final Integer statusCode, final String msg, final T data) {
        return ApiResponse.<T>builder()
            .statusCode(statusCode)
            .msg(msg)
            .data(data)
            .build();
    }
}