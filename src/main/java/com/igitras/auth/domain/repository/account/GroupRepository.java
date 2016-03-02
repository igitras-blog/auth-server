package com.igitras.auth.domain.repository.account;

import com.igitras.auth.domain.entity.account.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mason
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

}
