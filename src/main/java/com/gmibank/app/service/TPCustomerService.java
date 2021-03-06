package com.gmibank.app.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmibank.app.domain.TPAccount;
import com.gmibank.app.domain.TPCustomer;

/**
 * Service Interface for managing {@link TPCustomer}.
 */
public interface TPCustomerService {

    /**
     * Save a tPCustomer.
     *
     * @param tPCustomer the entity to save.
     * @return the persisted entity.
     */
    TPCustomer save(TPCustomer tPCustomer);

    /**
     * Get all the tPCustomers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TPCustomer> findAll(Pageable pageable);

    /**
     * Get all the tPCustomers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TPCustomer> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" tPCustomer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPCustomer> findOne(Long id);

    /**
     * Delete the "id" tPCustomer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Set<TPAccount> getCustomerAccounts(Long id);
}
