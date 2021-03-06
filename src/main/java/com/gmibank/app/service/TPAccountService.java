package com.gmibank.app.service;

import com.gmibank.app.domain.TPAccount;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TPAccount}.
 */
public interface TPAccountService {

    /**
     * Save a tPAccount.
     *
     * @param tPAccount the entity to save.
     * @return the persisted entity.
     */
    TPAccount save(TPAccount tPAccount);

    /**
     * Get all the tPAccounts.
     *
     * @return the list of entities.
     */
    List<TPAccount> findAll();


    /**
     * Get the "id" tPAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPAccount> findOne(Long id);

    /**
     * Delete the "id" tPAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
