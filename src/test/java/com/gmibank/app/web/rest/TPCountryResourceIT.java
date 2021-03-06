package com.gmibank.app.web.rest;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPCountry;
import com.gmibank.app.repository.TPCountryRepository;
import com.gmibank.app.service.TPCountryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TPCountryResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPCountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TPCountryRepository tPCountryRepository;

    @Autowired
    private TPCountryService tPCountryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPCountryMockMvc;

    private TPCountry tPCountry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPCountry createEntity(EntityManager em) {
        TPCountry tPCountry = new TPCountry()
            .name(DEFAULT_NAME);
        return tPCountry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPCountry createUpdatedEntity(EntityManager em) {
        TPCountry tPCountry = new TPCountry()
            .name(UPDATED_NAME);
        return tPCountry;
    }

    @BeforeEach
    public void initTest() {
        tPCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPCountry() throws Exception {
        int databaseSizeBeforeCreate = tPCountryRepository.findAll().size();
        // Create the TPCountry
        restTPCountryMockMvc.perform(post("/api/tp-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCountry)))
            .andExpect(status().isCreated());

        // Validate the TPCountry in the database
        List<TPCountry> tPCountryList = tPCountryRepository.findAll();
        assertThat(tPCountryList).hasSize(databaseSizeBeforeCreate + 1);
        TPCountry testTPCountry = tPCountryList.get(tPCountryList.size() - 1);
        assertThat(testTPCountry.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTPCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPCountryRepository.findAll().size();

        // Create the TPCountry with an existing ID
        tPCountry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPCountryMockMvc.perform(post("/api/tp-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCountry)))
            .andExpect(status().isBadRequest());

        // Validate the TPCountry in the database
        List<TPCountry> tPCountryList = tPCountryRepository.findAll();
        assertThat(tPCountryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTPCountries() throws Exception {
        // Initialize the database
        tPCountryRepository.saveAndFlush(tPCountry);

        // Get all the tPCountryList
        restTPCountryMockMvc.perform(get("/api/tp-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTPCountry() throws Exception {
        // Initialize the database
        tPCountryRepository.saveAndFlush(tPCountry);

        // Get the tPCountry
        restTPCountryMockMvc.perform(get("/api/tp-countries/{id}", tPCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPCountry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTPCountry() throws Exception {
        // Get the tPCountry
        restTPCountryMockMvc.perform(get("/api/tp-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPCountry() throws Exception {
        // Initialize the database
        tPCountryService.save(tPCountry);

        int databaseSizeBeforeUpdate = tPCountryRepository.findAll().size();

        // Update the tPCountry
        TPCountry updatedTPCountry = tPCountryRepository.findById(tPCountry.getId()).get();
        // Disconnect from session so that the updates on updatedTPCountry are not directly saved in db
        em.detach(updatedTPCountry);
        updatedTPCountry
            .name(UPDATED_NAME);

        restTPCountryMockMvc.perform(put("/api/tp-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPCountry)))
            .andExpect(status().isOk());

        // Validate the TPCountry in the database
        List<TPCountry> tPCountryList = tPCountryRepository.findAll();
        assertThat(tPCountryList).hasSize(databaseSizeBeforeUpdate);
        TPCountry testTPCountry = tPCountryList.get(tPCountryList.size() - 1);
        assertThat(testTPCountry.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTPCountry() throws Exception {
        int databaseSizeBeforeUpdate = tPCountryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPCountryMockMvc.perform(put("/api/tp-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCountry)))
            .andExpect(status().isBadRequest());

        // Validate the TPCountry in the database
        List<TPCountry> tPCountryList = tPCountryRepository.findAll();
        assertThat(tPCountryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPCountry() throws Exception {
        // Initialize the database
        tPCountryService.save(tPCountry);

        int databaseSizeBeforeDelete = tPCountryRepository.findAll().size();

        // Delete the tPCountry
        restTPCountryMockMvc.perform(delete("/api/tp-countries/{id}", tPCountry.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPCountry> tPCountryList = tPCountryRepository.findAll();
        assertThat(tPCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
