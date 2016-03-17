package com.igitras.auth.domain.repository.account;

import com.igitras.auth.domain.entity.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for the Role entity.
 *
 * @author mason
 */
public interface AuthorityRepository extends JpaRepository<Role, String> {

    Optional<Role> findOneByName(String name);

    Set<Role> findByNameIn(Set<String> name);
}
