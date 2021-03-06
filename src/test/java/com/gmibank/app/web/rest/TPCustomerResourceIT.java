package com.gmibank.app.web.rest;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPCustomer;
import com.gmibank.app.repository.TPCustomerRepository;
import com.gmibank.app.service.TPCustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TPCustomerResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPCustomerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_INITIAL = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_INITIAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_SSN = "AAAAAAAAAA";
    private static final String UPDATED_SSN = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ZELLE_ENROLLED = false;
    private static final Boolean UPDATED_ZELLE_ENROLLED = true;

    @Autowired
    private TPCustomerRepository tPCustomerRepository;

    @Mock
    private TPCustomerRepository tPCustomerRepositoryMock;

    @Mock
    private TPCustomerService tPCustomerServiceMock;

    @Autowired
    private TPCustomerService tPCustomerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPCustomerMockMvc;

    private TPCustomer tPCustomer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPCustomer createEntity(EntityManager em) {
        TPCustomer tPCustomer = new TPCustomer()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleInitial(DEFAULT_MIDDLE_INITIAL)
            .email(DEFAULT_EMAIL)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .zipCode(DEFAULT_ZIP_CODE)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .ssn(DEFAULT_SSN)
            .createDate(DEFAULT_CREATE_DATE)
            .zelleEnrolled(DEFAULT_ZELLE_ENROLLED);
        return tPCustomer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPCustomer createUpdatedEntity(EntityManager em) {
        TPCustomer tPCustomer = new TPCustomer()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleInitial(UPDATED_MIDDLE_INITIAL)
            .email(UPDATED_EMAIL)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .ssn(UPDATED_SSN)
            .createDate(UPDATED_CREATE_DATE)
            .zelleEnrolled(UPDATED_ZELLE_ENROLLED);
        return tPCustomer;
    }

    @BeforeEach
    public void initTest() {
        tPCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPCustomer() throws Exception {
        int databaseSizeBeforeCreate = tPCustomerRepository.findAll().size();
        // Create the TPCustomer
        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isCreated());

        // Validate the TPCustomer in the database
        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeCreate + 1);
        TPCustomer testTPCustomer = tPCustomerList.get(tPCustomerList.size() - 1);
        assertThat(testTPCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTPCustomer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTPCustomer.getMiddleInitial()).isEqualTo(DEFAULT_MIDDLE_INITIAL);
        assertThat(testTPCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTPCustomer.getMobilePhoneNumber()).isEqualTo(DEFAULT_MOBILE_PHONE_NUMBER);
        assertThat(testTPCustomer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testTPCustomer.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testTPCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTPCustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testTPCustomer.getSsn()).isEqualTo(DEFAULT_SSN);
        assertThat(testTPCustomer.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTPCustomer.isZelleEnrolled()).isEqualTo(DEFAULT_ZELLE_ENROLLED);
    }

    @Test
    @Transactional
    public void createTPCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPCustomerRepository.findAll().size();

        // Create the TPCustomer with an existing ID
        tPCustomer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        // Validate the TPCustomer in the database
        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setFirstName(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setLastName(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiddleInitialIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setMiddleInitial(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setEmail(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setZipCode(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setAddress(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setCity(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSsnIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPCustomerRepository.findAll().size();
        // set the field null
        tPCustomer.setSsn(null);

        // Create the TPCustomer, which fails.


        restTPCustomerMockMvc.perform(post("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTPCustomers() throws Exception {
        // Initialize the database
        tPCustomerRepository.saveAndFlush(tPCustomer);

        // Get all the tPCustomerList
        restTPCustomerMockMvc.perform(get("/api/tp-customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleInitial").value(hasItem(DEFAULT_MIDDLE_INITIAL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].ssn").value(hasItem(DEFAULT_SSN)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].zelleEnrolled").value(hasItem(DEFAULT_ZELLE_ENROLLED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTPCustomersWithEagerRelationshipsIsEnabled() throws Exception {
        when(tPCustomerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTPCustomerMockMvc.perform(get("/api/tp-customers?eagerload=true"))
            .andExpect(status().isOk());

        verify(tPCustomerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTPCustomersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tPCustomerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTPCustomerMockMvc.perform(get("/api/tp-customers?eagerload=true"))
            .andExpect(status().isOk());

        verify(tPCustomerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTPCustomer() throws Exception {
        // Initialize the database
        tPCustomerRepository.saveAndFlush(tPCustomer);

        // Get the tPCustomer
        restTPCustomerMockMvc.perform(get("/api/tp-customers/{id}", tPCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPCustomer.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleInitial").value(DEFAULT_MIDDLE_INITIAL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.ssn").value(DEFAULT_SSN))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.zelleEnrolled").value(DEFAULT_ZELLE_ENROLLED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTPCustomer() throws Exception {
        // Get the tPCustomer
        restTPCustomerMockMvc.perform(get("/api/tp-customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPCustomer() throws Exception {
        // Initialize the database
        tPCustomerService.save(tPCustomer);

        int databaseSizeBeforeUpdate = tPCustomerRepository.findAll().size();

        // Update the tPCustomer
        TPCustomer updatedTPCustomer = tPCustomerRepository.findById(tPCustomer.getId()).get();
        // Disconnect from session so that the updates on updatedTPCustomer are not directly saved in db
        em.detach(updatedTPCustomer);
        updatedTPCustomer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleInitial(UPDATED_MIDDLE_INITIAL)
            .email(UPDATED_EMAIL)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .ssn(UPDATED_SSN)
            .createDate(UPDATED_CREATE_DATE)
            .zelleEnrolled(UPDATED_ZELLE_ENROLLED);

        restTPCustomerMockMvc.perform(put("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPCustomer)))
            .andExpect(status().isOk());

        // Validate the TPCustomer in the database
        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeUpdate);
        TPCustomer testTPCustomer = tPCustomerList.get(tPCustomerList.size() - 1);
        assertThat(testTPCustomer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTPCustomer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTPCustomer.getMiddleInitial()).isEqualTo(UPDATED_MIDDLE_INITIAL);
        assertThat(testTPCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTPCustomer.getMobilePhoneNumber()).isEqualTo(UPDATED_MOBILE_PHONE_NUMBER);
        assertThat(testTPCustomer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testTPCustomer.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testTPCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTPCustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testTPCustomer.getSsn()).isEqualTo(UPDATED_SSN);
        assertThat(testTPCustomer.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTPCustomer.isZelleEnrolled()).isEqualTo(UPDATED_ZELLE_ENROLLED);
    }

    @Test
    @Transactional
    public void updateNonExistingTPCustomer() throws Exception {
        int databaseSizeBeforeUpdate = tPCustomerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPCustomerMockMvc.perform(put("/api/tp-customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPCustomer)))
            .andExpect(status().isBadRequest());

        // Validate the TPCustomer in the database
        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPCustomer() throws Exception {
        // Initialize the database
        tPCustomerService.save(tPCustomer);

        int databaseSizeBeforeDelete = tPCustomerRepository.findAll().size();

        // Delete the tPCustomer
        restTPCustomerMockMvc.perform(delete("/api/tp-customers/{id}", tPCustomer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPCustomer> tPCustomerList = tPCustomerRepository.findAll();
        assertThat(tPCustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
