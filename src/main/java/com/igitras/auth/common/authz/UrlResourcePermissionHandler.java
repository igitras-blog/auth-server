package com.igitras.auth.common.authz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * Created by mason on 3/18/16.
 */
@Component
public class UrlResourcePermissionHandler implements ResourcePermissionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UrlResourcePermissionHandler.class);

    @Override
    public boolean checkPermission(ResourcePermission permission, FilterInvocation invocation) {
        String method = invocation.getHttpRequest().getMethod();
        LOG.info("Checking Permission with permission {}, http method: {}", permission, method);
        return permission.grant(getRequiredPermissionByHttpMethod(permission.getResource(), method));
    }

    private static ResourcePermission getRequiredPermissionByHttpMethod(String resource, String method) {
        if (method.equalsIgnoreCase(HttpMethod.GET.name()) || method.equalsIgnoreCase(HttpMethod.HEAD.name())) {
            return new ResourcePermission(resource, ResourcePermission.READ);
        } else {
            return new ResourcePermission(resource, ResourcePermission.FULL);
        }
    }
}
