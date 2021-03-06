package com.gmibank.app.web.rest;

import com.gmibank.app.domain.TPCountry;
import com.gmibank.app.service.TPCountryService;
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
 * REST controller for managing {@link com.gmibank.app.domain.TPCountry}.
 */
@RestController
@RequestMapping("/api")
public class TPCountryResource {

    private final Logger log = LoggerFactory.getLogger(TPCountryResource.class);

    private static final String ENTITY_NAME = "tPCountry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPCountryService tPCountryService;

    public TPCountryResource(TPCountryService tPCountryService) {
        this.tPCountryService = tPCountryService;
    }

    /**
     * {@code POST  /tp-countries} : Create a new tPCountry.
     *
     * @param tPCountry the tPCountry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPCountry, or with status {@code 400 (Bad Request)} if the tPCountry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tp-countries")
    public ResponseEntity<TPCountry> createTPCountry(@RequestBody TPCountry tPCountry) throws URISyntaxException {
        log.debug("REST request to save TPCountry : {}", tPCountry);
        if (tPCountry.getId() != null) {
            throw new BadRequestAlertException("A new tPCountry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPCountry result = tPCountryService.save(tPCountry);
        return ResponseEntity.created(new URI("/api/tp-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tp-countries} : Updates an existing tPCountry.
     *
     * @param tPCountry the tPCountry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPCountry,
     * or with status {@code 400 (Bad Request)} if the tPCountry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPCountry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tp-countries")
    public ResponseEntity<TPCountry> updateTPCountry(@RequestBody TPCountry tPCountry) throws URISyntaxException {
        log.debug("REST request to update TPCountry : {}", tPCountry);
        if (tPCountry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPCountry result = tPCountryService.save(tPCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPCountry.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tp-countries} : get all the tPCountries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPCountries in body.
     */
    @GetMapping("/tp-countries")
    public List<TPCountry> getAllTPCountries() {
        log.debug("REST request to get all TPCountries");
        return tPCountryService.findAll();
    }

    /**
     * {@code GET  /tp-countries/:id} : get the "id" tPCountry.
     *
     * @param id the id of the tPCountry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPCountry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tp-countries/{id}")
    public ResponseEntity<TPCountry> getTPCountry(@PathVariable Long id) {
        log.debug("REST request to get TPCountry : {}", id);
        Optional<TPCountry> tPCountry = tPCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPCountry);
    }

    /**
     * {@code DELETE  /tp-countries/:id} : delete the "id" tPCountry.
     *
     * @param id the id of the tPCountry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tp-countries/{id}")
    public ResponseEntity<Void> deleteTPCountry(@PathVariable Long id) {
        log.debug("REST request to delete TPCountry : {}", id);
        tPCountryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
