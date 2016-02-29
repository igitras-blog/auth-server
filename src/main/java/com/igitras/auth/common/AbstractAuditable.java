package com.igitras.auth.common;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Abstract base class for auditable entities. Stores the audition values in persistent fields.
 *
 * @param <U>  the auditing type. Typically some kind of user.
 * @param <PK> the type of the auditing type's idenifier
 * @author mason
 */
@MappedSuperclass
public abstract class AbstractAuditable<U, PK extends Serializable> extends AbstractPersistable<PK>
        implements Auditable<U, PK> {
    private static final long serialVersionUID = 8771767911665503172L;

    @ManyToOne
    private U createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime dateTime;

    @ManyToOne
    private U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime lastModifiedDate;


    @Override
    public U getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
