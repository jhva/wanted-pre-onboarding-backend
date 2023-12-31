package com.api.wantedpreonboardingbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.wantedpreonboardingbackend.common.ApiResponse;

/**
 * @apiNote 유효성 검증에 대한 예외처리를 담당하는 클래스
 */
@ControllerAdvice
public class ValidExceptionController {

    /**
     * MethodArgumentNotValidException 예외를 처리하는 핸들러
     *
     * @param exception MethodArgumentNotValidException 객체
     * @return ApiResponse 객체를 ResponseEntity 에 담아 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> processValidationError(final MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();

        final StringBuilder builder = new StringBuilder();

        for (final FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        final ApiResponse<String> apiResponse = ApiResponse.<String>builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .msg("유효성 검증 실패")
            .data(builder.toString())
            .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}

