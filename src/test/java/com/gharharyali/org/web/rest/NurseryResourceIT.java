package com.gharharyali.org.web.rest;

import com.gharharyali.org.HaryaliApp;
import com.gharharyali.org.domain.Nursery;
import com.gharharyali.org.domain.Product;
import com.gharharyali.org.domain.Transaction;
import com.gharharyali.org.repository.NurseryRepository;
import com.gharharyali.org.repository.search.NurserySearchRepository;
import com.gharharyali.org.service.NurseryService;
import com.gharharyali.org.service.dto.NurseryDTO;
import com.gharharyali.org.service.mapper.NurseryMapper;
import com.gharharyali.org.service.dto.NurseryCriteria;
import com.gharharyali.org.service.NurseryQueryService;

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

import com.gharharyali.org.domain.enumeration.COUNTRY;
import com.gharharyali.org.domain.enumeration.PayMentMode;
import com.gharharyali.org.domain.enumeration.PayMentDuration;
/**
 * Integration tests for the {@link NurseryResource} REST controller.
 */
@SpringBootTest(classes = HaryaliApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class NurseryResourceIT {

    private static final String DEFAULT_NURSERY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NURSERY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NO = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SALUTATION = "AAAAAAAAAA";
    private static final String UPDATED_SALUTATION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NO = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final COUNTRY DEFAULT_COUNTRY = COUNTRY.INDIA;
    private static final COUNTRY UPDATED_COUNTRY = COUNTRY.INDIA;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_ADDHAR_NO = "AAAAAAAAAA";
    private static final String UPDATED_ADDHAR_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAN_CARD_NO = "BBBBBBBBBB";

    private static final PayMentMode DEFAULT_PAY_MENT_MODE = PayMentMode.ONLINE;
    private static final PayMentMode UPDATED_PAY_MENT_MODE = PayMentMode.UPI_ID;

    private static final String DEFAULT_UPI_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPI_ID = "BBBBBBBBBB";

    private static final PayMentDuration DEFAULT_PAY_MENT_DURATION = PayMentDuration.TEN;
    private static final PayMentDuration UPDATED_PAY_MENT_DURATION = PayMentDuration.FIFTEEN;

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_IFSC = "AAAAAAAAAA";
    private static final String UPDATED_BANK_IFSC = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private NurseryRepository nurseryRepository;

    @Autowired
    private NurseryMapper nurseryMapper;

    @Autowired
    private NurseryService nurseryService;

    /**
     * This repository is mocked in the com.gharharyali.org.repository.search test package.
     *
     * @see com.gharharyali.org.repository.search.NurserySearchRepositoryMockConfiguration
     */
    @Autowired
    private NurserySearchRepository mockNurserySearchRepository;

    @Autowired
    private NurseryQueryService nurseryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNurseryMockMvc;

    private Nursery nursery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nursery createEntity(EntityManager em) {
        Nursery nursery = new Nursery()
            .nurseryName(DEFAULT_NURSERY_NAME)
            .houseNo(DEFAULT_HOUSE_NO)
            .salutation(DEFAULT_SALUTATION)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .streetNo(DEFAULT_STREET_NO)
            .districtNo(DEFAULT_DISTRICT_NO)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .addharNo(DEFAULT_ADDHAR_NO)
            .panCardNo(DEFAULT_PAN_CARD_NO)
            .payMentMode(DEFAULT_PAY_MENT_MODE)
            .upiId(DEFAULT_UPI_ID)
            .payMentDuration(DEFAULT_PAY_MENT_DURATION)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .bankIFSC(DEFAULT_BANK_IFSC)
            .bankName(DEFAULT_BANK_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .userName(DEFAULT_USER_NAME);
        return nursery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nursery createUpdatedEntity(EntityManager em) {
        Nursery nursery = new Nursery()
            .nurseryName(UPDATED_NURSERY_NAME)
            .houseNo(UPDATED_HOUSE_NO)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .streetNo(UPDATED_STREET_NO)
            .districtNo(UPDATED_DISTRICT_NO)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .addharNo(UPDATED_ADDHAR_NO)
            .panCardNo(UPDATED_PAN_CARD_NO)
            .payMentMode(UPDATED_PAY_MENT_MODE)
            .upiId(UPDATED_UPI_ID)
            .payMentDuration(UPDATED_PAY_MENT_DURATION)
            .accountNo(UPDATED_ACCOUNT_NO)
            .bankIFSC(UPDATED_BANK_IFSC)
            .bankName(UPDATED_BANK_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .userName(UPDATED_USER_NAME);
        return nursery;
    }

    @BeforeEach
    public void initTest() {
        nursery = createEntity(em);
    }

    @Test
    @Transactional
    public void createNursery() throws Exception {
        int databaseSizeBeforeCreate = nurseryRepository.findAll().size();
        // Create the Nursery
        NurseryDTO nurseryDTO = nurseryMapper.toDto(nursery);
        restNurseryMockMvc.perform(post("/api/nurseries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseryDTO)))
            .andExpect(status().isCreated());

        // Validate the Nursery in the database
        List<Nursery> nurseryList = nurseryRepository.findAll();
        assertThat(nurseryList).hasSize(databaseSizeBeforeCreate + 1);
        Nursery testNursery = nurseryList.get(nurseryList.size() - 1);
        assertThat(testNursery.getNurseryName()).isEqualTo(DEFAULT_NURSERY_NAME);
        assertThat(testNursery.getHouseNo()).isEqualTo(DEFAULT_HOUSE_NO);
        assertThat(testNursery.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testNursery.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testNursery.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testNursery.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testNursery.getStreetNo()).isEqualTo(DEFAULT_STREET_NO);
        assertThat(testNursery.getDistrictNo()).isEqualTo(DEFAULT_DISTRICT_NO);
        assertThat(testNursery.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testNursery.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testNursery.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testNursery.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testNursery.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testNursery.getAddharNo()).isEqualTo(DEFAULT_ADDHAR_NO);
        assertThat(testNursery.getPanCardNo()).isEqualTo(DEFAULT_PAN_CARD_NO);
        assertThat(testNursery.getPayMentMode()).isEqualTo(DEFAULT_PAY_MENT_MODE);
        assertThat(testNursery.getUpiId()).isEqualTo(DEFAULT_UPI_ID);
        assertThat(testNursery.getPayMentDuration()).isEqualTo(DEFAULT_PAY_MENT_DURATION);
        assertThat(testNursery.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testNursery.getBankIFSC()).isEqualTo(DEFAULT_BANK_IFSC);
        assertThat(testNursery.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testNursery.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNursery.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testNursery.getUserName()).isEqualTo(DEFAULT_USER_NAME);

        // Validate the Nursery in Elasticsearch
        verify(mockNurserySearchRepository, times(1)).save(testNursery);
    }

    @Test
    @Transactional
    public void createNurseryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nurseryRepository.findAll().size();

        // Create the Nursery with an existing ID
        nursery.setId(1L);
        NurseryDTO nurseryDTO = nurseryMapper.toDto(nursery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNurseryMockMvc.perform(post("/api/nurseries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nursery in the database
        List<Nursery> nurseryList = nurseryRepository.findAll();
        assertThat(nurseryList).hasSize(databaseSizeBeforeCreate);

        // Validate the Nursery in Elasticsearch
        verify(mockNurserySearchRepository, times(0)).save(nursery);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nurseryRepository.findAll().size();
        // set the field null
        nursery.setFirstName(null);

        // Create the Nursery, which fails.
        NurseryDTO nurseryDTO = nurseryMapper.toDto(nursery);


        restNurseryMockMvc.perform(post("/api/nurseries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseryDTO)))
            .andExpect(status().isBadRequest());

        List<Nursery> nurseryList = nurseryRepository.findAll();
        assertThat(nurseryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNurseries() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList
        restNurseryMockMvc.perform(get("/api/nurseries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nursery.getId().intValue())))
            .andExpect(jsonPath("$.[*].nurseryName").value(hasItem(DEFAULT_NURSERY_NAME)))
            .andExpect(jsonPath("$.[*].houseNo").value(hasItem(DEFAULT_HOUSE_NO)))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].streetNo").value(hasItem(DEFAULT_STREET_NO)))
            .andExpect(jsonPath("$.[*].districtNo").value(hasItem(DEFAULT_DISTRICT_NO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].addharNo").value(hasItem(DEFAULT_ADDHAR_NO)))
            .andExpect(jsonPath("$.[*].panCardNo").value(hasItem(DEFAULT_PAN_CARD_NO)))
            .andExpect(jsonPath("$.[*].payMentMode").value(hasItem(DEFAULT_PAY_MENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].upiId").value(hasItem(DEFAULT_UPI_ID)))
            .andExpect(jsonPath("$.[*].payMentDuration").value(hasItem(DEFAULT_PAY_MENT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankIFSC").value(hasItem(DEFAULT_BANK_IFSC)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));
    }
    
    @Test
    @Transactional
    public void getNursery() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get the nursery
        restNurseryMockMvc.perform(get("/api/nurseries/{id}", nursery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nursery.getId().intValue()))
            .andExpect(jsonPath("$.nurseryName").value(DEFAULT_NURSERY_NAME))
            .andExpect(jsonPath("$.houseNo").value(DEFAULT_HOUSE_NO))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.streetNo").value(DEFAULT_STREET_NO))
            .andExpect(jsonPath("$.districtNo").value(DEFAULT_DISTRICT_NO))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.addharNo").value(DEFAULT_ADDHAR_NO))
            .andExpect(jsonPath("$.panCardNo").value(DEFAULT_PAN_CARD_NO))
            .andExpect(jsonPath("$.payMentMode").value(DEFAULT_PAY_MENT_MODE.toString()))
            .andExpect(jsonPath("$.upiId").value(DEFAULT_UPI_ID))
            .andExpect(jsonPath("$.payMentDuration").value(DEFAULT_PAY_MENT_DURATION.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.bankIFSC").value(DEFAULT_BANK_IFSC))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME));
    }


    @Test
    @Transactional
    public void getNurseriesByIdFiltering() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        Long id = nursery.getId();

        defaultNurseryShouldBeFound("id.equals=" + id);
        defaultNurseryShouldNotBeFound("id.notEquals=" + id);

        defaultNurseryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNurseryShouldNotBeFound("id.greaterThan=" + id);

        defaultNurseryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNurseryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNurseriesByNurseryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where nurseryName equals to DEFAULT_NURSERY_NAME
        defaultNurseryShouldBeFound("nurseryName.equals=" + DEFAULT_NURSERY_NAME);

        // Get all the nurseryList where nurseryName equals to UPDATED_NURSERY_NAME
        defaultNurseryShouldNotBeFound("nurseryName.equals=" + UPDATED_NURSERY_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByNurseryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where nurseryName not equals to DEFAULT_NURSERY_NAME
        defaultNurseryShouldNotBeFound("nurseryName.notEquals=" + DEFAULT_NURSERY_NAME);

        // Get all the nurseryList where nurseryName not equals to UPDATED_NURSERY_NAME
        defaultNurseryShouldBeFound("nurseryName.notEquals=" + UPDATED_NURSERY_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByNurseryNameIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where nurseryName in DEFAULT_NURSERY_NAME or UPDATED_NURSERY_NAME
        defaultNurseryShouldBeFound("nurseryName.in=" + DEFAULT_NURSERY_NAME + "," + UPDATED_NURSERY_NAME);

        // Get all the nurseryList where nurseryName equals to UPDATED_NURSERY_NAME
        defaultNurseryShouldNotBeFound("nurseryName.in=" + UPDATED_NURSERY_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByNurseryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where nurseryName is not null
        defaultNurseryShouldBeFound("nurseryName.specified=true");

        // Get all the nurseryList where nurseryName is null
        defaultNurseryShouldNotBeFound("nurseryName.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByNurseryNameContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where nurseryName contains DEFAULT_NURSERY_NAME
        defaultNurseryShouldBeFound("nurseryName.contains=" + DEFAULT_NURSERY_NAME);

        // Get all the nurseryList where nurseryName contains UPDATED_NURSERY_NAME
        defaultNurseryShouldNotBeFound("nurseryName.contains=" + UPDATED_NURSERY_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByNurseryNameNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where nurseryName does not contain DEFAULT_NURSERY_NAME
        defaultNurseryShouldNotBeFound("nurseryName.doesNotContain=" + DEFAULT_NURSERY_NAME);

        // Get all the nurseryList where nurseryName does not contain UPDATED_NURSERY_NAME
        defaultNurseryShouldBeFound("nurseryName.doesNotContain=" + UPDATED_NURSERY_NAME);
    }


    @Test
    @Transactional
    public void getAllNurseriesByHouseNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where houseNo equals to DEFAULT_HOUSE_NO
        defaultNurseryShouldBeFound("houseNo.equals=" + DEFAULT_HOUSE_NO);

        // Get all the nurseryList where houseNo equals to UPDATED_HOUSE_NO
        defaultNurseryShouldNotBeFound("houseNo.equals=" + UPDATED_HOUSE_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByHouseNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where houseNo not equals to DEFAULT_HOUSE_NO
        defaultNurseryShouldNotBeFound("houseNo.notEquals=" + DEFAULT_HOUSE_NO);

        // Get all the nurseryList where houseNo not equals to UPDATED_HOUSE_NO
        defaultNurseryShouldBeFound("houseNo.notEquals=" + UPDATED_HOUSE_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByHouseNoIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where houseNo in DEFAULT_HOUSE_NO or UPDATED_HOUSE_NO
        defaultNurseryShouldBeFound("houseNo.in=" + DEFAULT_HOUSE_NO + "," + UPDATED_HOUSE_NO);

        // Get all the nurseryList where houseNo equals to UPDATED_HOUSE_NO
        defaultNurseryShouldNotBeFound("houseNo.in=" + UPDATED_HOUSE_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByHouseNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where houseNo is not null
        defaultNurseryShouldBeFound("houseNo.specified=true");

        // Get all the nurseryList where houseNo is null
        defaultNurseryShouldNotBeFound("houseNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByHouseNoContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where houseNo contains DEFAULT_HOUSE_NO
        defaultNurseryShouldBeFound("houseNo.contains=" + DEFAULT_HOUSE_NO);

        // Get all the nurseryList where houseNo contains UPDATED_HOUSE_NO
        defaultNurseryShouldNotBeFound("houseNo.contains=" + UPDATED_HOUSE_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByHouseNoNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where houseNo does not contain DEFAULT_HOUSE_NO
        defaultNurseryShouldNotBeFound("houseNo.doesNotContain=" + DEFAULT_HOUSE_NO);

        // Get all the nurseryList where houseNo does not contain UPDATED_HOUSE_NO
        defaultNurseryShouldBeFound("houseNo.doesNotContain=" + UPDATED_HOUSE_NO);
    }


    @Test
    @Transactional
    public void getAllNurseriesBySalutationIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where salutation equals to DEFAULT_SALUTATION
        defaultNurseryShouldBeFound("salutation.equals=" + DEFAULT_SALUTATION);

        // Get all the nurseryList where salutation equals to UPDATED_SALUTATION
        defaultNurseryShouldNotBeFound("salutation.equals=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesBySalutationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where salutation not equals to DEFAULT_SALUTATION
        defaultNurseryShouldNotBeFound("salutation.notEquals=" + DEFAULT_SALUTATION);

        // Get all the nurseryList where salutation not equals to UPDATED_SALUTATION
        defaultNurseryShouldBeFound("salutation.notEquals=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesBySalutationIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where salutation in DEFAULT_SALUTATION or UPDATED_SALUTATION
        defaultNurseryShouldBeFound("salutation.in=" + DEFAULT_SALUTATION + "," + UPDATED_SALUTATION);

        // Get all the nurseryList where salutation equals to UPDATED_SALUTATION
        defaultNurseryShouldNotBeFound("salutation.in=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesBySalutationIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where salutation is not null
        defaultNurseryShouldBeFound("salutation.specified=true");

        // Get all the nurseryList where salutation is null
        defaultNurseryShouldNotBeFound("salutation.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesBySalutationContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where salutation contains DEFAULT_SALUTATION
        defaultNurseryShouldBeFound("salutation.contains=" + DEFAULT_SALUTATION);

        // Get all the nurseryList where salutation contains UPDATED_SALUTATION
        defaultNurseryShouldNotBeFound("salutation.contains=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesBySalutationNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where salutation does not contain DEFAULT_SALUTATION
        defaultNurseryShouldNotBeFound("salutation.doesNotContain=" + DEFAULT_SALUTATION);

        // Get all the nurseryList where salutation does not contain UPDATED_SALUTATION
        defaultNurseryShouldBeFound("salutation.doesNotContain=" + UPDATED_SALUTATION);
    }


    @Test
    @Transactional
    public void getAllNurseriesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where firstName equals to DEFAULT_FIRST_NAME
        defaultNurseryShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the nurseryList where firstName equals to UPDATED_FIRST_NAME
        defaultNurseryShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where firstName not equals to DEFAULT_FIRST_NAME
        defaultNurseryShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the nurseryList where firstName not equals to UPDATED_FIRST_NAME
        defaultNurseryShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultNurseryShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the nurseryList where firstName equals to UPDATED_FIRST_NAME
        defaultNurseryShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where firstName is not null
        defaultNurseryShouldBeFound("firstName.specified=true");

        // Get all the nurseryList where firstName is null
        defaultNurseryShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where firstName contains DEFAULT_FIRST_NAME
        defaultNurseryShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the nurseryList where firstName contains UPDATED_FIRST_NAME
        defaultNurseryShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where firstName does not contain DEFAULT_FIRST_NAME
        defaultNurseryShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the nurseryList where firstName does not contain UPDATED_FIRST_NAME
        defaultNurseryShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllNurseriesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where lastName equals to DEFAULT_LAST_NAME
        defaultNurseryShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the nurseryList where lastName equals to UPDATED_LAST_NAME
        defaultNurseryShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where lastName not equals to DEFAULT_LAST_NAME
        defaultNurseryShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the nurseryList where lastName not equals to UPDATED_LAST_NAME
        defaultNurseryShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultNurseryShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the nurseryList where lastName equals to UPDATED_LAST_NAME
        defaultNurseryShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where lastName is not null
        defaultNurseryShouldBeFound("lastName.specified=true");

        // Get all the nurseryList where lastName is null
        defaultNurseryShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where lastName contains DEFAULT_LAST_NAME
        defaultNurseryShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the nurseryList where lastName contains UPDATED_LAST_NAME
        defaultNurseryShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where lastName does not contain DEFAULT_LAST_NAME
        defaultNurseryShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the nurseryList where lastName does not contain UPDATED_LAST_NAME
        defaultNurseryShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllNurseriesByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultNurseryShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the nurseryList where middleName equals to UPDATED_MIDDLE_NAME
        defaultNurseryShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultNurseryShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the nurseryList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultNurseryShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultNurseryShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the nurseryList where middleName equals to UPDATED_MIDDLE_NAME
        defaultNurseryShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where middleName is not null
        defaultNurseryShouldBeFound("middleName.specified=true");

        // Get all the nurseryList where middleName is null
        defaultNurseryShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where middleName contains DEFAULT_MIDDLE_NAME
        defaultNurseryShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the nurseryList where middleName contains UPDATED_MIDDLE_NAME
        defaultNurseryShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultNurseryShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the nurseryList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultNurseryShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllNurseriesByStreetNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where streetNo equals to DEFAULT_STREET_NO
        defaultNurseryShouldBeFound("streetNo.equals=" + DEFAULT_STREET_NO);

        // Get all the nurseryList where streetNo equals to UPDATED_STREET_NO
        defaultNurseryShouldNotBeFound("streetNo.equals=" + UPDATED_STREET_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStreetNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where streetNo not equals to DEFAULT_STREET_NO
        defaultNurseryShouldNotBeFound("streetNo.notEquals=" + DEFAULT_STREET_NO);

        // Get all the nurseryList where streetNo not equals to UPDATED_STREET_NO
        defaultNurseryShouldBeFound("streetNo.notEquals=" + UPDATED_STREET_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStreetNoIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where streetNo in DEFAULT_STREET_NO or UPDATED_STREET_NO
        defaultNurseryShouldBeFound("streetNo.in=" + DEFAULT_STREET_NO + "," + UPDATED_STREET_NO);

        // Get all the nurseryList where streetNo equals to UPDATED_STREET_NO
        defaultNurseryShouldNotBeFound("streetNo.in=" + UPDATED_STREET_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStreetNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where streetNo is not null
        defaultNurseryShouldBeFound("streetNo.specified=true");

        // Get all the nurseryList where streetNo is null
        defaultNurseryShouldNotBeFound("streetNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByStreetNoContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where streetNo contains DEFAULT_STREET_NO
        defaultNurseryShouldBeFound("streetNo.contains=" + DEFAULT_STREET_NO);

        // Get all the nurseryList where streetNo contains UPDATED_STREET_NO
        defaultNurseryShouldNotBeFound("streetNo.contains=" + UPDATED_STREET_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStreetNoNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where streetNo does not contain DEFAULT_STREET_NO
        defaultNurseryShouldNotBeFound("streetNo.doesNotContain=" + DEFAULT_STREET_NO);

        // Get all the nurseryList where streetNo does not contain UPDATED_STREET_NO
        defaultNurseryShouldBeFound("streetNo.doesNotContain=" + UPDATED_STREET_NO);
    }


    @Test
    @Transactional
    public void getAllNurseriesByDistrictNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where districtNo equals to DEFAULT_DISTRICT_NO
        defaultNurseryShouldBeFound("districtNo.equals=" + DEFAULT_DISTRICT_NO);

        // Get all the nurseryList where districtNo equals to UPDATED_DISTRICT_NO
        defaultNurseryShouldNotBeFound("districtNo.equals=" + UPDATED_DISTRICT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByDistrictNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where districtNo not equals to DEFAULT_DISTRICT_NO
        defaultNurseryShouldNotBeFound("districtNo.notEquals=" + DEFAULT_DISTRICT_NO);

        // Get all the nurseryList where districtNo not equals to UPDATED_DISTRICT_NO
        defaultNurseryShouldBeFound("districtNo.notEquals=" + UPDATED_DISTRICT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByDistrictNoIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where districtNo in DEFAULT_DISTRICT_NO or UPDATED_DISTRICT_NO
        defaultNurseryShouldBeFound("districtNo.in=" + DEFAULT_DISTRICT_NO + "," + UPDATED_DISTRICT_NO);

        // Get all the nurseryList where districtNo equals to UPDATED_DISTRICT_NO
        defaultNurseryShouldNotBeFound("districtNo.in=" + UPDATED_DISTRICT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByDistrictNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where districtNo is not null
        defaultNurseryShouldBeFound("districtNo.specified=true");

        // Get all the nurseryList where districtNo is null
        defaultNurseryShouldNotBeFound("districtNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByDistrictNoContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where districtNo contains DEFAULT_DISTRICT_NO
        defaultNurseryShouldBeFound("districtNo.contains=" + DEFAULT_DISTRICT_NO);

        // Get all the nurseryList where districtNo contains UPDATED_DISTRICT_NO
        defaultNurseryShouldNotBeFound("districtNo.contains=" + UPDATED_DISTRICT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByDistrictNoNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where districtNo does not contain DEFAULT_DISTRICT_NO
        defaultNurseryShouldNotBeFound("districtNo.doesNotContain=" + DEFAULT_DISTRICT_NO);

        // Get all the nurseryList where districtNo does not contain UPDATED_DISTRICT_NO
        defaultNurseryShouldBeFound("districtNo.doesNotContain=" + UPDATED_DISTRICT_NO);
    }


    @Test
    @Transactional
    public void getAllNurseriesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where city equals to DEFAULT_CITY
        defaultNurseryShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the nurseryList where city equals to UPDATED_CITY
        defaultNurseryShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where city not equals to DEFAULT_CITY
        defaultNurseryShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the nurseryList where city not equals to UPDATED_CITY
        defaultNurseryShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where city in DEFAULT_CITY or UPDATED_CITY
        defaultNurseryShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the nurseryList where city equals to UPDATED_CITY
        defaultNurseryShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where city is not null
        defaultNurseryShouldBeFound("city.specified=true");

        // Get all the nurseryList where city is null
        defaultNurseryShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByCityContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where city contains DEFAULT_CITY
        defaultNurseryShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the nurseryList where city contains UPDATED_CITY
        defaultNurseryShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where city does not contain DEFAULT_CITY
        defaultNurseryShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the nurseryList where city does not contain UPDATED_CITY
        defaultNurseryShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllNurseriesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where state equals to DEFAULT_STATE
        defaultNurseryShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the nurseryList where state equals to UPDATED_STATE
        defaultNurseryShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where state not equals to DEFAULT_STATE
        defaultNurseryShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the nurseryList where state not equals to UPDATED_STATE
        defaultNurseryShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where state in DEFAULT_STATE or UPDATED_STATE
        defaultNurseryShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the nurseryList where state equals to UPDATED_STATE
        defaultNurseryShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where state is not null
        defaultNurseryShouldBeFound("state.specified=true");

        // Get all the nurseryList where state is null
        defaultNurseryShouldNotBeFound("state.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByStateContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where state contains DEFAULT_STATE
        defaultNurseryShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the nurseryList where state contains UPDATED_STATE
        defaultNurseryShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where state does not contain DEFAULT_STATE
        defaultNurseryShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the nurseryList where state does not contain UPDATED_STATE
        defaultNurseryShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }


    @Test
    @Transactional
    public void getAllNurseriesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where country equals to DEFAULT_COUNTRY
        defaultNurseryShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the nurseryList where country equals to UPDATED_COUNTRY
        defaultNurseryShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where country not equals to DEFAULT_COUNTRY
        defaultNurseryShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the nurseryList where country not equals to UPDATED_COUNTRY
        defaultNurseryShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultNurseryShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the nurseryList where country equals to UPDATED_COUNTRY
        defaultNurseryShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where country is not null
        defaultNurseryShouldBeFound("country.specified=true");

        // Get all the nurseryList where country is null
        defaultNurseryShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude equals to DEFAULT_LATITUDE
        defaultNurseryShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the nurseryList where latitude equals to UPDATED_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude not equals to DEFAULT_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the nurseryList where latitude not equals to UPDATED_LATITUDE
        defaultNurseryShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultNurseryShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the nurseryList where latitude equals to UPDATED_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude is not null
        defaultNurseryShouldBeFound("latitude.specified=true");

        // Get all the nurseryList where latitude is null
        defaultNurseryShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultNurseryShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the nurseryList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultNurseryShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the nurseryList where latitude is less than or equal to SMALLER_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude is less than DEFAULT_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the nurseryList where latitude is less than UPDATED_LATITUDE
        defaultNurseryShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where latitude is greater than DEFAULT_LATITUDE
        defaultNurseryShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the nurseryList where latitude is greater than SMALLER_LATITUDE
        defaultNurseryShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude equals to DEFAULT_LONGITUDE
        defaultNurseryShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the nurseryList where longitude equals to UPDATED_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude not equals to DEFAULT_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the nurseryList where longitude not equals to UPDATED_LONGITUDE
        defaultNurseryShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultNurseryShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the nurseryList where longitude equals to UPDATED_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude is not null
        defaultNurseryShouldBeFound("longitude.specified=true");

        // Get all the nurseryList where longitude is null
        defaultNurseryShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultNurseryShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the nurseryList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultNurseryShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the nurseryList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude is less than DEFAULT_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the nurseryList where longitude is less than UPDATED_LONGITUDE
        defaultNurseryShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where longitude is greater than DEFAULT_LONGITUDE
        defaultNurseryShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the nurseryList where longitude is greater than SMALLER_LONGITUDE
        defaultNurseryShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllNurseriesByAddharNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where addharNo equals to DEFAULT_ADDHAR_NO
        defaultNurseryShouldBeFound("addharNo.equals=" + DEFAULT_ADDHAR_NO);

        // Get all the nurseryList where addharNo equals to UPDATED_ADDHAR_NO
        defaultNurseryShouldNotBeFound("addharNo.equals=" + UPDATED_ADDHAR_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAddharNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where addharNo not equals to DEFAULT_ADDHAR_NO
        defaultNurseryShouldNotBeFound("addharNo.notEquals=" + DEFAULT_ADDHAR_NO);

        // Get all the nurseryList where addharNo not equals to UPDATED_ADDHAR_NO
        defaultNurseryShouldBeFound("addharNo.notEquals=" + UPDATED_ADDHAR_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAddharNoIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where addharNo in DEFAULT_ADDHAR_NO or UPDATED_ADDHAR_NO
        defaultNurseryShouldBeFound("addharNo.in=" + DEFAULT_ADDHAR_NO + "," + UPDATED_ADDHAR_NO);

        // Get all the nurseryList where addharNo equals to UPDATED_ADDHAR_NO
        defaultNurseryShouldNotBeFound("addharNo.in=" + UPDATED_ADDHAR_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAddharNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where addharNo is not null
        defaultNurseryShouldBeFound("addharNo.specified=true");

        // Get all the nurseryList where addharNo is null
        defaultNurseryShouldNotBeFound("addharNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByAddharNoContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where addharNo contains DEFAULT_ADDHAR_NO
        defaultNurseryShouldBeFound("addharNo.contains=" + DEFAULT_ADDHAR_NO);

        // Get all the nurseryList where addharNo contains UPDATED_ADDHAR_NO
        defaultNurseryShouldNotBeFound("addharNo.contains=" + UPDATED_ADDHAR_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAddharNoNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where addharNo does not contain DEFAULT_ADDHAR_NO
        defaultNurseryShouldNotBeFound("addharNo.doesNotContain=" + DEFAULT_ADDHAR_NO);

        // Get all the nurseryList where addharNo does not contain UPDATED_ADDHAR_NO
        defaultNurseryShouldBeFound("addharNo.doesNotContain=" + UPDATED_ADDHAR_NO);
    }


    @Test
    @Transactional
    public void getAllNurseriesByPanCardNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where panCardNo equals to DEFAULT_PAN_CARD_NO
        defaultNurseryShouldBeFound("panCardNo.equals=" + DEFAULT_PAN_CARD_NO);

        // Get all the nurseryList where panCardNo equals to UPDATED_PAN_CARD_NO
        defaultNurseryShouldNotBeFound("panCardNo.equals=" + UPDATED_PAN_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPanCardNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where panCardNo not equals to DEFAULT_PAN_CARD_NO
        defaultNurseryShouldNotBeFound("panCardNo.notEquals=" + DEFAULT_PAN_CARD_NO);

        // Get all the nurseryList where panCardNo not equals to UPDATED_PAN_CARD_NO
        defaultNurseryShouldBeFound("panCardNo.notEquals=" + UPDATED_PAN_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPanCardNoIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where panCardNo in DEFAULT_PAN_CARD_NO or UPDATED_PAN_CARD_NO
        defaultNurseryShouldBeFound("panCardNo.in=" + DEFAULT_PAN_CARD_NO + "," + UPDATED_PAN_CARD_NO);

        // Get all the nurseryList where panCardNo equals to UPDATED_PAN_CARD_NO
        defaultNurseryShouldNotBeFound("panCardNo.in=" + UPDATED_PAN_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPanCardNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where panCardNo is not null
        defaultNurseryShouldBeFound("panCardNo.specified=true");

        // Get all the nurseryList where panCardNo is null
        defaultNurseryShouldNotBeFound("panCardNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByPanCardNoContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where panCardNo contains DEFAULT_PAN_CARD_NO
        defaultNurseryShouldBeFound("panCardNo.contains=" + DEFAULT_PAN_CARD_NO);

        // Get all the nurseryList where panCardNo contains UPDATED_PAN_CARD_NO
        defaultNurseryShouldNotBeFound("panCardNo.contains=" + UPDATED_PAN_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPanCardNoNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where panCardNo does not contain DEFAULT_PAN_CARD_NO
        defaultNurseryShouldNotBeFound("panCardNo.doesNotContain=" + DEFAULT_PAN_CARD_NO);

        // Get all the nurseryList where panCardNo does not contain UPDATED_PAN_CARD_NO
        defaultNurseryShouldBeFound("panCardNo.doesNotContain=" + UPDATED_PAN_CARD_NO);
    }


    @Test
    @Transactional
    public void getAllNurseriesByPayMentModeIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentMode equals to DEFAULT_PAY_MENT_MODE
        defaultNurseryShouldBeFound("payMentMode.equals=" + DEFAULT_PAY_MENT_MODE);

        // Get all the nurseryList where payMentMode equals to UPDATED_PAY_MENT_MODE
        defaultNurseryShouldNotBeFound("payMentMode.equals=" + UPDATED_PAY_MENT_MODE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPayMentModeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentMode not equals to DEFAULT_PAY_MENT_MODE
        defaultNurseryShouldNotBeFound("payMentMode.notEquals=" + DEFAULT_PAY_MENT_MODE);

        // Get all the nurseryList where payMentMode not equals to UPDATED_PAY_MENT_MODE
        defaultNurseryShouldBeFound("payMentMode.notEquals=" + UPDATED_PAY_MENT_MODE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPayMentModeIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentMode in DEFAULT_PAY_MENT_MODE or UPDATED_PAY_MENT_MODE
        defaultNurseryShouldBeFound("payMentMode.in=" + DEFAULT_PAY_MENT_MODE + "," + UPDATED_PAY_MENT_MODE);

        // Get all the nurseryList where payMentMode equals to UPDATED_PAY_MENT_MODE
        defaultNurseryShouldNotBeFound("payMentMode.in=" + UPDATED_PAY_MENT_MODE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPayMentModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentMode is not null
        defaultNurseryShouldBeFound("payMentMode.specified=true");

        // Get all the nurseryList where payMentMode is null
        defaultNurseryShouldNotBeFound("payMentMode.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpiIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where upiId equals to DEFAULT_UPI_ID
        defaultNurseryShouldBeFound("upiId.equals=" + DEFAULT_UPI_ID);

        // Get all the nurseryList where upiId equals to UPDATED_UPI_ID
        defaultNurseryShouldNotBeFound("upiId.equals=" + UPDATED_UPI_ID);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpiIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where upiId not equals to DEFAULT_UPI_ID
        defaultNurseryShouldNotBeFound("upiId.notEquals=" + DEFAULT_UPI_ID);

        // Get all the nurseryList where upiId not equals to UPDATED_UPI_ID
        defaultNurseryShouldBeFound("upiId.notEquals=" + UPDATED_UPI_ID);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpiIdIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where upiId in DEFAULT_UPI_ID or UPDATED_UPI_ID
        defaultNurseryShouldBeFound("upiId.in=" + DEFAULT_UPI_ID + "," + UPDATED_UPI_ID);

        // Get all the nurseryList where upiId equals to UPDATED_UPI_ID
        defaultNurseryShouldNotBeFound("upiId.in=" + UPDATED_UPI_ID);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpiIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where upiId is not null
        defaultNurseryShouldBeFound("upiId.specified=true");

        // Get all the nurseryList where upiId is null
        defaultNurseryShouldNotBeFound("upiId.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByUpiIdContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where upiId contains DEFAULT_UPI_ID
        defaultNurseryShouldBeFound("upiId.contains=" + DEFAULT_UPI_ID);

        // Get all the nurseryList where upiId contains UPDATED_UPI_ID
        defaultNurseryShouldNotBeFound("upiId.contains=" + UPDATED_UPI_ID);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpiIdNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where upiId does not contain DEFAULT_UPI_ID
        defaultNurseryShouldNotBeFound("upiId.doesNotContain=" + DEFAULT_UPI_ID);

        // Get all the nurseryList where upiId does not contain UPDATED_UPI_ID
        defaultNurseryShouldBeFound("upiId.doesNotContain=" + UPDATED_UPI_ID);
    }


    @Test
    @Transactional
    public void getAllNurseriesByPayMentDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentDuration equals to DEFAULT_PAY_MENT_DURATION
        defaultNurseryShouldBeFound("payMentDuration.equals=" + DEFAULT_PAY_MENT_DURATION);

        // Get all the nurseryList where payMentDuration equals to UPDATED_PAY_MENT_DURATION
        defaultNurseryShouldNotBeFound("payMentDuration.equals=" + UPDATED_PAY_MENT_DURATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPayMentDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentDuration not equals to DEFAULT_PAY_MENT_DURATION
        defaultNurseryShouldNotBeFound("payMentDuration.notEquals=" + DEFAULT_PAY_MENT_DURATION);

        // Get all the nurseryList where payMentDuration not equals to UPDATED_PAY_MENT_DURATION
        defaultNurseryShouldBeFound("payMentDuration.notEquals=" + UPDATED_PAY_MENT_DURATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPayMentDurationIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentDuration in DEFAULT_PAY_MENT_DURATION or UPDATED_PAY_MENT_DURATION
        defaultNurseryShouldBeFound("payMentDuration.in=" + DEFAULT_PAY_MENT_DURATION + "," + UPDATED_PAY_MENT_DURATION);

        // Get all the nurseryList where payMentDuration equals to UPDATED_PAY_MENT_DURATION
        defaultNurseryShouldNotBeFound("payMentDuration.in=" + UPDATED_PAY_MENT_DURATION);
    }

    @Test
    @Transactional
    public void getAllNurseriesByPayMentDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where payMentDuration is not null
        defaultNurseryShouldBeFound("payMentDuration.specified=true");

        // Get all the nurseryList where payMentDuration is null
        defaultNurseryShouldNotBeFound("payMentDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultNurseryShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the nurseryList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultNurseryShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAccountNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where accountNo not equals to DEFAULT_ACCOUNT_NO
        defaultNurseryShouldNotBeFound("accountNo.notEquals=" + DEFAULT_ACCOUNT_NO);

        // Get all the nurseryList where accountNo not equals to UPDATED_ACCOUNT_NO
        defaultNurseryShouldBeFound("accountNo.notEquals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultNurseryShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the nurseryList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultNurseryShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where accountNo is not null
        defaultNurseryShouldBeFound("accountNo.specified=true");

        // Get all the nurseryList where accountNo is null
        defaultNurseryShouldNotBeFound("accountNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultNurseryShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the nurseryList where accountNo contains UPDATED_ACCOUNT_NO
        defaultNurseryShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllNurseriesByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultNurseryShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the nurseryList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultNurseryShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }


    @Test
    @Transactional
    public void getAllNurseriesByBankIFSCIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankIFSC equals to DEFAULT_BANK_IFSC
        defaultNurseryShouldBeFound("bankIFSC.equals=" + DEFAULT_BANK_IFSC);

        // Get all the nurseryList where bankIFSC equals to UPDATED_BANK_IFSC
        defaultNurseryShouldNotBeFound("bankIFSC.equals=" + UPDATED_BANK_IFSC);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankIFSCIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankIFSC not equals to DEFAULT_BANK_IFSC
        defaultNurseryShouldNotBeFound("bankIFSC.notEquals=" + DEFAULT_BANK_IFSC);

        // Get all the nurseryList where bankIFSC not equals to UPDATED_BANK_IFSC
        defaultNurseryShouldBeFound("bankIFSC.notEquals=" + UPDATED_BANK_IFSC);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankIFSCIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankIFSC in DEFAULT_BANK_IFSC or UPDATED_BANK_IFSC
        defaultNurseryShouldBeFound("bankIFSC.in=" + DEFAULT_BANK_IFSC + "," + UPDATED_BANK_IFSC);

        // Get all the nurseryList where bankIFSC equals to UPDATED_BANK_IFSC
        defaultNurseryShouldNotBeFound("bankIFSC.in=" + UPDATED_BANK_IFSC);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankIFSCIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankIFSC is not null
        defaultNurseryShouldBeFound("bankIFSC.specified=true");

        // Get all the nurseryList where bankIFSC is null
        defaultNurseryShouldNotBeFound("bankIFSC.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByBankIFSCContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankIFSC contains DEFAULT_BANK_IFSC
        defaultNurseryShouldBeFound("bankIFSC.contains=" + DEFAULT_BANK_IFSC);

        // Get all the nurseryList where bankIFSC contains UPDATED_BANK_IFSC
        defaultNurseryShouldNotBeFound("bankIFSC.contains=" + UPDATED_BANK_IFSC);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankIFSCNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankIFSC does not contain DEFAULT_BANK_IFSC
        defaultNurseryShouldNotBeFound("bankIFSC.doesNotContain=" + DEFAULT_BANK_IFSC);

        // Get all the nurseryList where bankIFSC does not contain UPDATED_BANK_IFSC
        defaultNurseryShouldBeFound("bankIFSC.doesNotContain=" + UPDATED_BANK_IFSC);
    }


    @Test
    @Transactional
    public void getAllNurseriesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankName equals to DEFAULT_BANK_NAME
        defaultNurseryShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the nurseryList where bankName equals to UPDATED_BANK_NAME
        defaultNurseryShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankName not equals to DEFAULT_BANK_NAME
        defaultNurseryShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the nurseryList where bankName not equals to UPDATED_BANK_NAME
        defaultNurseryShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultNurseryShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the nurseryList where bankName equals to UPDATED_BANK_NAME
        defaultNurseryShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankName is not null
        defaultNurseryShouldBeFound("bankName.specified=true");

        // Get all the nurseryList where bankName is null
        defaultNurseryShouldNotBeFound("bankName.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankName contains DEFAULT_BANK_NAME
        defaultNurseryShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the nurseryList where bankName contains UPDATED_BANK_NAME
        defaultNurseryShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where bankName does not contain DEFAULT_BANK_NAME
        defaultNurseryShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the nurseryList where bankName does not contain UPDATED_BANK_NAME
        defaultNurseryShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }


    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate equals to DEFAULT_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the nurseryList where createdDate equals to UPDATED_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the nurseryList where createdDate not equals to UPDATED_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the nurseryList where createdDate equals to UPDATED_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate is not null
        defaultNurseryShouldBeFound("createdDate.specified=true");

        // Get all the nurseryList where createdDate is null
        defaultNurseryShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the nurseryList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the nurseryList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate is less than DEFAULT_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the nurseryList where createdDate is less than UPDATED_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultNurseryShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the nurseryList where createdDate is greater than SMALLER_CREATED_DATE
        defaultNurseryShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the nurseryList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the nurseryList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the nurseryList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate is not null
        defaultNurseryShouldBeFound("updatedDate.specified=true");

        // Get all the nurseryList where updatedDate is null
        defaultNurseryShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate is greater than or equal to DEFAULT_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.greaterThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the nurseryList where updatedDate is greater than or equal to UPDATED_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.greaterThanOrEqual=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate is less than or equal to DEFAULT_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.lessThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the nurseryList where updatedDate is less than or equal to SMALLER_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.lessThanOrEqual=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate is less than DEFAULT_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.lessThan=" + DEFAULT_UPDATED_DATE);

        // Get all the nurseryList where updatedDate is less than UPDATED_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.lessThan=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where updatedDate is greater than DEFAULT_UPDATED_DATE
        defaultNurseryShouldNotBeFound("updatedDate.greaterThan=" + DEFAULT_UPDATED_DATE);

        // Get all the nurseryList where updatedDate is greater than SMALLER_UPDATED_DATE
        defaultNurseryShouldBeFound("updatedDate.greaterThan=" + SMALLER_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllNurseriesByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where userName equals to DEFAULT_USER_NAME
        defaultNurseryShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the nurseryList where userName equals to UPDATED_USER_NAME
        defaultNurseryShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where userName not equals to DEFAULT_USER_NAME
        defaultNurseryShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the nurseryList where userName not equals to UPDATED_USER_NAME
        defaultNurseryShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultNurseryShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the nurseryList where userName equals to UPDATED_USER_NAME
        defaultNurseryShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where userName is not null
        defaultNurseryShouldBeFound("userName.specified=true");

        // Get all the nurseryList where userName is null
        defaultNurseryShouldNotBeFound("userName.specified=false");
    }
                @Test
    @Transactional
    public void getAllNurseriesByUserNameContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where userName contains DEFAULT_USER_NAME
        defaultNurseryShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the nurseryList where userName contains UPDATED_USER_NAME
        defaultNurseryShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllNurseriesByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        // Get all the nurseryList where userName does not contain DEFAULT_USER_NAME
        defaultNurseryShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the nurseryList where userName does not contain UPDATED_USER_NAME
        defaultNurseryShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }


    @Test
    @Transactional
    public void getAllNurseriesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        nursery.addProduct(product);
        nurseryRepository.saveAndFlush(nursery);
        Long productId = product.getId();

        // Get all the nurseryList where product equals to productId
        defaultNurseryShouldBeFound("productId.equals=" + productId);

        // Get all the nurseryList where product equals to productId + 1
        defaultNurseryShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllNurseriesByTransactionsIsEqualToSomething() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);
        Transaction transactions = TransactionResourceIT.createEntity(em);
        em.persist(transactions);
        em.flush();
        nursery.addTransactions(transactions);
        nurseryRepository.saveAndFlush(nursery);
        Long transactionsId = transactions.getId();

        // Get all the nurseryList where transactions equals to transactionsId
        defaultNurseryShouldBeFound("transactionsId.equals=" + transactionsId);

        // Get all the nurseryList where transactions equals to transactionsId + 1
        defaultNurseryShouldNotBeFound("transactionsId.equals=" + (transactionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNurseryShouldBeFound(String filter) throws Exception {
        restNurseryMockMvc.perform(get("/api/nurseries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nursery.getId().intValue())))
            .andExpect(jsonPath("$.[*].nurseryName").value(hasItem(DEFAULT_NURSERY_NAME)))
            .andExpect(jsonPath("$.[*].houseNo").value(hasItem(DEFAULT_HOUSE_NO)))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].streetNo").value(hasItem(DEFAULT_STREET_NO)))
            .andExpect(jsonPath("$.[*].districtNo").value(hasItem(DEFAULT_DISTRICT_NO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].addharNo").value(hasItem(DEFAULT_ADDHAR_NO)))
            .andExpect(jsonPath("$.[*].panCardNo").value(hasItem(DEFAULT_PAN_CARD_NO)))
            .andExpect(jsonPath("$.[*].payMentMode").value(hasItem(DEFAULT_PAY_MENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].upiId").value(hasItem(DEFAULT_UPI_ID)))
            .andExpect(jsonPath("$.[*].payMentDuration").value(hasItem(DEFAULT_PAY_MENT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankIFSC").value(hasItem(DEFAULT_BANK_IFSC)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));

        // Check, that the count call also returns 1
        restNurseryMockMvc.perform(get("/api/nurseries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNurseryShouldNotBeFound(String filter) throws Exception {
        restNurseryMockMvc.perform(get("/api/nurseries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNurseryMockMvc.perform(get("/api/nurseries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNursery() throws Exception {
        // Get the nursery
        restNurseryMockMvc.perform(get("/api/nurseries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNursery() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        int databaseSizeBeforeUpdate = nurseryRepository.findAll().size();

        // Update the nursery
        Nursery updatedNursery = nurseryRepository.findById(nursery.getId()).get();
        // Disconnect from session so that the updates on updatedNursery are not directly saved in db
        em.detach(updatedNursery);
        updatedNursery
            .nurseryName(UPDATED_NURSERY_NAME)
            .houseNo(UPDATED_HOUSE_NO)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .streetNo(UPDATED_STREET_NO)
            .districtNo(UPDATED_DISTRICT_NO)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .addharNo(UPDATED_ADDHAR_NO)
            .panCardNo(UPDATED_PAN_CARD_NO)
            .payMentMode(UPDATED_PAY_MENT_MODE)
            .upiId(UPDATED_UPI_ID)
            .payMentDuration(UPDATED_PAY_MENT_DURATION)
            .accountNo(UPDATED_ACCOUNT_NO)
            .bankIFSC(UPDATED_BANK_IFSC)
            .bankName(UPDATED_BANK_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .userName(UPDATED_USER_NAME);
        NurseryDTO nurseryDTO = nurseryMapper.toDto(updatedNursery);

        restNurseryMockMvc.perform(put("/api/nurseries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseryDTO)))
            .andExpect(status().isOk());

        // Validate the Nursery in the database
        List<Nursery> nurseryList = nurseryRepository.findAll();
        assertThat(nurseryList).hasSize(databaseSizeBeforeUpdate);
        Nursery testNursery = nurseryList.get(nurseryList.size() - 1);
        assertThat(testNursery.getNurseryName()).isEqualTo(UPDATED_NURSERY_NAME);
        assertThat(testNursery.getHouseNo()).isEqualTo(UPDATED_HOUSE_NO);
        assertThat(testNursery.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testNursery.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testNursery.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testNursery.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testNursery.getStreetNo()).isEqualTo(UPDATED_STREET_NO);
        assertThat(testNursery.getDistrictNo()).isEqualTo(UPDATED_DISTRICT_NO);
        assertThat(testNursery.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testNursery.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testNursery.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testNursery.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testNursery.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testNursery.getAddharNo()).isEqualTo(UPDATED_ADDHAR_NO);
        assertThat(testNursery.getPanCardNo()).isEqualTo(UPDATED_PAN_CARD_NO);
        assertThat(testNursery.getPayMentMode()).isEqualTo(UPDATED_PAY_MENT_MODE);
        assertThat(testNursery.getUpiId()).isEqualTo(UPDATED_UPI_ID);
        assertThat(testNursery.getPayMentDuration()).isEqualTo(UPDATED_PAY_MENT_DURATION);
        assertThat(testNursery.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testNursery.getBankIFSC()).isEqualTo(UPDATED_BANK_IFSC);
        assertThat(testNursery.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testNursery.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNursery.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testNursery.getUserName()).isEqualTo(UPDATED_USER_NAME);

        // Validate the Nursery in Elasticsearch
        verify(mockNurserySearchRepository, times(1)).save(testNursery);
    }

    @Test
    @Transactional
    public void updateNonExistingNursery() throws Exception {
        int databaseSizeBeforeUpdate = nurseryRepository.findAll().size();

        // Create the Nursery
        NurseryDTO nurseryDTO = nurseryMapper.toDto(nursery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNurseryMockMvc.perform(put("/api/nurseries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nursery in the database
        List<Nursery> nurseryList = nurseryRepository.findAll();
        assertThat(nurseryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Nursery in Elasticsearch
        verify(mockNurserySearchRepository, times(0)).save(nursery);
    }

    @Test
    @Transactional
    public void deleteNursery() throws Exception {
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);

        int databaseSizeBeforeDelete = nurseryRepository.findAll().size();

        // Delete the nursery
        restNurseryMockMvc.perform(delete("/api/nurseries/{id}", nursery.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nursery> nurseryList = nurseryRepository.findAll();
        assertThat(nurseryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Nursery in Elasticsearch
        verify(mockNurserySearchRepository, times(1)).deleteById(nursery.getId());
    }

    @Test
    @Transactional
    public void searchNursery() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        nurseryRepository.saveAndFlush(nursery);
        when(mockNurserySearchRepository.search(queryStringQuery("id:" + nursery.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(nursery), PageRequest.of(0, 1), 1));

        // Search the nursery
        restNurseryMockMvc.perform(get("/api/_search/nurseries?query=id:" + nursery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nursery.getId().intValue())))
            .andExpect(jsonPath("$.[*].nurseryName").value(hasItem(DEFAULT_NURSERY_NAME)))
            .andExpect(jsonPath("$.[*].houseNo").value(hasItem(DEFAULT_HOUSE_NO)))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].streetNo").value(hasItem(DEFAULT_STREET_NO)))
            .andExpect(jsonPath("$.[*].districtNo").value(hasItem(DEFAULT_DISTRICT_NO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].addharNo").value(hasItem(DEFAULT_ADDHAR_NO)))
            .andExpect(jsonPath("$.[*].panCardNo").value(hasItem(DEFAULT_PAN_CARD_NO)))
            .andExpect(jsonPath("$.[*].payMentMode").value(hasItem(DEFAULT_PAY_MENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].upiId").value(hasItem(DEFAULT_UPI_ID)))
            .andExpect(jsonPath("$.[*].payMentDuration").value(hasItem(DEFAULT_PAY_MENT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankIFSC").value(hasItem(DEFAULT_BANK_IFSC)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));
    }
}
