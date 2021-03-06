package com.gmibank.app.web.rest;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPTransactionLog;
import com.gmibank.app.repository.TPTransactionLogRepository;
import com.gmibank.app.service.TPTransactionLogService;

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
 * Integration tests for the {@link TPTransactionLogResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPTransactionLogResourceIT {

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TRANSACTION_AMOUNT = 1;
    private static final Integer UPDATED_TRANSACTION_AMOUNT = 2;

    private static final Integer DEFAULT_NEW_BALANCE = 1;
    private static final Integer UPDATED_NEW_BALANCE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TPTransactionLogRepository tPTransactionLogRepository;

    @Autowired
    private TPTransactionLogService tPTransactionLogService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPTransactionLogMockMvc;

    private TPTransactionLog tPTransactionLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPTransactionLog createEntity(EntityManager em) {
        TPTransactionLog tPTransactionLog = new TPTransactionLog()
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .newBalance(DEFAULT_NEW_BALANCE)
            .description(DEFAULT_DESCRIPTION);
        return tPTransactionLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPTransactionLog createUpdatedEntity(EntityManager em) {
        TPTransactionLog tPTransactionLog = new TPTransactionLog()
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .newBalance(UPDATED_NEW_BALANCE)
            .description(UPDATED_DESCRIPTION);
        return tPTransactionLog;
    }

    @BeforeEach
    public void initTest() {
        tPTransactionLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPTransactionLog() throws Exception {
        int databaseSizeBeforeCreate = tPTransactionLogRepository.findAll().size();
        // Create the TPTransactionLog
        restTPTransactionLogMockMvc.perform(post("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isCreated());

        // Validate the TPTransactionLog in the database
        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeCreate + 1);
        TPTransactionLog testTPTransactionLog = tPTransactionLogList.get(tPTransactionLogList.size() - 1);
        assertThat(testTPTransactionLog.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testTPTransactionLog.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testTPTransactionLog.getNewBalance()).isEqualTo(DEFAULT_NEW_BALANCE);
        assertThat(testTPTransactionLog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTPTransactionLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPTransactionLogRepository.findAll().size();

        // Create the TPTransactionLog with an existing ID
        tPTransactionLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPTransactionLogMockMvc.perform(post("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isBadRequest());

        // Validate the TPTransactionLog in the database
        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPTransactionLogRepository.findAll().size();
        // set the field null
        tPTransactionLog.setTransactionDate(null);

        // Create the TPTransactionLog, which fails.


        restTPTransactionLogMockMvc.perform(post("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isBadRequest());

        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPTransactionLogRepository.findAll().size();
        // set the field null
        tPTransactionLog.setTransactionAmount(null);

        // Create the TPTransactionLog, which fails.


        restTPTransactionLogMockMvc.perform(post("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isBadRequest());

        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNewBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPTransactionLogRepository.findAll().size();
        // set the field null
        tPTransactionLog.setNewBalance(null);

        // Create the TPTransactionLog, which fails.


        restTPTransactionLogMockMvc.perform(post("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isBadRequest());

        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPTransactionLogRepository.findAll().size();
        // set the field null
        tPTransactionLog.setDescription(null);

        // Create the TPTransactionLog, which fails.


        restTPTransactionLogMockMvc.perform(post("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isBadRequest());

        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTPTransactionLogs() throws Exception {
        // Initialize the database
        tPTransactionLogRepository.saveAndFlush(tPTransactionLog);

        // Get all the tPTransactionLogList
        restTPTransactionLogMockMvc.perform(get("/api/tp-transaction-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPTransactionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].newBalance").value(hasItem(DEFAULT_NEW_BALANCE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTPTransactionLog() throws Exception {
        // Initialize the database
        tPTransactionLogRepository.saveAndFlush(tPTransactionLog);

        // Get the tPTransactionLog
        restTPTransactionLogMockMvc.perform(get("/api/tp-transaction-logs/{id}", tPTransactionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPTransactionLog.getId().intValue()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT))
            .andExpect(jsonPath("$.newBalance").value(DEFAULT_NEW_BALANCE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingTPTransactionLog() throws Exception {
        // Get the tPTransactionLog
        restTPTransactionLogMockMvc.perform(get("/api/tp-transaction-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPTransactionLog() throws Exception {
        // Initialize the database
        tPTransactionLogService.save(tPTransactionLog);

        int databaseSizeBeforeUpdate = tPTransactionLogRepository.findAll().size();

        // Update the tPTransactionLog
        TPTransactionLog updatedTPTransactionLog = tPTransactionLogRepository.findById(tPTransactionLog.getId()).get();
        // Disconnect from session so that the updates on updatedTPTransactionLog are not directly saved in db
        em.detach(updatedTPTransactionLog);
        updatedTPTransactionLog
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .newBalance(UPDATED_NEW_BALANCE)
            .description(UPDATED_DESCRIPTION);

        restTPTransactionLogMockMvc.perform(put("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPTransactionLog)))
            .andExpect(status().isOk());

        // Validate the TPTransactionLog in the database
        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeUpdate);
        TPTransactionLog testTPTransactionLog = tPTransactionLogList.get(tPTransactionLogList.size() - 1);
        assertThat(testTPTransactionLog.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testTPTransactionLog.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testTPTransactionLog.getNewBalance()).isEqualTo(UPDATED_NEW_BALANCE);
        assertThat(testTPTransactionLog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTPTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = tPTransactionLogRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPTransactionLogMockMvc.perform(put("/api/tp-transaction-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPTransactionLog)))
            .andExpect(status().isBadRequest());

        // Validate the TPTransactionLog in the database
        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPTransactionLog() throws Exception {
        // Initialize the database
        tPTransactionLogService.save(tPTransactionLog);

        int databaseSizeBeforeDelete = tPTransactionLogRepository.findAll().size();

        // Delete the tPTransactionLog
        restTPTransactionLogMockMvc.perform(delete("/api/tp-transaction-logs/{id}", tPTransactionLog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPTransactionLog> tPTransactionLogList = tPTransactionLogRepository.findAll();
        assertThat(tPTransactionLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
