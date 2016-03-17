package com.igitras.auth.common.auth;

import com.igitras.auth.common.authz.ResourcePermission;

/**
 * Created by mason on 3/18/16.
 */
public class ExternalUserProfile {
    private String username;
    private ResourcePermission[] permissions;

    public ExternalUserProfile(String username, ResourcePermission[] permissions) {
        this.username = username;
        this.permissions = permissions;
    }
}
