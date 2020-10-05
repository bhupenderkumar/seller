package com.gharharyali.org.web.rest;

import com.gharharyali.org.HaryaliApp;
import com.gharharyali.org.domain.Images;
import com.gharharyali.org.domain.Product;
import com.gharharyali.org.repository.ImagesRepository;
import com.gharharyali.org.repository.search.ImagesSearchRepository;
import com.gharharyali.org.service.ImagesService;
import com.gharharyali.org.service.dto.ImagesDTO;
import com.gharharyali.org.service.mapper.ImagesMapper;
import com.gharharyali.org.service.dto.ImagesCriteria;
import com.gharharyali.org.service.ImagesQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link ImagesResource} REST controller.
 */
@SpringBootTest(classes = HaryaliApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImagesResourceIT {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_THUMB_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMB_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_THUMB_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMB_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ALT = "AAAAAAAAAA";
    private static final String UPDATED_ALT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private ImagesService imagesService;

    /**
     * This repository is mocked in the com.gharharyali.org.repository.search test package.
     *
     * @see com.gharharyali.org.repository.search.ImagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private ImagesSearchRepository mockImagesSearchRepository;

    @Autowired
    private ImagesQueryService imagesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImagesMockMvc;

    private Images images;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Images createEntity(EntityManager em) {
        Images images = new Images()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .thumbImage(DEFAULT_THUMB_IMAGE)
            .thumbImageContentType(DEFAULT_THUMB_IMAGE_CONTENT_TYPE)
            .alt(DEFAULT_ALT)
            .title(DEFAULT_TITLE);
        return images;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Images createUpdatedEntity(EntityManager em) {
        Images images = new Images()
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .thumbImage(UPDATED_THUMB_IMAGE)
            .thumbImageContentType(UPDATED_THUMB_IMAGE_CONTENT_TYPE)
            .alt(UPDATED_ALT)
            .title(UPDATED_TITLE);
        return images;
    }

    @BeforeEach
    public void initTest() {
        images = createEntity(em);
    }

    @Test
    @Transactional
    public void createImages() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();
        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);
        restImagesMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate + 1);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testImages.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testImages.getThumbImage()).isEqualTo(DEFAULT_THUMB_IMAGE);
        assertThat(testImages.getThumbImageContentType()).isEqualTo(DEFAULT_THUMB_IMAGE_CONTENT_TYPE);
        assertThat(testImages.getAlt()).isEqualTo(DEFAULT_ALT);
        assertThat(testImages.getTitle()).isEqualTo(DEFAULT_TITLE);

        // Validate the Images in Elasticsearch
        verify(mockImagesSearchRepository, times(1)).save(testImages);
    }

    @Test
    @Transactional
    public void createImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // Create the Images with an existing ID
        images.setId(1L);
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagesMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Images in Elasticsearch
        verify(mockImagesSearchRepository, times(0)).save(images);
    }


    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList
        restImagesMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].thumbImageContentType").value(hasItem(DEFAULT_THUMB_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMB_IMAGE))))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get the images
        restImagesMockMvc.perform(get("/api/images/{id}", images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(images.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.thumbImageContentType").value(DEFAULT_THUMB_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.thumbImage").value(Base64Utils.encodeToString(DEFAULT_THUMB_IMAGE)))
            .andExpect(jsonPath("$.alt").value(DEFAULT_ALT))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }


    @Test
    @Transactional
    public void getImagesByIdFiltering() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        Long id = images.getId();

        defaultImagesShouldBeFound("id.equals=" + id);
        defaultImagesShouldNotBeFound("id.notEquals=" + id);

        defaultImagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImagesShouldNotBeFound("id.greaterThan=" + id);

        defaultImagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImagesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllImagesByAltIsEqualToSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where alt equals to DEFAULT_ALT
        defaultImagesShouldBeFound("alt.equals=" + DEFAULT_ALT);

        // Get all the imagesList where alt equals to UPDATED_ALT
        defaultImagesShouldNotBeFound("alt.equals=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    public void getAllImagesByAltIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where alt not equals to DEFAULT_ALT
        defaultImagesShouldNotBeFound("alt.notEquals=" + DEFAULT_ALT);

        // Get all the imagesList where alt not equals to UPDATED_ALT
        defaultImagesShouldBeFound("alt.notEquals=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    public void getAllImagesByAltIsInShouldWork() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where alt in DEFAULT_ALT or UPDATED_ALT
        defaultImagesShouldBeFound("alt.in=" + DEFAULT_ALT + "," + UPDATED_ALT);

        // Get all the imagesList where alt equals to UPDATED_ALT
        defaultImagesShouldNotBeFound("alt.in=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    public void getAllImagesByAltIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where alt is not null
        defaultImagesShouldBeFound("alt.specified=true");

        // Get all the imagesList where alt is null
        defaultImagesShouldNotBeFound("alt.specified=false");
    }
                @Test
    @Transactional
    public void getAllImagesByAltContainsSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where alt contains DEFAULT_ALT
        defaultImagesShouldBeFound("alt.contains=" + DEFAULT_ALT);

        // Get all the imagesList where alt contains UPDATED_ALT
        defaultImagesShouldNotBeFound("alt.contains=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    public void getAllImagesByAltNotContainsSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where alt does not contain DEFAULT_ALT
        defaultImagesShouldNotBeFound("alt.doesNotContain=" + DEFAULT_ALT);

        // Get all the imagesList where alt does not contain UPDATED_ALT
        defaultImagesShouldBeFound("alt.doesNotContain=" + UPDATED_ALT);
    }


    @Test
    @Transactional
    public void getAllImagesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where title equals to DEFAULT_TITLE
        defaultImagesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the imagesList where title equals to UPDATED_TITLE
        defaultImagesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllImagesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where title not equals to DEFAULT_TITLE
        defaultImagesShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the imagesList where title not equals to UPDATED_TITLE
        defaultImagesShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllImagesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultImagesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the imagesList where title equals to UPDATED_TITLE
        defaultImagesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllImagesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where title is not null
        defaultImagesShouldBeFound("title.specified=true");

        // Get all the imagesList where title is null
        defaultImagesShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllImagesByTitleContainsSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where title contains DEFAULT_TITLE
        defaultImagesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the imagesList where title contains UPDATED_TITLE
        defaultImagesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllImagesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList where title does not contain DEFAULT_TITLE
        defaultImagesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the imagesList where title does not contain UPDATED_TITLE
        defaultImagesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllImagesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        images.setProduct(product);
        imagesRepository.saveAndFlush(images);
        Long productId = product.getId();

        // Get all the imagesList where product equals to productId
        defaultImagesShouldBeFound("productId.equals=" + productId);

        // Get all the imagesList where product equals to productId + 1
        defaultImagesShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImagesShouldBeFound(String filter) throws Exception {
        restImagesMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].thumbImageContentType").value(hasItem(DEFAULT_THUMB_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMB_IMAGE))))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restImagesMockMvc.perform(get("/api/images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImagesShouldNotBeFound(String filter) throws Exception {
        restImagesMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImagesMockMvc.perform(get("/api/images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingImages() throws Exception {
        // Get the images
        restImagesMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images
        Images updatedImages = imagesRepository.findById(images.getId()).get();
        // Disconnect from session so that the updates on updatedImages are not directly saved in db
        em.detach(updatedImages);
        updatedImages
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .thumbImage(UPDATED_THUMB_IMAGE)
            .thumbImageContentType(UPDATED_THUMB_IMAGE_CONTENT_TYPE)
            .alt(UPDATED_ALT)
            .title(UPDATED_TITLE);
        ImagesDTO imagesDTO = imagesMapper.toDto(updatedImages);

        restImagesMockMvc.perform(put("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testImages.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testImages.getThumbImage()).isEqualTo(UPDATED_THUMB_IMAGE);
        assertThat(testImages.getThumbImageContentType()).isEqualTo(UPDATED_THUMB_IMAGE_CONTENT_TYPE);
        assertThat(testImages.getAlt()).isEqualTo(UPDATED_ALT);
        assertThat(testImages.getTitle()).isEqualTo(UPDATED_TITLE);

        // Validate the Images in Elasticsearch
        verify(mockImagesSearchRepository, times(1)).save(testImages);
    }

    @Test
    @Transactional
    public void updateNonExistingImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagesMockMvc.perform(put("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Images in Elasticsearch
        verify(mockImagesSearchRepository, times(0)).save(images);
    }

    @Test
    @Transactional
    public void deleteImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        int databaseSizeBeforeDelete = imagesRepository.findAll().size();

        // Delete the images
        restImagesMockMvc.perform(delete("/api/images/{id}", images.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Images in Elasticsearch
        verify(mockImagesSearchRepository, times(1)).deleteById(images.getId());
    }

    @Test
    @Transactional
    public void searchImages() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        imagesRepository.saveAndFlush(images);
        when(mockImagesSearchRepository.search(queryStringQuery("id:" + images.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(images), PageRequest.of(0, 1), 1));

        // Search the images
        restImagesMockMvc.perform(get("/api/_search/images?query=id:" + images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].thumbImageContentType").value(hasItem(DEFAULT_THUMB_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMB_IMAGE))))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
}
