package de.muenchen.kobit.backend.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUserInfoAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserInfoAuthoritiesService userInfoAuthoritiesService;

    public JwtUserInfoAuthenticationConverter(UserInfoAuthoritiesService userInfoAuthoritiesService) {
        this.userInfoAuthoritiesService = userInfoAuthoritiesService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return new JwtAuthenticationToken(source, userInfoAuthoritiesService.loadAuthorities(source));
    }

}
