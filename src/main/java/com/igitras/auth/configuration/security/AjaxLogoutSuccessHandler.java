package com.igitras.auth.configuration.security;

import static com.igitras.auth.utils.Constrains.Security.AUTH_HEADER;
import static com.igitras.auth.utils.Constrains.Security.BEARER_AUTHENTICATION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mason
 */
@Component
public class AjaxLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
        implements LogoutSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AjaxLogoutSuccessHandler.class);

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LOG.debug("Logout logical called.");
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_AUTHENTICATION)) {
            LOG.debug("Logout with authentication header {}", authHeader);
            final OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(authHeader);

            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessToken(oAuth2AccessToken);
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
