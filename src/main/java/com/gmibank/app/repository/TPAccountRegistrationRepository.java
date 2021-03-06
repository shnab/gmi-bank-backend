package com.gmibank.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmibank.app.domain.TPAccountRegistration;

/**
 * Spring Data  repository for the TPAccountRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPAccountRegistrationRepository extends JpaRepository<TPAccountRegistration, Long> {
	 @Query(value ="select tPAccountRegistration from TPAccountRegistration tPAccountRegistration  where tPAccountRegistration.ssn=:ssn")
	 List<TPAccountRegistration> findOneBySSN(@Param("ssn")String ssn);
}
