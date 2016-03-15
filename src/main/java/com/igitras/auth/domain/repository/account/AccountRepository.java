package com.igitras.auth.domain.repository.account;

import com.igitras.auth.domain.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author mason
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<Account> findOneByEmail(String email);

    Optional<Account> findOneByLogin(String login);

    Optional<Account> findOneById(Long id);

    @Override
    void delete(Account account);
}
