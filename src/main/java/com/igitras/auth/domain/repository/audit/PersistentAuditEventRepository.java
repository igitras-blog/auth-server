package com.igitras.auth.domain.repository.audit;

import com.igitras.auth.domain.entity.audit.PersistentAuditEvent;
import com.sun.tools.javac.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 *
 * @author mason
 */
public interface PersistentAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndEventDateAfter(String principal, LocalDateTime after);

    List<PersistentAuditEvent> findAllByEventDateBetween(LocalDateTime from, LocalDateTime to);

}
