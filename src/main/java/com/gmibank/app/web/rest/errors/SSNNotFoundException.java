package com.gmibank.app.web.rest.errors;

public class SSNNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public SSNNotFoundException() {
        super(ErrorConstants.SSN_ALREADY_USED_TYPE, "SSN not found!", "Registration", "ssn not found");
    }
}
