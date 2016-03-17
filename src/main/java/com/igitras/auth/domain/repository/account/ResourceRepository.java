package com.igitras.auth.domain.repository.account;

import com.igitras.auth.domain.entity.account.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 3/17/16.
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
