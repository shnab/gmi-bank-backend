package com.gmibank.app.web.rest.errors;

public class TransferBalanceExceedsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public TransferBalanceExceedsException() {
        super(ErrorConstants.TRANSFER_BALANCE_EXCEED, "Balance exceed!", "TPCustomer", "Balanceexceed");
    }
}
