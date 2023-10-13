package com.api.wantedpreonboardingbackend.exception.apply;

import com.api.wantedpreonboardingbackend.exception.CommonBusinessException;
import com.api.wantedpreonboardingbackend.exception.dto.ErrorMessage;

public class DuplicateApplyException extends CommonBusinessException {

    public DuplicateApplyException() {
        super(ErrorMessage.DUPLICATE_APPLY);
    }

}
