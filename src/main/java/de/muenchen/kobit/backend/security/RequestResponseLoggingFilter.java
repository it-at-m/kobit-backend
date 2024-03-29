/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.kobit.backend.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

/** This filter logs the username for requests. */
@Component
@Order(1)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    @Getter private static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String REQUEST_LOGGING_MODE_ALL = "all";

    private static final String REQUEST_LOGGING_MODE_CHANGING = "changing";

    private static final List<String> CHANGING_METHODS =
            Arrays.asList("POST", "PUT", "PATCH", "DELETE");

    /** The property or a zero length string if no property is available. */
    @Value("${security.logging.requests:}")
    private String requestLoggingMode;

    /** {@inheritDoc} */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.debug("Initializing filter: {}", this);
    }

    /**
     * The method logs the username extracted out of the {@link SecurityContext}. Additionally to
     * the username, the kind of HTTP-Request and the targeted URI is logged.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public void doFilter(
            final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (checkForLogging(httpRequest)) {
            log.info(
                    "User {} executed {} on URI {}",
                    AuthUtils.getUserName(),
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI());
        }
        chain.doFilter(request, response);
    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {
        log.debug("Destructing filter: {}", this);
    }

    /**
     * The method checks if logging the username should be done.
     *
     * @param httpServletRequest The request to check for logging.
     * @return True if logging should be done otherwise false.
     */
    private boolean checkForLogging(HttpServletRequest httpServletRequest) {
        return requestLoggingMode.equals(REQUEST_LOGGING_MODE_ALL)
                || (requestLoggingMode.equals(REQUEST_LOGGING_MODE_CHANGING)
                        && CHANGING_METHODS.contains(httpServletRequest.getMethod()));
    }
}
