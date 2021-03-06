package com.gmibank.app.service;

import com.gmibank.app.domain.TPState;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TPState}.
 */
public interface TPStateService {

    /**
     * Save a tPState.
     *
     * @param tPState the entity to save.
     * @return the persisted entity.
     */
    TPState save(TPState tPState);

    /**
     * Get all the tPStates.
     *
     * @return the list of entities.
     */
    List<TPState> findAll();


    /**
     * Get the "id" tPState.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPState> findOne(Long id);

    /**
     * Delete the "id" tPState.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
