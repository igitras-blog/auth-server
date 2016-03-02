package com.igitras.auth.service;

import com.igitras.auth.configuration.security.UserNotActivatedException;
import com.igitras.auth.domain.entity.account.Account;
import com.igitras.auth.domain.repository.account.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mason
 */
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasLength(username, "Username must have length while loading user");
        LOG.debug("Authenticating {}", username);
        String lowercaseLogin = username.toLowerCase();

        Optional<Account> account = accountRepository.findOneByLogin(lowercaseLogin);

        return account.map(ac -> {
            if (!ac.isActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated.");
            }

            List<GrantedAuthority> grantedAuthorities = ac.getAuthorities()
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());
            return new User(ac.getLogin(), ac.getPassword(), grantedAuthorities);
        })
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User " + lowercaseLogin + " was not found in the " + "database"));
    }
}
