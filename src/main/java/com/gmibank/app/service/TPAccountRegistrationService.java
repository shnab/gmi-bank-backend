package com.gmibank.app.service;

import java.util.List;
import java.util.Optional;

import com.gmibank.app.domain.TPAccountRegistration;

/**
 * Service Interface for managing {@link com.gmibank.app.domain.TPAccountRegistration}.
 */
public interface TPAccountRegistrationService {

    /**
     * Save a tPAccountRegistration.
     *
     * @param tPAccountRegistrationDTO the entity to save.
     * @return the persisted entity.
     */
	TPAccountRegistration save(TPAccountRegistration tPAccountRegistration);
	
	 /**
     * Get all the TPAccountRegistrations.
     *
     * @return the list of entities.
     */
    List<TPAccountRegistration> findAll();


    /**
     * Get the "id" TPAccountRegistration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPAccountRegistration> findOne(Long id);
    
    List<TPAccountRegistration> findOneBySSN(String ssn);

    /**
     * Delete the "id" TPAccountRegistration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


}
