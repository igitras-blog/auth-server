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
@Entity(name = "uaa_role")
public class Role extends AbstractAuditable<Long> {

    private static final long serialVersionUID = 4034289250685980540L;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "uaa_role_authority",
            joinColumns = {
                    @JoinColumn(name = "role_id",
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
