package com.igitras.auth.service;

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
import java.util.Optional;

/**
 * @author mason
 */
@Service
public class CustomUserManager {
    public static final Logger LOG = LoggerFactory.getLogger(CustomUserManager.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private AuthenticationManager authenticationManager;

    private UserCache userCache = new NullUserCache();

    public void setAuthenticationManager(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    // ~ Methods
    // ========================================================================================================

    protected void initDao() throws ApplicationContextException {
        if (authenticationManager == null) {
            LOG.info("No authentication manager set. Reauthentication of users when changing passwords will "
                    + "not be performed.");
        }
    }

    public Account createUser(AccountDto accountDto) {
        Account account = new Account();
        account.setActivated(accountDto.isEnabled());
        account.setLogin(accountDto.getUsername());
        String password = accountDto.getPassword();
        account.setPassword(password);
        account.setEmail(accountDto.getEmail());
        return accountRepository.save(account);
    }

    public void activeAccount(String username) {
        LOG.debug("Active credential with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setActivated(true);
            LOG.debug("Active credential with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void unexpireCredential(String username) {
        LOG.debug("Un-Expire credential with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setCredentialExpired(false);
            LOG.debug("Un-Expire credential with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void expireCredential(String username) {
        LOG.debug("Expire credential with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setCredentialExpired(true);
            LOG.debug("Expire credential with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void unexpireAccount(String username) {
        LOG.debug("Un-Expire account with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setAccountExpired(false);
            LOG.debug("Un-Expire account with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void expireAccount(String username) {
        LOG.debug("Expire account with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setAccountExpired(true);
            LOG.debug("Expire account with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void unlockAccount(String username) {
        LOG.debug("UnLock account with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setAccountLocked(false);
            LOG.debug("Unlock account with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void lockAccount(String username) {
        LOG.debug("lock account with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setAccountLocked(true);
            LOG.debug("Lock account with login: {}", username);
            accountRepository.save(account);
        }
        userCache.removeUserFromCache(username);
    }

    public void deleteUser(String username) {
        LOG.debug("delete account with {}", username);
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            LOG.debug("delete account with login: {}", username);
            accountRepository.delete(oneByLogin.get());
        }
        userCache.removeUserFromCache(username);
    }



    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context "
                    + "for current user.");
        }

        String username = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the supplied password.
        if (authenticationManager != null) {
            LOG.debug("Reauthenticating user '" + username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOG.debug("No authentication manager set. Password won't be re-checked.");
        }

        LOG.debug("Changing password for user '" + username + "'");
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            Account account = oneByLogin.get();
            account.setLogin(username);
            LOG.debug("Change password with login: {}", username);
            accountRepository.save(account);
        }

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));

        userCache.removeUserFromCache(username);
    }

    public boolean userExists(String username) {
        LOG.debug("Check user exist with '" + username + "'");
        Optional<Account> oneByLogin = accountRepository.findOneByLogin(username);
        if (oneByLogin.isPresent()) {
            LOG.debug("Find account with login: {}", username);
            return true;
        }

        return false;
    }

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(),
                    "getAuthority() method must return a non-empty string");
        }
    }

    protected Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = customUserDetailsService.loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }
}
