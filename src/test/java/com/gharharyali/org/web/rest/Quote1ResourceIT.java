package com.gharharyali.org.web.rest;

import com.gharharyali.org.HaryaliApp;
import com.gharharyali.org.domain.Quote1;
import com.gharharyali.org.repository.Quote1Repository;
import com.gharharyali.org.repository.search.Quote1SearchRepository;
import com.gharharyali.org.service.Quote1Service;
import com.gharharyali.org.service.dto.Quote1DTO;
import com.gharharyali.org.service.mapper.Quote1Mapper;
import com.gharharyali.org.service.dto.Quote1Criteria;
import com.gharharyali.org.service.Quote1QueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.gharharyali.org.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link Quote1Resource} REST controller.
 */
@SpringBootTest(classes = HaryaliApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class Quote1ResourceIT {

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final ZonedDateTime DEFAULT_LAST_TRADE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_TRADE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_TRADE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private Quote1Repository quote1Repository;

    @Autowired
    private Quote1Mapper quote1Mapper;

    @Autowired
    private Quote1Service quote1Service;

    /**
     * This repository is mocked in the com.gharharyali.org.repository.search test package.
     *
     * @see com.gharharyali.org.repository.search.Quote1SearchRepositoryMockConfiguration
     */
    @Autowired
    private Quote1SearchRepository mockQuote1SearchRepository;

    @Autowired
    private Quote1QueryService quote1QueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuote1MockMvc;

    private Quote1 quote1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote1 createEntity(EntityManager em) {
        Quote1 quote1 = new Quote1()
            .symbol(DEFAULT_SYMBOL)
            .price(DEFAULT_PRICE)
            .lastTrade(DEFAULT_LAST_TRADE);
        return quote1;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote1 createUpdatedEntity(EntityManager em) {
        Quote1 quote1 = new Quote1()
            .symbol(UPDATED_SYMBOL)
            .price(UPDATED_PRICE)
            .lastTrade(UPDATED_LAST_TRADE);
        return quote1;
    }

    @BeforeEach
    public void initTest() {
        quote1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuote1() throws Exception {
        int databaseSizeBeforeCreate = quote1Repository.findAll().size();
        // Create the Quote1
        Quote1DTO quote1DTO = quote1Mapper.toDto(quote1);
        restQuote1MockMvc.perform(post("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isCreated());

        // Validate the Quote1 in the database
        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeCreate + 1);
        Quote1 testQuote1 = quote1List.get(quote1List.size() - 1);
        assertThat(testQuote1.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testQuote1.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testQuote1.getLastTrade()).isEqualTo(DEFAULT_LAST_TRADE);

        // Validate the Quote1 in Elasticsearch
        verify(mockQuote1SearchRepository, times(1)).save(testQuote1);
    }

    @Test
    @Transactional
    public void createQuote1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quote1Repository.findAll().size();

        // Create the Quote1 with an existing ID
        quote1.setId(1L);
        Quote1DTO quote1DTO = quote1Mapper.toDto(quote1);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuote1MockMvc.perform(post("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quote1 in the database
        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeCreate);

        // Validate the Quote1 in Elasticsearch
        verify(mockQuote1SearchRepository, times(0)).save(quote1);
    }


    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = quote1Repository.findAll().size();
        // set the field null
        quote1.setSymbol(null);

        // Create the Quote1, which fails.
        Quote1DTO quote1DTO = quote1Mapper.toDto(quote1);


        restQuote1MockMvc.perform(post("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isBadRequest());

        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = quote1Repository.findAll().size();
        // set the field null
        quote1.setPrice(null);

        // Create the Quote1, which fails.
        Quote1DTO quote1DTO = quote1Mapper.toDto(quote1);


        restQuote1MockMvc.perform(post("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isBadRequest());

        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastTradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = quote1Repository.findAll().size();
        // set the field null
        quote1.setLastTrade(null);

        // Create the Quote1, which fails.
        Quote1DTO quote1DTO = quote1Mapper.toDto(quote1);


        restQuote1MockMvc.perform(post("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isBadRequest());

        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuote1s() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List
        restQuote1MockMvc.perform(get("/api/quote-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote1.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastTrade").value(hasItem(sameInstant(DEFAULT_LAST_TRADE))));
    }
    
    @Test
    @Transactional
    public void getQuote1() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get the quote1
        restQuote1MockMvc.perform(get("/api/quote-1-s/{id}", quote1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quote1.getId().intValue()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.lastTrade").value(sameInstant(DEFAULT_LAST_TRADE)));
    }


    @Test
    @Transactional
    public void getQuote1sByIdFiltering() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        Long id = quote1.getId();

        defaultQuote1ShouldBeFound("id.equals=" + id);
        defaultQuote1ShouldNotBeFound("id.notEquals=" + id);

        defaultQuote1ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuote1ShouldNotBeFound("id.greaterThan=" + id);

        defaultQuote1ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuote1ShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuote1sBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where symbol equals to DEFAULT_SYMBOL
        defaultQuote1ShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the quote1List where symbol equals to UPDATED_SYMBOL
        defaultQuote1ShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllQuote1sBySymbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where symbol not equals to DEFAULT_SYMBOL
        defaultQuote1ShouldNotBeFound("symbol.notEquals=" + DEFAULT_SYMBOL);

        // Get all the quote1List where symbol not equals to UPDATED_SYMBOL
        defaultQuote1ShouldBeFound("symbol.notEquals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllQuote1sBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultQuote1ShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the quote1List where symbol equals to UPDATED_SYMBOL
        defaultQuote1ShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllQuote1sBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where symbol is not null
        defaultQuote1ShouldBeFound("symbol.specified=true");

        // Get all the quote1List where symbol is null
        defaultQuote1ShouldNotBeFound("symbol.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuote1sBySymbolContainsSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where symbol contains DEFAULT_SYMBOL
        defaultQuote1ShouldBeFound("symbol.contains=" + DEFAULT_SYMBOL);

        // Get all the quote1List where symbol contains UPDATED_SYMBOL
        defaultQuote1ShouldNotBeFound("symbol.contains=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllQuote1sBySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where symbol does not contain DEFAULT_SYMBOL
        defaultQuote1ShouldNotBeFound("symbol.doesNotContain=" + DEFAULT_SYMBOL);

        // Get all the quote1List where symbol does not contain UPDATED_SYMBOL
        defaultQuote1ShouldBeFound("symbol.doesNotContain=" + UPDATED_SYMBOL);
    }


    @Test
    @Transactional
    public void getAllQuote1sByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price equals to DEFAULT_PRICE
        defaultQuote1ShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the quote1List where price equals to UPDATED_PRICE
        defaultQuote1ShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price not equals to DEFAULT_PRICE
        defaultQuote1ShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the quote1List where price not equals to UPDATED_PRICE
        defaultQuote1ShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultQuote1ShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the quote1List where price equals to UPDATED_PRICE
        defaultQuote1ShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price is not null
        defaultQuote1ShouldBeFound("price.specified=true");

        // Get all the quote1List where price is null
        defaultQuote1ShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price is greater than or equal to DEFAULT_PRICE
        defaultQuote1ShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the quote1List where price is greater than or equal to UPDATED_PRICE
        defaultQuote1ShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price is less than or equal to DEFAULT_PRICE
        defaultQuote1ShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the quote1List where price is less than or equal to SMALLER_PRICE
        defaultQuote1ShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price is less than DEFAULT_PRICE
        defaultQuote1ShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the quote1List where price is less than UPDATED_PRICE
        defaultQuote1ShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where price is greater than DEFAULT_PRICE
        defaultQuote1ShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the quote1List where price is greater than SMALLER_PRICE
        defaultQuote1ShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade equals to DEFAULT_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.equals=" + DEFAULT_LAST_TRADE);

        // Get all the quote1List where lastTrade equals to UPDATED_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.equals=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade not equals to DEFAULT_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.notEquals=" + DEFAULT_LAST_TRADE);

        // Get all the quote1List where lastTrade not equals to UPDATED_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.notEquals=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsInShouldWork() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade in DEFAULT_LAST_TRADE or UPDATED_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.in=" + DEFAULT_LAST_TRADE + "," + UPDATED_LAST_TRADE);

        // Get all the quote1List where lastTrade equals to UPDATED_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.in=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade is not null
        defaultQuote1ShouldBeFound("lastTrade.specified=true");

        // Get all the quote1List where lastTrade is null
        defaultQuote1ShouldNotBeFound("lastTrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade is greater than or equal to DEFAULT_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.greaterThanOrEqual=" + DEFAULT_LAST_TRADE);

        // Get all the quote1List where lastTrade is greater than or equal to UPDATED_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.greaterThanOrEqual=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade is less than or equal to DEFAULT_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.lessThanOrEqual=" + DEFAULT_LAST_TRADE);

        // Get all the quote1List where lastTrade is less than or equal to SMALLER_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.lessThanOrEqual=" + SMALLER_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsLessThanSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade is less than DEFAULT_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.lessThan=" + DEFAULT_LAST_TRADE);

        // Get all the quote1List where lastTrade is less than UPDATED_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.lessThan=" + UPDATED_LAST_TRADE);
    }

    @Test
    @Transactional
    public void getAllQuote1sByLastTradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        // Get all the quote1List where lastTrade is greater than DEFAULT_LAST_TRADE
        defaultQuote1ShouldNotBeFound("lastTrade.greaterThan=" + DEFAULT_LAST_TRADE);

        // Get all the quote1List where lastTrade is greater than SMALLER_LAST_TRADE
        defaultQuote1ShouldBeFound("lastTrade.greaterThan=" + SMALLER_LAST_TRADE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuote1ShouldBeFound(String filter) throws Exception {
        restQuote1MockMvc.perform(get("/api/quote-1-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote1.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastTrade").value(hasItem(sameInstant(DEFAULT_LAST_TRADE))));

        // Check, that the count call also returns 1
        restQuote1MockMvc.perform(get("/api/quote-1-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuote1ShouldNotBeFound(String filter) throws Exception {
        restQuote1MockMvc.perform(get("/api/quote-1-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuote1MockMvc.perform(get("/api/quote-1-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuote1() throws Exception {
        // Get the quote1
        restQuote1MockMvc.perform(get("/api/quote-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote1() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        int databaseSizeBeforeUpdate = quote1Repository.findAll().size();

        // Update the quote1
        Quote1 updatedQuote1 = quote1Repository.findById(quote1.getId()).get();
        // Disconnect from session so that the updates on updatedQuote1 are not directly saved in db
        em.detach(updatedQuote1);
        updatedQuote1
            .symbol(UPDATED_SYMBOL)
            .price(UPDATED_PRICE)
            .lastTrade(UPDATED_LAST_TRADE);
        Quote1DTO quote1DTO = quote1Mapper.toDto(updatedQuote1);

        restQuote1MockMvc.perform(put("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isOk());

        // Validate the Quote1 in the database
        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeUpdate);
        Quote1 testQuote1 = quote1List.get(quote1List.size() - 1);
        assertThat(testQuote1.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testQuote1.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testQuote1.getLastTrade()).isEqualTo(UPDATED_LAST_TRADE);

        // Validate the Quote1 in Elasticsearch
        verify(mockQuote1SearchRepository, times(1)).save(testQuote1);
    }

    @Test
    @Transactional
    public void updateNonExistingQuote1() throws Exception {
        int databaseSizeBeforeUpdate = quote1Repository.findAll().size();

        // Create the Quote1
        Quote1DTO quote1DTO = quote1Mapper.toDto(quote1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuote1MockMvc.perform(put("/api/quote-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quote1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quote1 in the database
        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Quote1 in Elasticsearch
        verify(mockQuote1SearchRepository, times(0)).save(quote1);
    }

    @Test
    @Transactional
    public void deleteQuote1() throws Exception {
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);

        int databaseSizeBeforeDelete = quote1Repository.findAll().size();

        // Delete the quote1
        restQuote1MockMvc.perform(delete("/api/quote-1-s/{id}", quote1.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quote1> quote1List = quote1Repository.findAll();
        assertThat(quote1List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Quote1 in Elasticsearch
        verify(mockQuote1SearchRepository, times(1)).deleteById(quote1.getId());
    }

    @Test
    @Transactional
    public void searchQuote1() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        quote1Repository.saveAndFlush(quote1);
        when(mockQuote1SearchRepository.search(queryStringQuery("id:" + quote1.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(quote1), PageRequest.of(0, 1), 1));

        // Search the quote1
        restQuote1MockMvc.perform(get("/api/_search/quote-1-s?query=id:" + quote1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote1.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastTrade").value(hasItem(sameInstant(DEFAULT_LAST_TRADE))));
    }
}
