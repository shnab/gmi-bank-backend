package com.gmibank.app.web.rest;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPState;
import com.gmibank.app.repository.TPStateRepository;
import com.gmibank.app.service.TPStateService;

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
 * Integration tests for the {@link TPStateResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPStateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TPStateRepository tPStateRepository;

    @Autowired
    private TPStateService tPStateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPStateMockMvc;

    private TPState tPState;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPState createEntity(EntityManager em) {
        TPState tPState = new TPState()
            .name(DEFAULT_NAME);
        return tPState;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPState createUpdatedEntity(EntityManager em) {
        TPState tPState = new TPState()
            .name(UPDATED_NAME);
        return tPState;
    }

    @BeforeEach
    public void initTest() {
        tPState = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPState() throws Exception {
        int databaseSizeBeforeCreate = tPStateRepository.findAll().size();
        // Create the TPState
        restTPStateMockMvc.perform(post("/api/tp-states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPState)))
            .andExpect(status().isCreated());

        // Validate the TPState in the database
        List<TPState> tPStateList = tPStateRepository.findAll();
        assertThat(tPStateList).hasSize(databaseSizeBeforeCreate + 1);
        TPState testTPState = tPStateList.get(tPStateList.size() - 1);
        assertThat(testTPState.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTPStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPStateRepository.findAll().size();

        // Create the TPState with an existing ID
        tPState.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPStateMockMvc.perform(post("/api/tp-states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPState)))
            .andExpect(status().isBadRequest());

        // Validate the TPState in the database
        List<TPState> tPStateList = tPStateRepository.findAll();
        assertThat(tPStateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTPStates() throws Exception {
        // Initialize the database
        tPStateRepository.saveAndFlush(tPState);

        // Get all the tPStateList
        restTPStateMockMvc.perform(get("/api/tp-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPState.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTPState() throws Exception {
        // Initialize the database
        tPStateRepository.saveAndFlush(tPState);

        // Get the tPState
        restTPStateMockMvc.perform(get("/api/tp-states/{id}", tPState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPState.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTPState() throws Exception {
        // Get the tPState
        restTPStateMockMvc.perform(get("/api/tp-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPState() throws Exception {
        // Initialize the database
        tPStateService.save(tPState);

        int databaseSizeBeforeUpdate = tPStateRepository.findAll().size();

        // Update the tPState
        TPState updatedTPState = tPStateRepository.findById(tPState.getId()).get();
        // Disconnect from session so that the updates on updatedTPState are not directly saved in db
        em.detach(updatedTPState);
        updatedTPState
            .name(UPDATED_NAME);

        restTPStateMockMvc.perform(put("/api/tp-states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPState)))
            .andExpect(status().isOk());

        // Validate the TPState in the database
        List<TPState> tPStateList = tPStateRepository.findAll();
        assertThat(tPStateList).hasSize(databaseSizeBeforeUpdate);
        TPState testTPState = tPStateList.get(tPStateList.size() - 1);
        assertThat(testTPState.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTPState() throws Exception {
        int databaseSizeBeforeUpdate = tPStateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPStateMockMvc.perform(put("/api/tp-states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPState)))
            .andExpect(status().isBadRequest());

        // Validate the TPState in the database
        List<TPState> tPStateList = tPStateRepository.findAll();
        assertThat(tPStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPState() throws Exception {
        // Initialize the database
        tPStateService.save(tPState);

        int databaseSizeBeforeDelete = tPStateRepository.findAll().size();

        // Delete the tPState
        restTPStateMockMvc.perform(delete("/api/tp-states/{id}", tPState.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPState> tPStateList = tPStateRepository.findAll();
        assertThat(tPStateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
