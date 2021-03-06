package com.gmibank.app.repository;

import com.gmibank.app.domain.TPEmployee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TPEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPEmployeeRepository extends JpaRepository<TPEmployee, Long> {
}
