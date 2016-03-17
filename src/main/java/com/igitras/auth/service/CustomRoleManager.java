package com.igitras.auth.service;

import com.igitras.auth.common.authz.ResourcePermission;
import com.igitras.auth.domain.entity.account.Account;
import com.igitras.auth.domain.repository.account.AccountRepository;
import com.igitras.auth.mvc.dto.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author mason
 */
@Service
public class CustomRoleManager {
    public static final Logger LOG = LoggerFactory.getLogger(CustomRoleManager.class);


    public ResourcePermission getPermissionByRolesAndResource(List<String> roles, String resource) {
        return null;
    }
}
