package com.igitras.auth.mvc.dto;

import java.io.Serializable;

/**
 * Created by mason on 3/14/16.
 */
public class AccountDto implements Serializable {
    private boolean enabled;
    private String username;
    private String password;
    private String email;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
