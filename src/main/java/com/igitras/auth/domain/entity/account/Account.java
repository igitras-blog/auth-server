package com.igitras.auth.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.igitras.auth.common.audit.AbstractAuditable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author mason
 */
@Entity(name = "uaa_account")
public class Account extends AbstractAuditable<Long> {
    private static final long serialVersionUID = -5936880652980576016L;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$|(anonymousUser)")
    @Size(min = 6,
          max = 64)
    @Column(length = 64,
            unique = true,
            nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Column(length = 128,
            nullable = false)
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    @Size(max = 128)
    @Column(length = 128,
            unique = true)
    private String email;

    @Column(nullable = false)
    private boolean activated = false;

    private boolean accountLocked = false;

    private boolean accountExpired = false;

    private boolean credentialExpired = false;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "uaa_account_authorities",
               joinColumns = {
                       @JoinColumn(name = "account_id",
                                   referencedColumnName = "id")
               },
               inverseJoinColumns = {
                       @JoinColumn(name = "role_id",
                                   referencedColumnName = "id")
               })
    private Set<Authority> authorities = new HashSet<>();

    private Set<Group> groups = new HashSet<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isCredentialExpired() {
        return credentialExpired;
    }

    public void setCredentialExpired(boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

}