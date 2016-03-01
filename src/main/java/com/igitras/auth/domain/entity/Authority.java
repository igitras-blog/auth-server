package com.igitras.auth.domain.entity;

import com.igitras.auth.common.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by mason on 2/29/16.
 */
@Entity
public class Authority extends AbstractAuditable<Account, Long> {
    @NotNull
    @Size(min = 3,
          max = 64)
    @Column(unique = true,
            length = 64)
    private String name;

    @Column(length = 512)
    private String description;

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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }

        Authority authority = (Authority) other;

        if (name != null ? !name.equals(authority.name) : authority.name != null) {
            return false;
        }
        return !(description != null ? !description.equals(authority.description) : authority.description != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
