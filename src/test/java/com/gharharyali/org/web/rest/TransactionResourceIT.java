package com.gharharyali.org.web.rest;

import com.gharharyali.org.HaryaliApp;
import com.gharharyali.org.domain.Transaction;
import com.gharharyali.org.domain.Nursery;
import com.gharharyali.org.repository.TransactionRepository;
import com.gharharyali.org.repository.search.TransactionSearchRepository;
import com.gharharyali.org.service.TransactionService;
import com.gharharyali.org.service.dto.TransactionDTO;
import com.gharharyali.org.service.mapper.TransactionMapper;
import com.gharharyali.org.service.dto.TransactionCriteria;
import com.gharharyali.org.service.TransactionQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gharharyali.org.domain.enumeration.PayMentMode;
/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = HaryaliApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_TRANSACTION_AMOUNT = 1D;
    private static final Double UPDATED_TRANSACTION_AMOUNT = 2D;
    private static final Double SMALLER_TRANSACTION_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TRANSACTION_DATE = LocalDate.ofEpochDay(-1L);

    private static final PayMentMode DEFAULT_TRANSACTION_METHOD = PayMentMode.ONLINE;
    private static final PayMentMode UPDATED_TRANSACTION_METHOD = PayMentMode.UPI_ID;

    private static final String DEFAULT_PROCESSED_BY = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSED_BY = "BBBBBBBBBB";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;

    /**
     * This repository is mocked in the com.gharharyali.org.repository.search test package.
     *
     * @see com.gharharyali.org.repository.search.TransactionSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionSearchRepository mockTransactionSearchRepository;

    @Autowired
    private TransactionQueryService transactionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .userName(DEFAULT_USER_NAME)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .transactionMethod(DEFAULT_TRANSACTION_METHOD)
            .processedBy(DEFAULT_PROCESSED_BY);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .userName(UPDATED_USER_NAME)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionMethod(UPDATED_TRANSACTION_METHOD)
            .processedBy(UPDATED_PROCESSED_BY);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testTransaction.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testTransaction.getTransactionMethod()).isEqualTo(DEFAULT_TRANSACTION_METHOD);
        assertThat(testTransaction.getProcessedBy()).isEqualTo(DEFAULT_PROCESSED_BY);

        // Validate the Transaction in Elasticsearch
        verify(mockTransactionSearchRepository, times(1)).save(testTransaction);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Transaction in Elasticsearch
        verify(mockTransactionSearchRepository, times(0)).save(transaction);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionMethod").value(hasItem(DEFAULT_TRANSACTION_METHOD.toString())))
            .andExpect(jsonPath("$.[*].processedBy").value(hasItem(DEFAULT_PROCESSED_BY)));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.transactionMethod").value(DEFAULT_TRANSACTION_METHOD.toString()))
            .andExpect(jsonPath("$.processedBy").value(DEFAULT_PROCESSED_BY));
    }


    @Test
    @Transactional
    public void getTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        Long id = transaction.getId();

        defaultTransactionShouldBeFound("id.equals=" + id);
        defaultTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where userName equals to DEFAULT_USER_NAME
        defaultTransactionShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the transactionList where userName equals to UPDATED_USER_NAME
        defaultTransactionShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where userName not equals to DEFAULT_USER_NAME
        defaultTransactionShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the transactionList where userName not equals to UPDATED_USER_NAME
        defaultTransactionShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultTransactionShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the transactionList where userName equals to UPDATED_USER_NAME
        defaultTransactionShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where userName is not null
        defaultTransactionShouldBeFound("userName.specified=true");

        // Get all the transactionList where userName is null
        defaultTransactionShouldNotBeFound("userName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByUserNameContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where userName contains DEFAULT_USER_NAME
        defaultTransactionShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the transactionList where userName contains UPDATED_USER_NAME
        defaultTransactionShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionsByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where userName does not contain DEFAULT_USER_NAME
        defaultTransactionShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the transactionList where userName does not contain UPDATED_USER_NAME
        defaultTransactionShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount not equals to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.notEquals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount not equals to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.notEquals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount is not null
        defaultTransactionShouldBeFound("transactionAmount.specified=true");

        // Get all the transactionList where transactionAmount is null
        defaultTransactionShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount is greater than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.greaterThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount is greater than or equal to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.greaterThanOrEqual=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount is less than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.lessThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount is less than or equal to SMALLER_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.lessThanOrEqual=" + SMALLER_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount is less than DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.lessThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount is less than UPDATED_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.lessThan=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionAmount is greater than DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionShouldNotBeFound("transactionAmount.greaterThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionList where transactionAmount is greater than SMALLER_TRANSACTION_AMOUNT
        defaultTransactionShouldBeFound("transactionAmount.greaterThan=" + SMALLER_TRANSACTION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate not equals to DEFAULT_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.notEquals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate not equals to UPDATED_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.notEquals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate is not null
        defaultTransactionShouldBeFound("transactionDate.specified=true");

        // Get all the transactionList where transactionDate is null
        defaultTransactionShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate is greater than or equal to DEFAULT_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.greaterThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate is greater than or equal to UPDATED_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.greaterThanOrEqual=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate is less than or equal to DEFAULT_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.lessThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate is less than or equal to SMALLER_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.lessThanOrEqual=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate is less than DEFAULT_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate is less than UPDATED_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionDate is greater than DEFAULT_TRANSACTION_DATE
        defaultTransactionShouldNotBeFound("transactionDate.greaterThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionList where transactionDate is greater than SMALLER_TRANSACTION_DATE
        defaultTransactionShouldBeFound("transactionDate.greaterThan=" + SMALLER_TRANSACTION_DATE);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTransactionMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionMethod equals to DEFAULT_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transactionMethod.equals=" + DEFAULT_TRANSACTION_METHOD);

        // Get all the transactionList where transactionMethod equals to UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transactionMethod.equals=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionMethod not equals to DEFAULT_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transactionMethod.notEquals=" + DEFAULT_TRANSACTION_METHOD);

        // Get all the transactionList where transactionMethod not equals to UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transactionMethod.notEquals=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionMethodIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionMethod in DEFAULT_TRANSACTION_METHOD or UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transactionMethod.in=" + DEFAULT_TRANSACTION_METHOD + "," + UPDATED_TRANSACTION_METHOD);

        // Get all the transactionList where transactionMethod equals to UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transactionMethod.in=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionMethod is not null
        defaultTransactionShouldBeFound("transactionMethod.specified=true");

        // Get all the transactionList where transactionMethod is null
        defaultTransactionShouldNotBeFound("transactionMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByProcessedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where processedBy equals to DEFAULT_PROCESSED_BY
        defaultTransactionShouldBeFound("processedBy.equals=" + DEFAULT_PROCESSED_BY);

        // Get all the transactionList where processedBy equals to UPDATED_PROCESSED_BY
        defaultTransactionShouldNotBeFound("processedBy.equals=" + UPDATED_PROCESSED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionsByProcessedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where processedBy not equals to DEFAULT_PROCESSED_BY
        defaultTransactionShouldNotBeFound("processedBy.notEquals=" + DEFAULT_PROCESSED_BY);

        // Get all the transactionList where processedBy not equals to UPDATED_PROCESSED_BY
        defaultTransactionShouldBeFound("processedBy.notEquals=" + UPDATED_PROCESSED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionsByProcessedByIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where processedBy in DEFAULT_PROCESSED_BY or UPDATED_PROCESSED_BY
        defaultTransactionShouldBeFound("processedBy.in=" + DEFAULT_PROCESSED_BY + "," + UPDATED_PROCESSED_BY);

        // Get all the transactionList where processedBy equals to UPDATED_PROCESSED_BY
        defaultTransactionShouldNotBeFound("processedBy.in=" + UPDATED_PROCESSED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionsByProcessedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where processedBy is not null
        defaultTransactionShouldBeFound("processedBy.specified=true");

        // Get all the transactionList where processedBy is null
        defaultTransactionShouldNotBeFound("processedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByProcessedByContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where processedBy contains DEFAULT_PROCESSED_BY
        defaultTransactionShouldBeFound("processedBy.contains=" + DEFAULT_PROCESSED_BY);

        // Get all the transactionList where processedBy contains UPDATED_PROCESSED_BY
        defaultTransactionShouldNotBeFound("processedBy.contains=" + UPDATED_PROCESSED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionsByProcessedByNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where processedBy does not contain DEFAULT_PROCESSED_BY
        defaultTransactionShouldNotBeFound("processedBy.doesNotContain=" + DEFAULT_PROCESSED_BY);

        // Get all the transactionList where processedBy does not contain UPDATED_PROCESSED_BY
        defaultTransactionShouldBeFound("processedBy.doesNotContain=" + UPDATED_PROCESSED_BY);
    }


    @Test
    @Transactional
    public void getAllTransactionsByNurseryIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        Nursery nursery = NurseryResourceIT.createEntity(em);
        em.persist(nursery);
        em.flush();
        transaction.setNursery(nursery);
        transactionRepository.saveAndFlush(transaction);
        Long nurseryId = nursery.getId();

        // Get all the transactionList where nursery equals to nurseryId
        defaultTransactionShouldBeFound("nurseryId.equals=" + nurseryId);

        // Get all the transactionList where nursery equals to nurseryId + 1
        defaultTransactionShouldNotBeFound("nurseryId.equals=" + (nurseryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionShouldBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionMethod").value(hasItem(DEFAULT_TRANSACTION_METHOD.toString())))
            .andExpect(jsonPath("$.[*].processedBy").value(hasItem(DEFAULT_PROCESSED_BY)));

        // Check, that the count call also returns 1
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionShouldNotBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .userName(UPDATED_USER_NAME)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionMethod(UPDATED_TRANSACTION_METHOD)
            .processedBy(UPDATED_PROCESSED_BY);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTransaction.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testTransaction.getTransactionMethod()).isEqualTo(UPDATED_TRANSACTION_METHOD);
        assertThat(testTransaction.getProcessedBy()).isEqualTo(UPDATED_PROCESSED_BY);

        // Validate the Transaction in Elasticsearch
        verify(mockTransactionSearchRepository, times(1)).save(testTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Transaction in Elasticsearch
        verify(mockTransactionSearchRepository, times(0)).save(transaction);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Transaction in Elasticsearch
        verify(mockTransactionSearchRepository, times(1)).deleteById(transaction.getId());
    }

    @Test
    @Transactional
    public void searchTransaction() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        when(mockTransactionSearchRepository.search(queryStringQuery("id:" + transaction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(transaction), PageRequest.of(0, 1), 1));

        // Search the transaction
        restTransactionMockMvc.perform(get("/api/_search/transactions?query=id:" + transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionMethod").value(hasItem(DEFAULT_TRANSACTION_METHOD.toString())))
            .andExpect(jsonPath("$.[*].processedBy").value(hasItem(DEFAULT_PROCESSED_BY)));
    }
}
