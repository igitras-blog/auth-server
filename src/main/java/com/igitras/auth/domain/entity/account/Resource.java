package com.igitras.auth.domain.entity.account;

import com.igitras.auth.common.audit.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by mason on 3/17/16.
 */
@Entity(name = "uaa_resource")
public class Resource extends AbstractAuditable<Long> {

    @NotNull
    @Column(unique = true,
            nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        Resource resource = (Resource) o;

        return !(name != null ? !name.equals(resource.name) : resource.name != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                '}';
    }
}
