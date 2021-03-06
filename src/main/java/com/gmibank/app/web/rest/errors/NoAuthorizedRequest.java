package com.gmibank.app.web.rest.errors;

public class NoAuthorizedRequest extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public NoAuthorizedRequest() {
        super(ErrorConstants.ACCOUNT_NO_AUTHORIZES, "Account No Access", "Transfer", "account no access");
    }
}
