package com.igitras.auth.domain.repository;

import com.igitras.auth.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author mason
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findOneByClientId(String clientId);
}
