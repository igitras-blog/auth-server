package com.igitras.auth.domain.repository.account;

import com.igitras.auth.domain.entity.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mason
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}
