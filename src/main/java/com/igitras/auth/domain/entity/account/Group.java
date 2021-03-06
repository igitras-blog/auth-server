package com.igitras.auth.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.igitras.auth.common.audit.AbstractAuditable;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 * @author mason
 */
@Entity(name = "uaa_group")
public class Group extends AbstractAuditable<Long> {

    private static final long serialVersionUID = 3094777966413111844L;

    @NotNull
    @Column(unique = true,
            nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "uaa_group_member",
               joinColumns = {
                       @JoinColumn(name = "group_id",
                                   referencedColumnName = "id")
               },
               inverseJoinColumns = {
                       @JoinColumn(name = "member_id",
                                   referencedColumnName = "id")
               })
    private Set<Account> members = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "uaa_group_role",
               joinColumns = {
                       @JoinColumn(name = "group_id",
                                   referencedColumnName = "id")
               },
               inverseJoinColumns = {
                       @JoinColumn(name = "role_id",
                                   referencedColumnName = "id")
               })
    private Set<Role> roles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getMembers() {
        return members;
    }

    public void setMembers(Set<Account> members) {
        this.members = members;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
