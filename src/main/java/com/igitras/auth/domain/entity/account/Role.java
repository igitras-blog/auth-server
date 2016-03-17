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
import javax.validation.constraints.Size;

/**
 * Created by mason on 2/29/16.
 */
@Entity(name = "uaa_role")
public class Role extends AbstractAuditable<Long> {
    @NotNull
    @Size(min = 3,
          max = 64)
    @Column(unique = true,
            length = 64)
    private String name;

    @Column(length = 512)
    private String description;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "uaa_role_permission",
               joinColumns = {
                       @JoinColumn(name = "role_id",
                                   referencedColumnName = "id")
               },
               inverseJoinColumns = {
                       @JoinColumn(name = "resource_id",
                                   referencedColumnName = "id")
               })
    private Set<Resource> resources = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        Role role = (Role) o;

        if (name != null ? !name.equals(role.name) : role.name != null) {
            return false;
        }
        if (description != null ? !description.equals(role.description) : role.description != null) {
            return false;
        }
        return !(resources != null ? !resources.equals(role.resources) : role.resources != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (resources != null ? resources.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", resources=" + resources +
                '}';
    }
}
