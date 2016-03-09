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
@Entity
public class Group extends AbstractAuditable<Long> {

    private static final long serialVersionUID = 3094777966413111844L;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "group_member",
            joinColumns = {@JoinColumn(name = "group_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id",
                    referencedColumnName = "id")})
    private Set<Account> members = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "agroup_authority",
            joinColumns = {
                    @JoinColumn(name = "group_id",
                            referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id",
                            referencedColumnName = "id")
            })
    private Set<Authority> authorities = new HashSet<>();

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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
