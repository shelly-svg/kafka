package com.example.config.security;

public class AuthorityConfiguration {

    public static final String ADMIN_SCOPE = "ADMIN";

    private final String appAdminScope;

    public AuthorityConfiguration(String applicationName) {
        this.appAdminScope = String.format("%s.%s", applicationName, ADMIN_SCOPE);
    }

    public String[] getSuperAuthorities() {
        return new String[]{ADMIN_SCOPE, appAdminScope};
    }

}