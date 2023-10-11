package com.api.wantedpreonboardingbackend.exception.company;

import com.api.wantedpreonboardingbackend.exception.CommonBusinessException;
import com.api.wantedpreonboardingbackend.exception.dto.ErrorMessage;

public class CompanyNotExist extends CommonBusinessException {

    public CompanyNotExist() {
        super(ErrorMessage.NOT_EXIST_COMPANY);
    }
}
