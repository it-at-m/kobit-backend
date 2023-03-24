package de.muenchen.kobit.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthUtils {

    private static final String TOKEN_USER_NAME = "user_name";

    private AuthUtils(){}

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            final JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
            return (String) jwtToken.getTokenAttributes().getOrDefault(TOKEN_USER_NAME, null);
        } else {
            throw new RuntimeException("Only authentication via token is allowed!");
        }
    }
}
