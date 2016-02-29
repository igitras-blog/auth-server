package com.igitras.auth.repository;

import com.igitras.auth.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 *
 * @author mason
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
