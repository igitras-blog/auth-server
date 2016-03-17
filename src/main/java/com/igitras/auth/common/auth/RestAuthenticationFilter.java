package com.igitras.auth.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igitras.auth.service.CustomUserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.csrf.CsrfToken;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mason on 3/18/16.
 */
public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationFilter.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private CustomUserManager userManager;

    public RestAuthenticationFilter() {
        super("/login");
        this.setAuthenticationSuccessHandler((HttpServletRequest request,
                                              HttpServletResponse response, Authentication authentication) -> {
            LocalUserDetails user = (LocalUserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            CsrfToken token = (CsrfToken) request
                    .getAttribute("org.springframework.security.web.csrf.CsrfToken");
            LOG.info("Login succeed, user name is " + user.getUsername()
                    + ", csrf token is " + token.getToken());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.addHeader(token.getHeaderName(), token.getToken());
            mapper.writeValue(
                    response.getOutputStream(),
                    new ExternalUserProfile(user.getUsername(), userManager.getResourcePermissionsByUserId(user.getId())));
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            ExternalAuthenticationObject authObj
                    = mapper.readValue(request.getInputStream(), ExternalAuthenticationObject.class);
            Authentication authRequest = new UsernamePasswordAuthenticationToken(authObj.getUser(),
                    authObj.getPassword());
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Fail to read authentication information from the body", e);
        }
    }

    public void setUserManager(CustomUserManager userManager) {
        this.userManager = userManager;
    }
}
