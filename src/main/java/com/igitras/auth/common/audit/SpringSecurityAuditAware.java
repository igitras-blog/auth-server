package com.igitras.auth.common.audit;

import com.igitras.auth.utils.Constrains;
import com.igitras.auth.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

/**
 * Implementation of AuditorAware based on Spring Security.
 *
 * @author mason
 */
public class SpringSecurityAuditAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String login = SecurityUtils.getCurrentUserLogin();
        return login != null ? login : Constrains.Security.SYSTEM_ACCOUNT;
    }
}
