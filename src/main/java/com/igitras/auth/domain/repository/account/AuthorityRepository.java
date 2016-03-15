package com.igitras.auth.domain.repository.account;

import com.igitras.auth.domain.entity.account.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for the Authority entity.
 *
 * @author mason
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Optional<Authority> findOneByName(String name);

    Set<Authority> findByNameIn(Set<String> name);
}
