package com.gmibank.app.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI SSN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/ssn-already-used");
    public static final URI SSN_NOT_FOUND = URI.create(PROBLEM_BASE_URL + "/ssn-not-found");
    public static final URI ACCOUNT_NO_AUTHORIZES = URI.create(PROBLEM_BASE_URL + "/account-no-access");
    public static final URI TRANSFER_UNSUCCESS = URI.create(PROBLEM_BASE_URL + "/transfer-unsuccess");
    public static final URI TRANSFER_BALANCE_EXCEED = URI.create(PROBLEM_BASE_URL + "/transfer-balance-exceed");
    private ErrorConstants() {
    }
}
