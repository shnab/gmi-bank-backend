package com.gmibank.app.repository;

import com.gmibank.app.domain.TPAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TPAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPAccountRepository extends JpaRepository<TPAccount, Long> {
}
