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

    @JsonIgnore
    @Size(max = 20)
    @Column(name = "activation_key",
            length = 20)
    private String activationKey;

    @Column(nullable = false)
    private boolean activated = false;

    @Size(max = 20)
    @Column(name = "reset_key",
            length = 20)
    private String resetKey;

    @Column(name = "reset_date",
            nullable = true)
    private ZonedDateTime resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "uaa_account_role",
               joinColumns = {
                       @JoinColumn(name = "account_id",
                                   referencedColumnName = "id")
               },
               inverseJoinColumns = {
                       @JoinColumn(name = "role_id",
                                   referencedColumnName = "id")
               })
    private Set<Role> roles = new HashSet<>();

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

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Account account = (Account) o;

        if (activated != account.activated) {
            return false;
        }
        if (login != null ? !login.equals(account.login) : account.login != null) {
            return false;
        }
        if (password != null ? !password.equals(account.password) : account.password != null) {
            return false;
        }
        if (email != null ? !email.equals(account.email) : account.email != null) {
            return false;
        }
        if (activationKey != null ? !activationKey.equals(account.activationKey) : account.activationKey != null) {
            return false;
        }
        if (resetKey != null ? !resetKey.equals(account.resetKey) : account.resetKey != null) {
            return false;
        }
        if (resetDate != null ? !resetDate.equals(account.resetDate) : account.resetDate != null) {
            return false;
        }
        if (roles != null ? !roles.equals(account.roles) : account.roles != null) {
            return false;
        }
        return !(groups != null ? !groups.equals(account.groups) : account.groups != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (activationKey != null ? activationKey.hashCode() : 0);
        result = 31 * result + (activated ? 1 : 0);
        result = 31 * result + (resetKey != null ? resetKey.hashCode() : 0);
        result = 31 * result + (resetDate != null ? resetDate.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        return result;
    }
}