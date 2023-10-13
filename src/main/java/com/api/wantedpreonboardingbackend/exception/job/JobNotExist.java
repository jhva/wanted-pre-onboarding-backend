package com.api.wantedpreonboardingbackend.exception.job;

import com.api.wantedpreonboardingbackend.exception.CommonBusinessException;
import com.api.wantedpreonboardingbackend.exception.dto.ErrorMessage;

public class JobNotExist extends CommonBusinessException {

    public JobNotExist() {
        super(ErrorMessage.NOT_EXIST_JOB);
    }
}
