package com.igitras.auth.common.auth;

/**
 * Created by mason on 3/18/16.
 */
public class ExternalAuthenticationObject {
    private String user = "";
    private String password = "";

    public ExternalAuthenticationObject(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public ExternalAuthenticationObject() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
