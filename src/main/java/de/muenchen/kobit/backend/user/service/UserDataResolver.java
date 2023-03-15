package de.muenchen.kobit.backend.user.service;

import de.muenchen.kobit.backend.configuration.JwtTokenUtil;
import de.muenchen.kobit.backend.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * critical it seems that beside the email, the field names are different from dev to test. if we
 * need more than just the mail wen need to check the different tokens and find a way to work with
 * them!
 */
@Service
@Slf4j
public class UserDataResolver {

    private static final String TOKEN_EMAIL = "email";

    private final JwtTokenUtil jwtTokenUtil;

    UserDataResolver(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public final User getCurrentUser() {
        return readUserFromToken(SecurityContextHolder.getContext().getAuthentication());
    }

    private User readUserFromToken(Authentication authentication) {
        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        final HashMap<String, Object> userDetails =
                (HashMap<String, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
        var jwtToken = ((OAuth2AuthenticationDetails) oAuth2Authentication.getDetails()).getTokenValue();
        return new User(getUserMail(userDetails), jwtTokenUtil.getClientRoles(jwtToken));
    }

    private static String getUserMail(HashMap<String, Object> details) {
        return details.get(TOKEN_EMAIL).toString();
    }

}
