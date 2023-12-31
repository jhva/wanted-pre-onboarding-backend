package com.api.wantedpreonboardingbackend.exception;

import com.api.wantedpreonboardingbackend.exception.dto.ErrorMessage;

import lombok.Getter;

@Getter
public class CommonBusinessException extends RuntimeException {
    private final ErrorMessage Errmessage;

    public CommonBusinessException(ErrorMessage message) {
        super(message.getDescription());
        this.Errmessage = message;
    }

    public CommonBusinessException(ErrorMessage message, String reason) {
        super(reason);
        this.Errmessage = message;
    }

    public CommonBusinessException(String reason) {
        super(reason);
        this.Errmessage = ErrorMessage.CONFLICT_ERROR;
    }
}
