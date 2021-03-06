package com.gmibank.app.service;

import com.gmibank.app.domain.TPEmployee;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TPEmployee}.
 */
public interface TPEmployeeService {

    /**
     * Save a tPEmployee.
     *
     * @param tPEmployee the entity to save.
     * @return the persisted entity.
     */
    TPEmployee save(TPEmployee tPEmployee);

    /**
     * Get all the tPEmployees.
     *
     * @return the list of entities.
     */
    List<TPEmployee> findAll();


    /**
     * Get the "id" tPEmployee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPEmployee> findOne(Long id);

    /**
     * Delete the "id" tPEmployee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
