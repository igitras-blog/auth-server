package com.igitras.auth.common.authz;

import com.igitras.auth.service.CustomResourceManager;
import com.igitras.auth.service.CustomRoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 3/17/16.
 */
@Component
public class PermissionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionHandler.class);

    @Autowired
    private CustomRoleManager roleManager;

    @Autowired
    private ResourcePermissionHandler handler;

    public boolean checkPermission(Authentication authentication, String resource, FilterInvocation invocation){
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        LOG.info("Checking permission with roles {} and resource [{}]", roles, resource);
        ResourcePermission permission = roleManager.getPermissionByRolesAndResource(roles, resource);
        return handler.checkPermission(permission, invocation);
    }
}
