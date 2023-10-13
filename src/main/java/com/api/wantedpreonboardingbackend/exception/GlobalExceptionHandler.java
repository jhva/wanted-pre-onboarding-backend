package com.api.wantedpreonboardingbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.wantedpreonboardingbackend.exception.dto.ErrorDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonBusinessException.class)
    protected ResponseEntity<ErrorDto> handleBusinessException(
        final CommonBusinessException e,
        final HttpServletRequest request
    ) {
        log.error("CommonBusinessException -> {} {}", e.getErrmessage(), e.getCause());
        log.error("Request url {}", request.getRequestURL());

        return ResponseEntity
            .status(e.getErrmessage().getStatus())
            .body(
                new ErrorDto(
                    e.getErrmessage().name(),
                    e.getErrmessage().getDescription())
            );
    }
}
