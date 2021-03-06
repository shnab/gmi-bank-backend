package com.gmibank.app.web.rest;

import com.gmibank.app.GmiBankBackendApp;
import com.gmibank.app.domain.TPAccount;
import com.gmibank.app.repository.TPAccountRepository;
import com.gmibank.app.service.TPAccountService;

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

import com.gmibank.app.domain.enumeration.TPAccountType;
import com.gmibank.app.domain.enumeration.TPAccountStatusType;
/**
 * Integration tests for the {@link TPAccountResource} REST controller.
 */
@SpringBootTest(classes = GmiBankBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TPAccountResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_BALANCE = 1;
    private static final Integer UPDATED_BALANCE = 2;

    private static final TPAccountType DEFAULT_ACCOUNT_TYPE = TPAccountType.CHECKING;
    private static final TPAccountType UPDATED_ACCOUNT_TYPE = TPAccountType.SAVING;

    private static final TPAccountStatusType DEFAULT_ACCOUNT_STATUS_TYPE = TPAccountStatusType.ACTIVE;
    private static final TPAccountStatusType UPDATED_ACCOUNT_STATUS_TYPE = TPAccountStatusType.SUESPENDED;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TPAccountRepository tPAccountRepository;

    @Autowired
    private TPAccountService tPAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPAccountMockMvc;

    private TPAccount tPAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPAccount createEntity(EntityManager em) {
        TPAccount tPAccount = new TPAccount()
            .description(DEFAULT_DESCRIPTION)
            .balance(DEFAULT_BALANCE)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountStatusType(DEFAULT_ACCOUNT_STATUS_TYPE)
            .createDate(DEFAULT_CREATE_DATE)
            .closedDate(DEFAULT_CLOSED_DATE);
        return tPAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPAccount createUpdatedEntity(EntityManager em) {
        TPAccount tPAccount = new TPAccount()
            .description(UPDATED_DESCRIPTION)
            .balance(UPDATED_BALANCE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountStatusType(UPDATED_ACCOUNT_STATUS_TYPE)
            .createDate(UPDATED_CREATE_DATE)
            .closedDate(UPDATED_CLOSED_DATE);
        return tPAccount;
    }

    @BeforeEach
    public void initTest() {
        tPAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPAccount() throws Exception {
        int databaseSizeBeforeCreate = tPAccountRepository.findAll().size();
        // Create the TPAccount
        restTPAccountMockMvc.perform(post("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isCreated());

        // Validate the TPAccount in the database
        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeCreate + 1);
        TPAccount testTPAccount = tPAccountList.get(tPAccountList.size() - 1);
        assertThat(testTPAccount.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTPAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testTPAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testTPAccount.getAccountStatusType()).isEqualTo(DEFAULT_ACCOUNT_STATUS_TYPE);
        assertThat(testTPAccount.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTPAccount.getClosedDate()).isEqualTo(DEFAULT_CLOSED_DATE);
    }

    @Test
    @Transactional
    public void createTPAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPAccountRepository.findAll().size();

        // Create the TPAccount with an existing ID
        tPAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPAccountMockMvc.perform(post("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isBadRequest());

        // Validate the TPAccount in the database
        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRepository.findAll().size();
        // set the field null
        tPAccount.setDescription(null);

        // Create the TPAccount, which fails.


        restTPAccountMockMvc.perform(post("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isBadRequest());

        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRepository.findAll().size();
        // set the field null
        tPAccount.setBalance(null);

        // Create the TPAccount, which fails.


        restTPAccountMockMvc.perform(post("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isBadRequest());

        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRepository.findAll().size();
        // set the field null
        tPAccount.setAccountType(null);

        // Create the TPAccount, which fails.


        restTPAccountMockMvc.perform(post("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isBadRequest());

        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountStatusTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tPAccountRepository.findAll().size();
        // set the field null
        tPAccount.setAccountStatusType(null);

        // Create the TPAccount, which fails.


        restTPAccountMockMvc.perform(post("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isBadRequest());

        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTPAccounts() throws Exception {
        // Initialize the database
        tPAccountRepository.saveAndFlush(tPAccount);

        // Get all the tPAccountList
        restTPAccountMockMvc.perform(get("/api/tp-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountStatusType").value(hasItem(DEFAULT_ACCOUNT_STATUS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].closedDate").value(hasItem(DEFAULT_CLOSED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTPAccount() throws Exception {
        // Initialize the database
        tPAccountRepository.saveAndFlush(tPAccount);

        // Get the tPAccount
        restTPAccountMockMvc.perform(get("/api/tp-accounts/{id}", tPAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPAccount.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.accountStatusType").value(DEFAULT_ACCOUNT_STATUS_TYPE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.closedDate").value(DEFAULT_CLOSED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTPAccount() throws Exception {
        // Get the tPAccount
        restTPAccountMockMvc.perform(get("/api/tp-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPAccount() throws Exception {
        // Initialize the database
        tPAccountService.save(tPAccount);

        int databaseSizeBeforeUpdate = tPAccountRepository.findAll().size();

        // Update the tPAccount
        TPAccount updatedTPAccount = tPAccountRepository.findById(tPAccount.getId()).get();
        // Disconnect from session so that the updates on updatedTPAccount are not directly saved in db
        em.detach(updatedTPAccount);
        updatedTPAccount
            .description(UPDATED_DESCRIPTION)
            .balance(UPDATED_BALANCE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountStatusType(UPDATED_ACCOUNT_STATUS_TYPE)
            .createDate(UPDATED_CREATE_DATE)
            .closedDate(UPDATED_CLOSED_DATE);

        restTPAccountMockMvc.perform(put("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPAccount)))
            .andExpect(status().isOk());

        // Validate the TPAccount in the database
        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeUpdate);
        TPAccount testTPAccount = tPAccountList.get(tPAccountList.size() - 1);
        assertThat(testTPAccount.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTPAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testTPAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testTPAccount.getAccountStatusType()).isEqualTo(UPDATED_ACCOUNT_STATUS_TYPE);
        assertThat(testTPAccount.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTPAccount.getClosedDate()).isEqualTo(UPDATED_CLOSED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTPAccount() throws Exception {
        int databaseSizeBeforeUpdate = tPAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPAccountMockMvc.perform(put("/api/tp-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tPAccount)))
            .andExpect(status().isBadRequest());

        // Validate the TPAccount in the database
        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPAccount() throws Exception {
        // Initialize the database
        tPAccountService.save(tPAccount);

        int databaseSizeBeforeDelete = tPAccountRepository.findAll().size();

        // Delete the tPAccount
        restTPAccountMockMvc.perform(delete("/api/tp-accounts/{id}", tPAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TPAccount> tPAccountList = tPAccountRepository.findAll();
        assertThat(tPAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
