package com.igitras.auth.common.authz;

import java.util.List;

/**
 * Created by mason on 3/18/16.
 */
public class ResourcePermission {
    public static final int READ = 0x01;
    public static final int FULL = 0xFF;
    public static final int NONE = 0x00;

    private String resource;
    private int permission;

    public ResourcePermission() {
    }

    public ResourcePermission(String resource, int permission) {
        this.resource = resource;
        this.permission = permission;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public boolean grant(ResourcePermission target) {
        return resource.equalsIgnoreCase(target.getResource())
                && (permission & target.getPermission()) == target.getPermission();
    }

    /**
     * Merge a list of ResourcePermissions into one. the result
     * ResourcePermission's permission should represent all the permissions that
     * the original list have on the target res.
     *
     * @param resource    resource
     * @param permissions permissions
     *
     * @return merged permissions
     */
    public static ResourcePermission mergerPermissions(String resource, List<ResourcePermission> permissions) {
        int merged = 0;
        for (ResourcePermission permission : permissions) {
            if (resource.equalsIgnoreCase(permission.getResource())) {
                merged |= permission.getPermission();
            }
        }
        return new ResourcePermission(resource, merged);
    }
}
