package com.igitras.auth.common;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Interface for auditable entities. Allows storing and retrieving creation and modification information. The changing
 * instance (typically some user) is to be defined by a generics definition.
 *
 * @param <U>  the auditing type. Typically some kind of user.
 * @param <ID> the type of the audited type's identifier
 * @author mason
 */
public interface Auditable<U, ID extends Serializable> extends Persistable<ID> {
    /**
     * Returns the user who created this entity.
     *
     * @return the createdBy
     */
    U getCreatedBy();

    /**
     * Sets the user who created this entity.
     *
     * @param createdBy the creating entity to set
     */
    void setCreatedBy(final U createdBy);

    /**
     * Returns the creation date of the entity.
     *
     * @return the createdDate
     */
    ZonedDateTime getCreatedDate();

    /**
     * Sets the creation date of the entity.
     *
     * @param creationDate the creation date to set
     */
    void setCreatedDate(final ZonedDateTime creationDate);

    /**
     * Returns the user who modified the entity lastly.
     *
     * @return the lastModifiedBy
     */
    U getLastModifiedBy();

    /**
     * Sets the user who modified the entity lastly.
     *
     * @param lastModifiedBy the last modifying entity to set
     */
    void setLastModifiedBy(final U lastModifiedBy);

    /**
     * Returns the date of the last modification.
     *
     * @return the lastModifiedDate
     */
    ZonedDateTime getLastModifiedDate();

    /**
     * Sets the date of the last modification.
     *
     * @param lastModifiedDate the date of the last modification to set
     */
    void setLastModifiedDate(final ZonedDateTime lastModifiedDate);
}
