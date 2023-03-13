package de.muenchen.kobit.backend.user.service;

import de.muenchen.kobit.backend.user.model.User;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

/**
 * critical it seems that beside the email, the field names are different from dev to test. if we
 * need more than just the mail wen need to check the different tokens and find a way to work with
 * them!
 */
@Service
@Slf4j
public class UserDataResolver {

    private static final String TOKEN_EMAIL = "email";

    public final User getCurrentUser() {
        return readUserFromToken(SecurityContextHolder.getContext().getAuthentication());
    }

    private static User readUserFromToken(Authentication authentication) {
        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        final HashMap<String, Object> details =
                (HashMap<String, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
        return mapDetailsToUser(details);
    }

    private static User mapDetailsToUser(HashMap<String, Object> details) {
        log.debug(String.valueOf(details));
        return new User(details.get(TOKEN_EMAIL).toString());
    }
}
