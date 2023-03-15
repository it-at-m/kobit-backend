package de.muenchen.kobit.backend.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
public class JwtTokenUtil {

    private static final String RESOURCE_FIELD = "resource_access";
    private static final String KOBIT_FIELD = "kobit";
    private static final String ROLES_FIELD = "roles";
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getClientRoles(String token) {
        byte[] body = decodeTokenBody(token);
        return retrieveRoles(body);
    }

    private List<String> retrieveRoles(byte[] body) {
        List<String> roles = new ArrayList<>();
        try {
            JsonNode roleNode = getRoleNode(body);
            if (roleNode.isArray()) {
                for (final JsonNode objNode : roleNode) {
                    roles.add(objNode.textValue());
                }
            }
        } catch (IOException e) {
            log.error("Unexpected Error reading Roles from User!", e);
        }
        return roles;
    }

    private JsonNode getRoleNode(byte[] body) throws IOException {
        JsonNode node = objectMapper.readTree(body);
        if(node.get(RESOURCE_FIELD).has(KOBIT_FIELD)){
            return node.get(RESOURCE_FIELD).get(KOBIT_FIELD).get(ROLES_FIELD);
        }
        return objectMapper.readTree("");
    }

    private byte[] decodeTokenBody(String token) {
        return decoder.decode(getTokenBody(token));
    }

    private String getTokenBody(String token) {
        return token.split("\\.")[1];
    }

}
