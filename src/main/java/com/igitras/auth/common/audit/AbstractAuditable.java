package com.igitras.auth.common.audit;

import com.igitras.auth.common.Auditable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Abstract base class for auditable entities. Stores the audition values in persistent fields.
 *
 * @param <PK> the type of the auditing type's idenifier
 * @author mason
 */
@MappedSuperclass
public abstract class AbstractAuditable<PK extends Serializable> extends AbstractPersistable<PK>
        implements Auditable<String, PK> {
    private static final long serialVersionUID = 8771767911665503172L;

    private String createdBy;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
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
