package com.gmibank.app.web.rest.errors;

public class SSNAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public SSNAlreadyUsedException() {
        super(ErrorConstants.SSN_ALREADY_USED_TYPE, "SSN is already saved!", "Registration", "ssnexists");
    }
}
