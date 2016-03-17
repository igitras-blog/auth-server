package com.igitras.auth.common.authz;

import org.springframework.security.web.FilterInvocation;

/**
 * Created by mason on 3/18/16.
 */
public interface ResourcePermissionHandler {

    boolean checkPermission(ResourcePermission permission, FilterInvocation invocation);

}
