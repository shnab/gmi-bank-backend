package com.gmibank.app.web.rest.errors;

public class TransferException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public TransferException() {
        super(ErrorConstants.TRANSFER_UNSUCCESS, "Transfer unsuccessful!", "Transfer", "Transfer unsuccess");
    }
}
