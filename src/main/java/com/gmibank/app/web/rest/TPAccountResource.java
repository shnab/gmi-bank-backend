package com.gmibank.app.web.rest;

import com.gmibank.app.domain.TPAccount;
import com.gmibank.app.service.TPAccountService;
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
 * REST controller for managing {@link com.gmibank.app.domain.TPAccount}.
 */
@RestController
@RequestMapping("/api")
public class TPAccountResource {

    private final Logger log = LoggerFactory.getLogger(TPAccountResource.class);

    private static final String ENTITY_NAME = "tPAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPAccountService tPAccountService;

    public TPAccountResource(TPAccountService tPAccountService) {
        this.tPAccountService = tPAccountService;
    }

    /**
     * {@code POST  /tp-accounts} : Create a new tPAccount.
     *
     * @param tPAccount the tPAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPAccount, or with status {@code 400 (Bad Request)} if the tPAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tp-accounts")
    public ResponseEntity<TPAccount> createTPAccount(@Valid @RequestBody TPAccount tPAccount) throws URISyntaxException {
        log.debug("REST request to save TPAccount : {}", tPAccount);
        if (tPAccount.getId() != null) {
            throw new BadRequestAlertException("A new tPAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPAccount result = tPAccountService.save(tPAccount);
        return ResponseEntity.created(new URI("/api/tp-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tp-accounts} : Updates an existing tPAccount.
     *
     * @param tPAccount the tPAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPAccount,
     * or with status {@code 400 (Bad Request)} if the tPAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tp-accounts")
    public ResponseEntity<TPAccount> updateTPAccount(@Valid @RequestBody TPAccount tPAccount) throws URISyntaxException {
        log.debug("REST request to update TPAccount : {}", tPAccount);
        if (tPAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPAccount result = tPAccountService.save(tPAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tp-accounts} : get all the tPAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPAccounts in body.
     */
    @GetMapping("/tp-accounts")
    public List<TPAccount> getAllTPAccounts() {
        log.debug("REST request to get all TPAccounts");
        return tPAccountService.findAll();
    }

    /**
     * {@code GET  /tp-accounts/:id} : get the "id" tPAccount.
     *
     * @param id the id of the tPAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tp-accounts/{id}")
    public ResponseEntity<TPAccount> getTPAccount(@PathVariable Long id) {
        log.debug("REST request to get TPAccount : {}", id);
        Optional<TPAccount> tPAccount = tPAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPAccount);
    }

    /**
     * {@code DELETE  /tp-accounts/:id} : delete the "id" tPAccount.
     *
     * @param id the id of the tPAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tp-accounts/{id}")
    public ResponseEntity<Void> deleteTPAccount(@PathVariable Long id) {
        log.debug("REST request to delete TPAccount : {}", id);
        tPAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
