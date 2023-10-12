package com.api.wantedpreonboardingbackend.exception.user;

import com.api.wantedpreonboardingbackend.exception.CommonBusinessException;
import com.api.wantedpreonboardingbackend.exception.dto.ErrorMessage;

public class UserNotExist extends CommonBusinessException {

    public UserNotExist() {
        super(ErrorMessage.NOT_EXIST_JOB);
    }
}
