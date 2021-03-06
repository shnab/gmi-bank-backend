package com.gmibank.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_EMPLOYEE";
    
    public static final String MANAGER = "ROLE_MANAGER";
    
    public static final String CUSTOMER = "ROLE_CUSTOMER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
