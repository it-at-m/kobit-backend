package de.muenchen.kobit.backend.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.kobit.backend.user.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Component
public class UserDataResolver {

    private static final String TOKEN_EMAIL = "email";

    private static final String TOKEN_DEPARTMENT = "department";
    private static final String RESOURCE_FIELD = "resource_access";
    private static final String KOBIT_FIELD = "kobit";
    private static final String ROLES_FIELD = "roles";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public final User getCurrentUser() {
        return readUserFromToken(SecurityContextHolder.getContext().getAuthentication());
    }

    private User readUserFromToken(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            final JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
            Map<String, Object> tokenAttributes = jwtToken.getTokenAttributes();
            try {
                return new User(getUserMail(tokenAttributes), getDepartment(tokenAttributes), getRoles(tokenAttributes));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Only authentication via token is allowed!");
        }
    }

    private static List<String> getRoles(Map<String, Object> tokenDetails)
            throws JsonProcessingException {
        final JsonNode roleNode = jsonNodeOfRoles(mapTokenToJson(tokenDetails));
        final List<String> roles = new ArrayList<>();
        if (roleNode.isArray()) {
            for (final JsonNode objNode : roleNode) {
                roles.add(objNode.textValue());
            }
        }
        return roles;
    }

    private static JsonNode mapTokenToJson(Map<String, Object> tokenDetails)
            throws JsonProcessingException {
        return objectMapper.readTree(String.valueOf(tokenDetails.get(RESOURCE_FIELD)));
    }

    private static JsonNode jsonNodeOfRoles(JsonNode node) throws JsonProcessingException {
        if (node.get(KOBIT_FIELD) != null) {
            return node.get(KOBIT_FIELD).get(ROLES_FIELD);
        } else {
            return objectMapper.readTree("");
        }
    }

    private static String getUserMail(Map<String, Object> tokenDetails) {
        return tokenDetails.get(TOKEN_EMAIL).toString();
    }

    private static String getDepartment(Map<String, Object> tokenDetails) {
        return tokenDetails.get(TOKEN_DEPARTMENT).toString();
    }
}
