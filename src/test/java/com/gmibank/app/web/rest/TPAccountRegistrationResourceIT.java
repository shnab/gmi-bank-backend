package com.gmibank.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPAccountRegistration;
import com.gmibank.app.repository.TPAccountRegistrationRepository;
import com.gmibank.app.service.TPAccountRegistrationService;
import com.gmibank.app.service.dto.TPAccountRegistrationDTO;

/**
 * Integration tests for the {@link TPAccountRegistrationResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPAccountRegistrationResourceIT {

    private static final String DEFAULT_SSN = "/026-36-8258/";
    private static final String UPDATED_SSN = "/008-06-5333/";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE_NUMBER = "950-658-5135";
    private static final String UPDATED_MOBILE_PHONE_NUMBER = "790-543-6343";

    @Autowired
    private TPAccountRegistrationRepository tPAccountRegistrationRepository;


    @Autowired
    private TPAccountRegistrationService tPAccountRegistrationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPAccountRegistrationMockMvc;

    private TPAccountRegistration tPAccountRegistration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPAccountRegistration createEntity(EntityManager em) {
        TPAccountRegistration tPAccountRegistration = new TPAccountRegistration()
            .ssn(DEFAULT_SSN)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER);
        return tPAccountRegistration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPAccountRegistration createUpdatedEntity(EntityManager em) {
        TPAccountRegistration tPAccountRegistration = new TPAccountRegistration()
            .ssn(UPDATED_SSN)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER);
        return tPAccountRegistration;
    }

    @BeforeEach
    public void initTest() {
        tPAccountRegistration = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPAccountRegistration() throws Exception {
        int databaseSizeBeforeCreate = tPAccountRegistrationRepository.findAll().size();
        // Create the TPAccountRegistration
        
        restTPAccountRegistrationMockMvc.perform(post("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isCreated());

        // Validate the TPAccountRegistration in the database
        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        TPAccountRegistration testTPAccountRegistration = tPAccountRegistrationList.get(tPAccountRegistrationList.size() - 1);
        assertThat(testTPAccountRegistration.getSsn()).isEqualTo(DEFAULT_SSN);
        assertThat(testTPAccountRegistration.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTPAccountRegistration.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTPAccountRegistration.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTPAccountRegistration.getMobilePhoneNumber()).isEqualTo(DEFAULT_MOBILE_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createTPAccountRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPAccountRegistrationRepository.findAll().size();

        // Create the TPAccountRegistration with an existing ID
        tPAccountRegistration.setId(1L);
       

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPAccountRegistrationMockMvc.perform(post("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isBadRequest());

        // Validate the TPAccountRegistration in the database
        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSsnIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRegistrationRepository.findAll().size();
        // set the field null
        tPAccountRegistration.setSsn(null);

     

        restTPAccountRegistrationMockMvc.perform(post("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isBadRequest());

        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRegistrationRepository.findAll().size();
        // set the field null
        tPAccountRegistration.setFirstName(null);

     

        restTPAccountRegistrationMockMvc.perform(post("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isBadRequest());

        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRegistrationRepository.findAll().size();
        // set the field null
        tPAccountRegistration.setLastName(null);

   
        restTPAccountRegistrationMockMvc.perform(post("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isBadRequest());

        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTPAccountRegistrations() throws Exception {
        // Initialize the database
        tPAccountRegistrationRepository.saveAndFlush(tPAccountRegistration);

        // Get all the tPAccountRegistrationList
        restTPAccountRegistrationMockMvc.perform(get("/api/tp-account-registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPAccountRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].ssn").value(hasItem(DEFAULT_SSN)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTPAccountRegistration() throws Exception {
        // Initialize the database
        tPAccountRegistrationRepository.saveAndFlush(tPAccountRegistration);

        // Get the tPAccountRegistration
        restTPAccountRegistrationMockMvc.perform(get("/api/tp-account-registrations/{id}", tPAccountRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPAccountRegistration.getId().intValue()))
            .andExpect(jsonPath("$.ssn").value(DEFAULT_SSN))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingTPAccountRegistration() throws Exception {
        // Get the tPAccountRegistration
        restTPAccountRegistrationMockMvc.perform(get("/api/tp-account-registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPAccountRegistration() throws Exception {
        // Initialize the database
        tPAccountRegistrationRepository.saveAndFlush(tPAccountRegistration);

        int databaseSizeBeforeUpdate = tPAccountRegistrationRepository.findAll().size();

        // Update the tPAccountRegistration
        TPAccountRegistration updatedTPAccountRegistration = tPAccountRegistrationRepository.findById(tPAccountRegistration.getId()).get();
        // Disconnect from session so that the updates on updatedTPAccountRegistration are not directly saved in db
        em.detach(updatedTPAccountRegistration);
        updatedTPAccountRegistration
            .ssn(UPDATED_SSN)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER);
     

        restTPAccountRegistrationMockMvc.perform(put("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isOk());

        // Validate the TPAccountRegistration in the database
        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeUpdate);
        TPAccountRegistration testTPAccountRegistration = tPAccountRegistrationList.get(tPAccountRegistrationList.size() - 1);
        assertThat(testTPAccountRegistration.getSsn()).isEqualTo(UPDATED_SSN);
        assertThat(testTPAccountRegistration.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTPAccountRegistration.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTPAccountRegistration.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTPAccountRegistration.getMobilePhoneNumber()).isEqualTo(UPDATED_MOBILE_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTPAccountRegistration() throws Exception {
        int databaseSizeBeforeUpdate = tPAccountRegistrationRepository.findAll().size();


        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPAccountRegistrationMockMvc.perform(put("/api/tp-account-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccountRegistration)))
            .andExpect(status().isBadRequest());

        // Validate the TPAccountRegistration in the database
        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPAccountRegistration() throws Exception {
        // Initialize the database
        tPAccountRegistrationRepository.saveAndFlush(tPAccountRegistration);

        int databaseSizeBeforeDelete = tPAccountRegistrationRepository.findAll().size();

        // Delete the tPAccountRegistration
        restTPAccountRegistrationMockMvc.perform(delete("/api/tp-account-registrations/{id}", tPAccountRegistration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPAccountRegistration> tPAccountRegistrationList = tPAccountRegistrationRepository.findAll();
        assertThat(tPAccountRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
