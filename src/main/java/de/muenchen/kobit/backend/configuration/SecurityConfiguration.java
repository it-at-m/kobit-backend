/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.kobit.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/** The central class for configuration of all security aspects. */
@Configuration
@Profile("!no-security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(RestTemplateAutoConfiguration.class)
public class SecurityConfiguration {

    private final RestTemplateBuilder templateBuilder;

    @Value("${security.oauth2.resource.user-info-uri}")
    private String userInfoUri;

    public SecurityConfiguration(RestTemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                // allow access to /actuator/info
                .antMatchers("/actuator/info")
                .permitAll()
                // permit swagger
                .antMatchers("/swagger-ui/**", "/api/docs/**")
                .permitAll()
                // allow access to /actuator/health for OpenShift Health Check
                .antMatchers("/actuator/health")
                .permitAll()
                // allow access to /actuator/metrics for Prometheus monitoring in OpenShift
                .antMatchers("/actuator/metrics")
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(
                        new JwtUserInfoAuthenticationConverter(
                                new UserInfoAuthoritiesService(userInfoUri, templateBuilder)));
        return http.build();
    }
}
