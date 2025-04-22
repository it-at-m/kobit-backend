package de.muenchen.kobit.backend.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.muenchen.kobit.backend.user.client.UserInfoClient;
import de.muenchen.kobit.backend.user.model.User;
import de.muenchen.kobit.backend.user.model.UserInfoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDataResolverTest {

    @Mock
    private UserInfoClient userInfoClient;

    @InjectMocks
    private UserDataResolver userDataResolver;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private JwtAuthenticationToken jwtAuthenticationToken;

    @Mock
    private Jwt jwt;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @ParameterizedTest
    @MethodSource("provideUserInfoViews")
    public void testGetCurrentUser(UserInfoView userInfoView, String expectedDepartment) throws JsonProcessingException {
        // Mocking SecurityContext and Authentication
        when(securityContext.getAuthentication()).thenReturn(jwtAuthenticationToken);

        // Mocking token attributes
        Map<String, Object> tokenAttributes = new HashMap<>();
        tokenAttributes.put("email", "test@example.com");
        tokenAttributes.put("resource_access", "{\"kobit\":{\"roles\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}}");

        when(jwtAuthenticationToken.getTokenAttributes()).thenReturn(tokenAttributes);
        when(jwtAuthenticationToken.getToken()).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("mockTokenValue");

        // Mocking UserInfoClient response
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer mockTokenValue");
        when(userInfoClient.getUserInformation(headerMap)).thenReturn(userInfoView);

        // Call the method to test
        User user = userDataResolver.getCurrentUser();

        // Assertions
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals(expectedDepartment, user.getDepartment());
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains("ROLE_USER"));
        assertTrue(user.getRoles().contains("ROLE_ADMIN"));
    }

    private static Stream<Arguments> provideUserInfoViews() {
        return Stream.of(
                Arguments.of(new UserInfoView("ITM-ABC", "CN=tb.123456,OU=Users,OU=KUL,OU=Bereiche,DC=muenchen,DC=de"), "KUL"),
                Arguments.of(new UserInfoView("HR-ABC", ""), "HR")

        );
    }
}
