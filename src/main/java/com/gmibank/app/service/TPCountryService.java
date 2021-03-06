package com.gmibank.app.service;

import com.gmibank.app.domain.TPCountry;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TPCountry}.
 */
public interface TPCountryService {

    /**
     * Save a tPCountry.
     *
     * @param tPCountry the entity to save.
     * @return the persisted entity.
     */
    TPCountry save(TPCountry tPCountry);

    /**
     * Get all the tPCountries.
     *
     * @return the list of entities.
     */
    List<TPCountry> findAll();


    /**
     * Get the "id" tPCountry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPCountry> findOne(Long id);

    /**
     * Delete the "id" tPCountry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
