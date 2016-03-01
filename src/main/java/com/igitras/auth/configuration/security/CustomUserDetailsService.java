package com.igitras.auth.configuration.security;

import com.igitras.auth.domain.entity.Account;
import com.igitras.auth.domain.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author mason
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasLength(username, "Username must have length while loading user");
        LOG.debug("Authenticating {}", username);
        String lowercaseLogin = username.toLowerCase();

        Optional<Account> account = accountRepository.findOneByLogin(lowercaseLogin);

        return null;
    }
}
