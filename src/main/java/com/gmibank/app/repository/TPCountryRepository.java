package com.gmibank.app.repository;

import com.gmibank.app.domain.TPCountry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TPCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPCountryRepository extends JpaRepository<TPCountry, Long> {
}
