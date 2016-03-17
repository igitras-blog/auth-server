package com.igitras.auth.common.authz;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * Created by mason on 3/18/16.
 */
public class RbacSecurityExceptionMethods {

    private final Authentication authentication;
    private PermissionHandler permissionHandler;
    private FilterInvocation invocation;

    public RbacSecurityExceptionMethods(PermissionHandler permissionHandler, Authentication authentication,
                                        FilterInvocation invocation) {
        this.permissionHandler = permissionHandler;
        this.authentication = authentication;
        this.invocation = invocation;
    }

    public boolean accessResource(String resource) {
        return permissionHandler.checkPermission(authentication, resource, invocation);
    }
}
