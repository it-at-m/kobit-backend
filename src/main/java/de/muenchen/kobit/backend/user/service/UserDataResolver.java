package de.muenchen.kobit.backend.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.kobit.backend.user.client.UserInfoClient;
import de.muenchen.kobit.backend.user.model.User;
import de.muenchen.kobit.backend.user.model.UserInfoView;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String RESOURCE_FIELD = "resource_access";
    private static final String KOBIT_FIELD = "kobit";
    private static final String ROLES_FIELD = "roles";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final UserInfoClient userInfoClient;

    public UserDataResolver(UserInfoClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    public final User getCurrentUser() {
        return readUserFromToken(SecurityContextHolder.getContext().getAuthentication());
    }

    private User readUserFromToken(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            log.info("Authentication instance of JwtAuthenticationToken");
            final JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
            // Logging the length of the token here
            log.info("Token length: {}", jwtToken.getToken().getTokenValue().length());
            Map<String, Object> tokenAttributes = jwtToken.getTokenAttributes();
            try {
                return new User(
                        getUserMail(tokenAttributes),
                        getDepartment(jwtToken.getToken().getTokenValue()),
                        getRoles(tokenAttributes));
            } catch (JsonProcessingException e) {
                log.error("Error processing token attributes: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            log.error("Authentication not via token, throwing RuntimeException");
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

    private String getDepartment(String jwtToken) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + jwtToken);
        UserInfoView userInfoView = userInfoClient.getUserInformation(headerMap);
        // splits the two parts of the department
        // expected looks: DEPARTMENT-UNIT exp. ITM-KM55
        String splitter = "-";
        return userInfoView.getDepartment().toUpperCase().split(splitter)[0];
    }
}
