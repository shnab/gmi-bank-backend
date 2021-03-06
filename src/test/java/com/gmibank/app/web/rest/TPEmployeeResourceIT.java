package com.gmibank.app.web.rest;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPEmployee;
import com.gmibank.app.repository.TPEmployeeRepository;
import com.gmibank.app.service.TPEmployeeService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TPEmployeeResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPEmployeeResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    @Autowired
    private TPEmployeeRepository tPEmployeeRepository;

    @Autowired
    private TPEmployeeService tPEmployeeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPEmployeeMockMvc;

    private TPEmployee tPEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPEmployee createEntity(EntityManager em) {
        TPEmployee tPEmployee = new TPEmployee()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .hireDate(DEFAULT_HIRE_DATE)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .zipCode(DEFAULT_ZIP_CODE)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .ssn(DEFAULT_SSN)
            .createDate(DEFAULT_CREATE_DATE);
        return tPEmployee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPEmployee createUpdatedEntity(EntityManager em) {
        TPEmployee tPEmployee = new TPEmployee()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .hireDate(UPDATED_HIRE_DATE)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .ssn(UPDATED_SSN)
            .createDate(UPDATED_CREATE_DATE);
        return tPEmployee;
    }

    @BeforeEach
    public void initTest() {
        tPEmployee = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPEmployee() throws Exception {
        int databaseSizeBeforeCreate = tPEmployeeRepository.findAll().size();
        // Create the TPEmployee
        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isCreated());

        // Validate the TPEmployee in the database
        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        TPEmployee testTPEmployee = tPEmployeeList.get(tPEmployeeList.size() - 1);
        assertThat(testTPEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTPEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTPEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTPEmployee.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testTPEmployee.getMobilePhoneNumber()).isEqualTo(DEFAULT_MOBILE_PHONE_NUMBER);
        assertThat(testTPEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testTPEmployee.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testTPEmployee.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTPEmployee.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testTPEmployee.getSsn()).isEqualTo(DEFAULT_SSN);
        assertThat(testTPEmployee.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createTPEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPEmployeeRepository.findAll().size();

        // Create the TPEmployee with an existing ID
        tPEmployee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the TPEmployee in the database
        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setFirstName(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setLastName(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setEmail(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHireDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setHireDate(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setZipCode(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setAddress(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setCity(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSsnIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPEmployeeRepository.findAll().size();
        // set the field null
        tPEmployee.setSsn(null);

        // Create the TPEmployee, which fails.


        restTPEmployeeMockMvc.perform(post("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTPEmployees() throws Exception {
        // Initialize the database
        tPEmployeeRepository.saveAndFlush(tPEmployee);

        // Get all the tPEmployeeList
        restTPEmployeeMockMvc.perform(get("/api/tp-employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].ssn").value(hasItem(DEFAULT_SSN)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTPEmployee() throws Exception {
        // Initialize the database
        tPEmployeeRepository.saveAndFlush(tPEmployee);

        // Get the tPEmployee
        restTPEmployeeMockMvc.perform(get("/api/tp-employees/{id}", tPEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPEmployee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.ssn").value(DEFAULT_SSN))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTPEmployee() throws Exception {
        // Get the tPEmployee
        restTPEmployeeMockMvc.perform(get("/api/tp-employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPEmployee() throws Exception {
        // Initialize the database
        tPEmployeeService.save(tPEmployee);

        int databaseSizeBeforeUpdate = tPEmployeeRepository.findAll().size();

        // Update the tPEmployee
        TPEmployee updatedTPEmployee = tPEmployeeRepository.findById(tPEmployee.getId()).get();
        // Disconnect from session so that the updates on updatedTPEmployee are not directly saved in db
        em.detach(updatedTPEmployee);
        updatedTPEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .hireDate(UPDATED_HIRE_DATE)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .ssn(UPDATED_SSN)
            .createDate(UPDATED_CREATE_DATE);

        restTPEmployeeMockMvc.perform(put("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPEmployee)))
            .andExpect(status().isOk());

        // Validate the TPEmployee in the database
        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeUpdate);
        TPEmployee testTPEmployee = tPEmployeeList.get(tPEmployeeList.size() - 1);
        assertThat(testTPEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTPEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTPEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTPEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testTPEmployee.getMobilePhoneNumber()).isEqualTo(UPDATED_MOBILE_PHONE_NUMBER);
        assertThat(testTPEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testTPEmployee.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testTPEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTPEmployee.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testTPEmployee.getSsn()).isEqualTo(UPDATED_SSN);
        assertThat(testTPEmployee.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTPEmployee() throws Exception {
        int databaseSizeBeforeUpdate = tPEmployeeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPEmployeeMockMvc.perform(put("/api/tp-employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the TPEmployee in the database
        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPEmployee() throws Exception {
        // Initialize the database
        tPEmployeeService.save(tPEmployee);

        int databaseSizeBeforeDelete = tPEmployeeRepository.findAll().size();

        // Delete the tPEmployee
        restTPEmployeeMockMvc.perform(delete("/api/tp-employees/{id}", tPEmployee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPEmployee> tPEmployeeList = tPEmployeeRepository.findAll();
        assertThat(tPEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
