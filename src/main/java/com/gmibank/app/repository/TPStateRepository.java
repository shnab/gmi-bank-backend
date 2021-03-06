package com.gmibank.app.repository;

import com.gmibank.app.domain.TPState;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TPState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPStateRepository extends JpaRepository<TPState, Long> {
}
