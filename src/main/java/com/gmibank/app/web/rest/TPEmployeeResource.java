package com.gmibank.app.web.rest;

import com.gmibank.app.domain.TPEmployee;
import com.gmibank.app.service.TPEmployeeService;
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
 * REST controller for managing {@link com.gmibank.app.domain.TPEmployee}.
 */
@RestController
@RequestMapping("/api")
public class TPEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(TPEmployeeResource.class);

    private static final String ENTITY_NAME = "tPEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPEmployeeService tPEmployeeService;

    public TPEmployeeResource(TPEmployeeService tPEmployeeService) {
        this.tPEmployeeService = tPEmployeeService;
    }

    /**
     * {@code POST  /tp-employees} : Create a new tPEmployee.
     *
     * @param tPEmployee the tPEmployee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPEmployee, or with status {@code 400 (Bad Request)} if the tPEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tp-employees")
    public ResponseEntity<TPEmployee> createTPEmployee(@Valid @RequestBody TPEmployee tPEmployee) throws URISyntaxException {
        log.debug("REST request to save TPEmployee : {}", tPEmployee);
        if (tPEmployee.getId() != null) {
            throw new BadRequestAlertException("A new tPEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPEmployee result = tPEmployeeService.save(tPEmployee);
        return ResponseEntity.created(new URI("/api/tp-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tp-employees} : Updates an existing tPEmployee.
     *
     * @param tPEmployee the tPEmployee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPEmployee,
     * or with status {@code 400 (Bad Request)} if the tPEmployee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPEmployee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tp-employees")
    public ResponseEntity<TPEmployee> updateTPEmployee(@Valid @RequestBody TPEmployee tPEmployee) throws URISyntaxException {
        log.debug("REST request to update TPEmployee : {}", tPEmployee);
        if (tPEmployee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPEmployee result = tPEmployeeService.save(tPEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPEmployee.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tp-employees} : get all the tPEmployees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPEmployees in body.
     */
    @GetMapping("/tp-employees")
    public List<TPEmployee> getAllTPEmployees() {
        log.debug("REST request to get all TPEmployees");
        return tPEmployeeService.findAll();
    }

    /**
     * {@code GET  /tp-employees/:id} : get the "id" tPEmployee.
     *
     * @param id the id of the tPEmployee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPEmployee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tp-employees/{id}")
    public ResponseEntity<TPEmployee> getTPEmployee(@PathVariable Long id) {
        log.debug("REST request to get TPEmployee : {}", id);
        Optional<TPEmployee> tPEmployee = tPEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPEmployee);
    }

    /**
     * {@code DELETE  /tp-employees/:id} : delete the "id" tPEmployee.
     *
     * @param id the id of the tPEmployee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tp-employees/{id}")
    public ResponseEntity<Void> deleteTPEmployee(@PathVariable Long id) {
        log.debug("REST request to delete TPEmployee : {}", id);
        tPEmployeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
