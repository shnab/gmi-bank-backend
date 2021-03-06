package com.gmibank.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmibank.app.domain.TPAccountRegistration;
import com.gmibank.app.service.TPAccountRegistrationService;
import com.gmibank.app.web.rest.errors.BadRequestAlertException;
import com.gmibank.app.web.rest.errors.SSNNotFoundException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gmibank.app.domain.TPAccountRegistration}.
 */
@RestController
@RequestMapping("/api")
public class TPAccountRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(TPAccountRegistrationResource.class);

    private static final String ENTITY_NAME = "tPAccountRegistration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPAccountRegistrationService tPAccountRegistrationService;

    public TPAccountRegistrationResource(TPAccountRegistrationService tPAccountRegistrationService) {
        this.tPAccountRegistrationService = tPAccountRegistrationService;
    }

    /**
     * {@code POST  /tp-account-registrations} : Create a new tPAccountRegistration.
     *
     * @param tPAccountRegistrationDTO the tPAccountRegistrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPAccountRegistrationDTO, or with status {@code 400 (Bad Request)} if the tPAccountRegistration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tp-account-registrations")
    public ResponseEntity<TPAccountRegistration> createTPAccountRegistration(@Valid @RequestBody TPAccountRegistration tPAccountRegistration) throws URISyntaxException {
        log.debug("REST request to save TPAccountRegistration : {}", tPAccountRegistration);
        if (tPAccountRegistration.getId() != null) {
            throw new BadRequestAlertException("A new tPAccountRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPAccountRegistration result = tPAccountRegistrationService.save(tPAccountRegistration);
        return ResponseEntity.created(new URI("/api/tp-account-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tp-account-registrations} : Updates an existing tPAccountRegistration.
     *
     * @param tPAccountRegistrationDTO the tPAccountRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPAccountRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the tPAccountRegistrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPAccountRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tp-account-registrations")
    public ResponseEntity<TPAccountRegistration> updateTPAccountRegistration(@Valid @RequestBody TPAccountRegistration tPAccountRegistration) throws URISyntaxException {
        log.debug("REST request to update TPAccountRegistration : {}", tPAccountRegistration);
        if (tPAccountRegistration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPAccountRegistration result = tPAccountRegistrationService.save(tPAccountRegistration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPAccountRegistration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tp-account-registrations} : get all the tPAccountRegistrations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPAccountRegistrations in body.
     */
    @GetMapping("/tp-account-registrations")
    public List<TPAccountRegistration> getAllTPAccountRegistrations() {
        log.debug("REST request to get all TPAccountRegistrations");
        return tPAccountRegistrationService.findAll();
    }

    /**
     * {@code GET  /tp-account-registrations/:id} : get the "id" tPAccountRegistration.
     *
     * @param id the id of the tPAccountRegistrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPAccountRegistrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tp-account-registrations/{id}")
    public ResponseEntity<TPAccountRegistration> getTPAccountRegistration(@PathVariable Long id) {
        log.debug("REST request to get TPAccountRegistration : {}", id);
        Optional<TPAccountRegistration> tPAccountRegistration = tPAccountRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPAccountRegistration);
    }

    /**
     * {@code DELETE  /tp-account-registrations/:id} : delete the "id" tPAccountRegistration.
     *
     * @param id the id of the tPAccountRegistrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tp-account-registrations/{id}")
    public ResponseEntity<Void> deleteTPAccountRegistration(@PathVariable Long id) {
        log.debug("REST request to delete TPAccountRegistration : {}", id);
        tPAccountRegistrationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/tp-account-registrations/find/{ssn}")
    public ResponseEntity<TPAccountRegistration> getAccountRegistrationBySSN(@PathVariable String ssn) {
        log.debug("REST request to get all TPAccountRegistrations");
        List<TPAccountRegistration> list = tPAccountRegistrationService.findOneBySSN(ssn);
        
        TPAccountRegistration ar=new TPAccountRegistration();
        
        if(!list.isEmpty()) {
        	ar=list.get(0); 
        } else {
            throw new SSNNotFoundException();
        }
        
        return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ar.getSsn()))
        .body(ar);
    }
    
}
