package com.gmibank.app.web.rest;

import com.gmibank.app.domain.TPState;
import com.gmibank.app.service.TPStateService;
import com.gmibank.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gmibank.app.domain.TPState}.
 */
@RestController
@RequestMapping("/api")
public class TPStateResource {

    private final Logger log = LoggerFactory.getLogger(TPStateResource.class);

    private static final String ENTITY_NAME = "tPState";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPStateService tPStateService;

    public TPStateResource(TPStateService tPStateService) {
        this.tPStateService = tPStateService;
    }

    /**
     * {@code POST  /tp-states} : Create a new tPState.
     *
     * @param tPState the tPState to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPState, or with status {@code 400 (Bad Request)} if the tPState has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tp-states")
    public ResponseEntity<TPState> createTPState(@RequestBody TPState tPState) throws URISyntaxException {
        log.debug("REST request to save TPState : {}", tPState);
        if (tPState.getId() != null) {
            throw new BadRequestAlertException("A new tPState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPState result = tPStateService.save(tPState);
        return ResponseEntity.created(new URI("/api/tp-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tp-states} : Updates an existing tPState.
     *
     * @param tPState the tPState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPState,
     * or with status {@code 400 (Bad Request)} if the tPState is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tp-states")
    public ResponseEntity<TPState> updateTPState(@RequestBody TPState tPState) throws URISyntaxException {
        log.debug("REST request to update TPState : {}", tPState);
        if (tPState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPState result = tPStateService.save(tPState);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPState.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tp-states} : get all the tPStates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPStates in body.
     */
    @GetMapping("/tp-states")
    public List<TPState> getAllTPStates() {
        log.debug("REST request to get all TPStates");
        List<TPState> findAll = tPStateService.findAll();
        return findAll;
    }

    /**
     * {@code GET  /tp-states/:id} : get the "id" tPState.
     *
     * @param id the id of the tPState to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPState, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tp-states/{id}")
    public ResponseEntity<TPState> getTPState(@PathVariable Long id) {
        log.debug("REST request to get TPState : {}", id);
        Optional<TPState> tPState = tPStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPState);
    }

    /**
     * {@code DELETE  /tp-states/:id} : delete the "id" tPState.
     *
     * @param id the id of the tPState to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tp-states/{id}")
    public ResponseEntity<Void> deleteTPState(@PathVariable Long id) {
        log.debug("REST request to delete TPState : {}", id);
        tPStateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
