package com.gmibank.app.web.rest;

import com.gmibank.app.domain.TPTransactionLog;
import com.gmibank.app.service.TPTransactionLogService;
import com.gmibank.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gmibank.app.domain.TPTransactionLog}.
 */
@RestController
@RequestMapping("/api")
public class TPTransactionLogResource {

    private final Logger log = LoggerFactory.getLogger(TPTransactionLogResource.class);

    private static final String ENTITY_NAME = "tPTransactionLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPTransactionLogService tPTransactionLogService;

    public TPTransactionLogResource(TPTransactionLogService tPTransactionLogService) {
        this.tPTransactionLogService = tPTransactionLogService;
    }

    /**
     * {@code POST  /tp-transaction-logs} : Create a new tPTransactionLog.
     *
     * @param tPTransactionLog the tPTransactionLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPTransactionLog, or with status {@code 400 (Bad Request)} if the tPTransactionLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tp-transaction-logs")
    public ResponseEntity<TPTransactionLog> createTPTransactionLog(@Valid @RequestBody TPTransactionLog tPTransactionLog) throws URISyntaxException {
        log.debug("REST request to save TPTransactionLog : {}", tPTransactionLog);
        if (tPTransactionLog.getId() != null) {
            throw new BadRequestAlertException("A new tPTransactionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPTransactionLog result = tPTransactionLogService.save(tPTransactionLog);
        return ResponseEntity.created(new URI("/api/tp-transaction-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tp-transaction-logs} : Updates an existing tPTransactionLog.
     *
     * @param tPTransactionLog the tPTransactionLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPTransactionLog,
     * or with status {@code 400 (Bad Request)} if the tPTransactionLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPTransactionLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tp-transaction-logs")
    public ResponseEntity<TPTransactionLog> updateTPTransactionLog(@Valid @RequestBody TPTransactionLog tPTransactionLog) throws URISyntaxException {
        log.debug("REST request to update TPTransactionLog : {}", tPTransactionLog);
        if (tPTransactionLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPTransactionLog result = tPTransactionLogService.save(tPTransactionLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPTransactionLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tp-transaction-logs} : get all the tPTransactionLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPTransactionLogs in body.
     */
    @GetMapping("/tp-transaction-logs")
    public List<TPTransactionLog> getAllTPTransactionLogs() {
        log.debug("REST request to get all TPTransactionLogs");
        return tPTransactionLogService.findAll();
    }

    /**
     * {@code GET  /tp-transaction-logs/:id} : get the "id" tPTransactionLog.
     *
     * @param id the id of the tPTransactionLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPTransactionLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tp-transaction-logs/{id}")
    public ResponseEntity<TPTransactionLog> getTPTransactionLog(@PathVariable Long id) {
        log.debug("REST request to get TPTransactionLog : {}", id);
        Optional<TPTransactionLog> tPTransactionLog = tPTransactionLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPTransactionLog);
    }

    /**
     * {@code DELETE  /tp-transaction-logs/:id} : delete the "id" tPTransactionLog.
     *
     * @param id the id of the tPTransactionLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tp-transaction-logs/{id}")
    public ResponseEntity<Void> deleteTPTransactionLog(@PathVariable Long id) {
        log.debug("REST request to delete TPTransactionLog : {}", id);
        tPTransactionLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/tp-transaction-logs/account/{id}")
    public List<TPTransactionLog> getAllTPTransactionLogsByAccountId(@PathVariable Long id) {
        log.debug("REST request to get all TPTransactionLogs");
        
        return tPTransactionLogService.findAllByAccountId(id);
    }
    
}
