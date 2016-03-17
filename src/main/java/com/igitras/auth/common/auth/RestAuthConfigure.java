package com.igitras.auth.common.auth;

import static org.springframework.security.web.util.matcher.AnyRequestMatcher.INSTANCE;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import com.igitras.auth.service.CustomUserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by mason on 3/18/16.
 */
@Component
public class RestAuthConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private static final Logger LOG = LoggerFactory.getLogger(RestAuthConfigure.class);

    @Autowired
    private CustomUserManager userManager;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        registerDefaultAuthenticationEndpoint(builder);
    }

    @SuppressWarnings("unchecked")
    private void registerDefaultAuthenticationEndpoint(HttpSecurity builder) {
        ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling
                = builder.getConfigurer(ExceptionHandlingConfigurer.class);
        if (exceptionHandling == null) {
            return;
        }

        exceptionHandling.defaultAuthenticationEntryPointFor(
                postProcess(
                        (AuthenticationEntryPoint) (request, response, authException) ->
                                response.sendError(SC_UNAUTHORIZED, authException.getMessage())),
                INSTANCE);
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter();
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        filter.setAuthenticationManager(authenticationManager);
        filter.setSessionAuthenticationStrategy(builder.getSharedObject(SessionAuthenticationStrategy.class));
        filter.setUserManager(userManager);
        filter.setRememberMeServices(builder.getSharedObject(RememberMeServices.class));
        filter = postProcess(filter);
        builder.addFilterAfter(filter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
