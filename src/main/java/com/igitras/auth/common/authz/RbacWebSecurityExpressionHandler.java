package com.igitras.auth.common.authz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

/**
 * Created by mason on 3/18/16.
 */
@Component
public class RbacWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RbacWebSecurityExpressionHandler.class);

    @Autowired
    private PermissionHandler permissionHandler;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication,
                                                                        FilterInvocation invocation) {

        StandardEvaluationContext evaluationContext = super.createEvaluationContextInternal(authentication, invocation);
        evaluationContext.setVariable("rbac", new RbacSecurityExceptionMethods(permissionHandler, authentication, invocation));
        return evaluationContext;
    }
}
