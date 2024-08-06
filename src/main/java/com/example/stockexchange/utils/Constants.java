package com.example.stockexchange.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class Constants {

    public static class Error {
        public static final String STOCK_NOT_FOUND = "Stock not found, stock name: %s";
        public static final String STOCK_ALREADY_EXISTS = "Stock Exchange already exists, stock name: %s";
        public static final String USER_NOT_FOUND = "User not found, user name: %s";
        public static final String USER_ALREADY_EXISTS = "User already exists, user name: %s";

        public static final String RESOURCE_ALREADY_EXISTS = "Resource already exists";
        public static final String RESOURCE_NOT_FOUND = "Resource not found";
        public static final String TECHNICAL_ERROR = "Technical exception has been encountered";
    }

    public final static String ADMIN_ROLE = "ADMIN";
    public final static List<GrantedAuthority> GRANTED_AUTHORITIES = List.of(new SimpleGrantedAuthority(ADMIN_ROLE));
}
