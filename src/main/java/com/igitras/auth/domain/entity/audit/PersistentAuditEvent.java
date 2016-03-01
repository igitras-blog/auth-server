package com.igitras.auth.domain.entity.audit;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.validation.constraints.NotNull;

/**
 * @author mason
 */
@Entity
public class PersistentAuditEvent extends AbstractPersistable<Long> {
    private static final long serialVersionUID = 4840192680208404735L;

    @NotNull
    @Column(nullable = false)
    private String principal;
    private LocalDateTime eventDate;
    private String eventType;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "persistent_audit_event_data", joinColumns = {@JoinColumn(name = "id")})
    private Map<String, String> data = new HashMap<>();

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
