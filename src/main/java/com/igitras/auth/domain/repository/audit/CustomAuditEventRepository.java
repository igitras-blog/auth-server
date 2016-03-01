package com.igitras.auth.domain.repository.audit;

import com.igitras.auth.common.audit.AuditEventConverter;
import com.igitras.auth.domain.entity.audit.PersistentAuditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author mason
 */
@Repository
public class CustomAuditEventRepository {

    @Autowired
    private PersistentAuditEventRepository auditEventRepository;

    @Bean
    public org.springframework.boot.actuate.audit.AuditEventRepository auditEventRepository() {
        return new AuditEventRepository() {
            private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";
            private static final String ANONYMOUS_USER = "anonymousUser";

            @Autowired
            private AuditEventConverter auditEventConverter;

            @Override
            public List<AuditEvent> find(String principal, Date after) {
                Iterable<PersistentAuditEvent> persistentAuditEvents;

                if (principal == null && after == null) {
                    persistentAuditEvents = auditEventRepository.findAll();
                } else if (after == null) {
                    persistentAuditEvents = auditEventRepository.findByPrincipal(principal);
                } else {
                    persistentAuditEvents = auditEventRepository.findByPrincipalAndEventDateAfter(principal,
                            LocalDateTime.from(after.toInstant()));
                }
                return auditEventConverter.convertToAuditEvents(persistentAuditEvents);
            }

            @Override
            @Transactional(propagation = Propagation.REQUIRES_NEW)
            public void add(AuditEvent event) {
                if (!AUTHORIZATION_FAILURE.equals(event.getType()) && !ANONYMOUS_USER.equals(event.getPrincipal())) {
                    PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
                    persistentAuditEvent.setPrincipal(event.getPrincipal());
                    persistentAuditEvent.setEventDate(
                            LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(event.getTimestamp().getTime()), ZoneId.systemDefault()
                            ));
                    persistentAuditEvent.setEventType(event.getType());
                    persistentAuditEvent.setData(auditEventConverter.convertDataToStrings(event.getData()));
                    auditEventRepository.save(persistentAuditEvent);
                }

            }
        };
    }
}
