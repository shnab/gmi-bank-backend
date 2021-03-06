package com.gmibank.app.service;

import com.gmibank.app.domain.TPTransactionLog;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TPTransactionLog}.
 */
public interface TPTransactionLogService {

    /**
     * Save a tPTransactionLog.
     *
     * @param tPTransactionLog the entity to save.
     * @return the persisted entity.
     */
    TPTransactionLog save(TPTransactionLog tPTransactionLog);

    /**
     * Get all the tPTransactionLogs.
     *
     * @return the list of entities.
     */
    List<TPTransactionLog> findAll();


    /**
     * Get the "id" tPTransactionLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPTransactionLog> findOne(Long id);

    /**
     * Delete the "id" tPTransactionLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<TPTransactionLog> findAllByAccountId(Long id);
}
