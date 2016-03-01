package com.igitras.auth.configuration.security;

import com.igitras.auth.common.audit.AuditEventConverter;
import com.igitras.auth.common.audit.SpringSecurityAuditAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * @author mason
 */
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditEventConverter auditEventConverter() {
        return new AuditEventConverter();
    }

    @Bean
    public AuditorAware auditorAware() {
        return new SpringSecurityAuditAware();
    }
}
