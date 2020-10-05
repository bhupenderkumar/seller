package com.gharharyali.org.web.rest;

import com.gharharyali.org.HaryaliApp;
import com.gharharyali.org.domain.FeedBack;
import com.gharharyali.org.domain.Product;
import com.gharharyali.org.repository.FeedBackRepository;
import com.gharharyali.org.repository.search.FeedBackSearchRepository;
import com.gharharyali.org.service.FeedBackService;
import com.gharharyali.org.service.dto.FeedBackDTO;
import com.gharharyali.org.service.mapper.FeedBackMapper;
import com.gharharyali.org.service.dto.FeedBackCriteria;
import com.gharharyali.org.service.FeedBackQueryService;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FeedBackResource} REST controller.
 */
@SpringBootTest(classes = HaryaliApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedBackResourceIT {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;
    private static final Integer SMALLER_RATING = 1 - 1;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_USER_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Autowired
    private FeedBackService feedBackService;

    /**
     * This repository is mocked in the com.gharharyali.org.repository.search test package.
     *
     * @see com.gharharyali.org.repository.search.FeedBackSearchRepositoryMockConfiguration
     */
    @Autowired
    private FeedBackSearchRepository mockFeedBackSearchRepository;

    @Autowired
    private FeedBackQueryService feedBackQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedBackMockMvc;

    private FeedBack feedBack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBack createEntity(EntityManager em) {
        FeedBack feedBack = new FeedBack()
            .rating(DEFAULT_RATING)
            .userName(DEFAULT_USER_NAME)
            .userComments(DEFAULT_USER_COMMENTS);
        return feedBack;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBack createUpdatedEntity(EntityManager em) {
        FeedBack feedBack = new FeedBack()
            .rating(UPDATED_RATING)
            .userName(UPDATED_USER_NAME)
            .userComments(UPDATED_USER_COMMENTS);
        return feedBack;
    }

    @BeforeEach
    public void initTest() {
        feedBack = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedBack() throws Exception {
        int databaseSizeBeforeCreate = feedBackRepository.findAll().size();
        // Create the FeedBack
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);
        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isCreated());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeCreate + 1);
        FeedBack testFeedBack = feedBackList.get(feedBackList.size() - 1);
        assertThat(testFeedBack.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testFeedBack.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testFeedBack.getUserComments()).isEqualTo(DEFAULT_USER_COMMENTS);

        // Validate the FeedBack in Elasticsearch
        verify(mockFeedBackSearchRepository, times(1)).save(testFeedBack);
    }

    @Test
    @Transactional
    public void createFeedBackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedBackRepository.findAll().size();

        // Create the FeedBack with an existing ID
        feedBack.setId(1L);
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeCreate);

        // Validate the FeedBack in Elasticsearch
        verify(mockFeedBackSearchRepository, times(0)).save(feedBack);
    }


    @Test
    @Transactional
    public void checkUserCommentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedBackRepository.findAll().size();
        // set the field null
        feedBack.setUserComments(null);

        // Create the FeedBack, which fails.
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);


        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isBadRequest());

        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedBacks() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList
        restFeedBackMockMvc.perform(get("/api/feed-backs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userComments").value(hasItem(DEFAULT_USER_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get the feedBack
        restFeedBackMockMvc.perform(get("/api/feed-backs/{id}", feedBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedBack.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userComments").value(DEFAULT_USER_COMMENTS));
    }


    @Test
    @Transactional
    public void getFeedBacksByIdFiltering() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        Long id = feedBack.getId();

        defaultFeedBackShouldBeFound("id.equals=" + id);
        defaultFeedBackShouldNotBeFound("id.notEquals=" + id);

        defaultFeedBackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedBackShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedBackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedBackShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating equals to DEFAULT_RATING
        defaultFeedBackShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the feedBackList where rating equals to UPDATED_RATING
        defaultFeedBackShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating not equals to DEFAULT_RATING
        defaultFeedBackShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the feedBackList where rating not equals to UPDATED_RATING
        defaultFeedBackShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultFeedBackShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the feedBackList where rating equals to UPDATED_RATING
        defaultFeedBackShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating is not null
        defaultFeedBackShouldBeFound("rating.specified=true");

        // Get all the feedBackList where rating is null
        defaultFeedBackShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating is greater than or equal to DEFAULT_RATING
        defaultFeedBackShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the feedBackList where rating is greater than or equal to UPDATED_RATING
        defaultFeedBackShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating is less than or equal to DEFAULT_RATING
        defaultFeedBackShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the feedBackList where rating is less than or equal to SMALLER_RATING
        defaultFeedBackShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating is less than DEFAULT_RATING
        defaultFeedBackShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the feedBackList where rating is less than UPDATED_RATING
        defaultFeedBackShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where rating is greater than DEFAULT_RATING
        defaultFeedBackShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the feedBackList where rating is greater than SMALLER_RATING
        defaultFeedBackShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }


    @Test
    @Transactional
    public void getAllFeedBacksByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userName equals to DEFAULT_USER_NAME
        defaultFeedBackShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the feedBackList where userName equals to UPDATED_USER_NAME
        defaultFeedBackShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userName not equals to DEFAULT_USER_NAME
        defaultFeedBackShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the feedBackList where userName not equals to UPDATED_USER_NAME
        defaultFeedBackShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultFeedBackShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the feedBackList where userName equals to UPDATED_USER_NAME
        defaultFeedBackShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userName is not null
        defaultFeedBackShouldBeFound("userName.specified=true");

        // Get all the feedBackList where userName is null
        defaultFeedBackShouldNotBeFound("userName.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedBacksByUserNameContainsSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userName contains DEFAULT_USER_NAME
        defaultFeedBackShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the feedBackList where userName contains UPDATED_USER_NAME
        defaultFeedBackShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userName does not contain DEFAULT_USER_NAME
        defaultFeedBackShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the feedBackList where userName does not contain UPDATED_USER_NAME
        defaultFeedBackShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }


    @Test
    @Transactional
    public void getAllFeedBacksByUserCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userComments equals to DEFAULT_USER_COMMENTS
        defaultFeedBackShouldBeFound("userComments.equals=" + DEFAULT_USER_COMMENTS);

        // Get all the feedBackList where userComments equals to UPDATED_USER_COMMENTS
        defaultFeedBackShouldNotBeFound("userComments.equals=" + UPDATED_USER_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userComments not equals to DEFAULT_USER_COMMENTS
        defaultFeedBackShouldNotBeFound("userComments.notEquals=" + DEFAULT_USER_COMMENTS);

        // Get all the feedBackList where userComments not equals to UPDATED_USER_COMMENTS
        defaultFeedBackShouldBeFound("userComments.notEquals=" + UPDATED_USER_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userComments in DEFAULT_USER_COMMENTS or UPDATED_USER_COMMENTS
        defaultFeedBackShouldBeFound("userComments.in=" + DEFAULT_USER_COMMENTS + "," + UPDATED_USER_COMMENTS);

        // Get all the feedBackList where userComments equals to UPDATED_USER_COMMENTS
        defaultFeedBackShouldNotBeFound("userComments.in=" + UPDATED_USER_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userComments is not null
        defaultFeedBackShouldBeFound("userComments.specified=true");

        // Get all the feedBackList where userComments is null
        defaultFeedBackShouldNotBeFound("userComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedBacksByUserCommentsContainsSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userComments contains DEFAULT_USER_COMMENTS
        defaultFeedBackShouldBeFound("userComments.contains=" + DEFAULT_USER_COMMENTS);

        // Get all the feedBackList where userComments contains UPDATED_USER_COMMENTS
        defaultFeedBackShouldNotBeFound("userComments.contains=" + UPDATED_USER_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllFeedBacksByUserCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList where userComments does not contain DEFAULT_USER_COMMENTS
        defaultFeedBackShouldNotBeFound("userComments.doesNotContain=" + DEFAULT_USER_COMMENTS);

        // Get all the feedBackList where userComments does not contain UPDATED_USER_COMMENTS
        defaultFeedBackShouldBeFound("userComments.doesNotContain=" + UPDATED_USER_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllFeedBacksByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        feedBack.setProduct(product);
        feedBackRepository.saveAndFlush(feedBack);
        Long productId = product.getId();

        // Get all the feedBackList where product equals to productId
        defaultFeedBackShouldBeFound("productId.equals=" + productId);

        // Get all the feedBackList where product equals to productId + 1
        defaultFeedBackShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedBackShouldBeFound(String filter) throws Exception {
        restFeedBackMockMvc.perform(get("/api/feed-backs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userComments").value(hasItem(DEFAULT_USER_COMMENTS)));

        // Check, that the count call also returns 1
        restFeedBackMockMvc.perform(get("/api/feed-backs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedBackShouldNotBeFound(String filter) throws Exception {
        restFeedBackMockMvc.perform(get("/api/feed-backs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedBackMockMvc.perform(get("/api/feed-backs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFeedBack() throws Exception {
        // Get the feedBack
        restFeedBackMockMvc.perform(get("/api/feed-backs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        int databaseSizeBeforeUpdate = feedBackRepository.findAll().size();

        // Update the feedBack
        FeedBack updatedFeedBack = feedBackRepository.findById(feedBack.getId()).get();
        // Disconnect from session so that the updates on updatedFeedBack are not directly saved in db
        em.detach(updatedFeedBack);
        updatedFeedBack
            .rating(UPDATED_RATING)
            .userName(UPDATED_USER_NAME)
            .userComments(UPDATED_USER_COMMENTS);
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(updatedFeedBack);

        restFeedBackMockMvc.perform(put("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isOk());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeUpdate);
        FeedBack testFeedBack = feedBackList.get(feedBackList.size() - 1);
        assertThat(testFeedBack.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFeedBack.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testFeedBack.getUserComments()).isEqualTo(UPDATED_USER_COMMENTS);

        // Validate the FeedBack in Elasticsearch
        verify(mockFeedBackSearchRepository, times(1)).save(testFeedBack);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedBack() throws Exception {
        int databaseSizeBeforeUpdate = feedBackRepository.findAll().size();

        // Create the FeedBack
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackMockMvc.perform(put("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FeedBack in Elasticsearch
        verify(mockFeedBackSearchRepository, times(0)).save(feedBack);
    }

    @Test
    @Transactional
    public void deleteFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        int databaseSizeBeforeDelete = feedBackRepository.findAll().size();

        // Delete the feedBack
        restFeedBackMockMvc.perform(delete("/api/feed-backs/{id}", feedBack.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FeedBack in Elasticsearch
        verify(mockFeedBackSearchRepository, times(1)).deleteById(feedBack.getId());
    }

    @Test
    @Transactional
    public void searchFeedBack() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);
        when(mockFeedBackSearchRepository.search(queryStringQuery("id:" + feedBack.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(feedBack), PageRequest.of(0, 1), 1));

        // Search the feedBack
        restFeedBackMockMvc.perform(get("/api/_search/feed-backs?query=id:" + feedBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userComments").value(hasItem(DEFAULT_USER_COMMENTS)));
    }
}
