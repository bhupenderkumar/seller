package com.gharharyali.org.web.rest;

import com.gharharyali.org.HaryaliApp;
import com.gharharyali.org.domain.Product;
import com.gharharyali.org.domain.Images;
import com.gharharyali.org.domain.FeedBack;
import com.gharharyali.org.domain.Nursery;
import com.gharharyali.org.repository.ProductRepository;
import com.gharharyali.org.repository.search.ProductSearchRepository;
import com.gharharyali.org.service.ProductService;
import com.gharharyali.org.service.dto.ProductDTO;
import com.gharharyali.org.service.mapper.ProductMapper;
import com.gharharyali.org.service.dto.ProductCriteria;
import com.gharharyali.org.service.ProductQueryService;

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

import com.gharharyali.org.domain.enumeration.Approval;
import com.gharharyali.org.domain.enumeration.ProductType;
/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = HaryaliApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 10D;
    private static final Double UPDATED_PRICE = 11D;
    private static final Double SMALLER_PRICE = 10D - 1D;

    private static final Approval DEFAULT_APPROVAL = Approval.APPROVED;
    private static final Approval UPDATED_APPROVAL = Approval.IN_PROGRESS;

    private static final Boolean DEFAULT_SHOW_STATUS = false;
    private static final Boolean UPDATED_SHOW_STATUS = true;

    private static final ProductType DEFAULT_PRODUCT_TYPE = ProductType.ARTIFICIAL_PLANT;
    private static final ProductType UPDATED_PRODUCT_TYPE = ProductType.SEED;

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_PRODUCT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PRODUCT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    /**
     * This repository is mocked in the com.gharharyali.org.repository.search test package.
     *
     * @see com.gharharyali.org.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productName(DEFAULT_PRODUCT_NAME)
            .productDescription(DEFAULT_PRODUCT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .approval(DEFAULT_APPROVAL)
            .showStatus(DEFAULT_SHOW_STATUS)
            .productType(DEFAULT_PRODUCT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .productDate(DEFAULT_PRODUCT_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .userName(DEFAULT_USER_NAME);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .productName(UPDATED_PRODUCT_NAME)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .price(UPDATED_PRICE)
            .approval(UPDATED_APPROVAL)
            .showStatus(UPDATED_SHOW_STATUS)
            .productType(UPDATED_PRODUCT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .productDate(UPDATED_PRODUCT_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .userName(UPDATED_USER_NAME);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getApproval()).isEqualTo(DEFAULT_APPROVAL);
        assertThat(testProduct.isShowStatus()).isEqualTo(DEFAULT_SHOW_STATUS);
        assertThat(testProduct.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProduct.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testProduct.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testProduct.getProductDate()).isEqualTo(DEFAULT_PRODUCT_DATE);
        assertThat(testProduct.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testProduct.getUserName()).isEqualTo(DEFAULT_USER_NAME);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }


    @Test
    @Transactional
    public void checkProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setProductName(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setProductDescription(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setPrice(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].approval").value(hasItem(DEFAULT_APPROVAL.toString())))
            .andExpect(jsonPath("$.[*].showStatus").value(hasItem(DEFAULT_SHOW_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].productDate").value(hasItem(DEFAULT_PRODUCT_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.productDescription").value(DEFAULT_PRODUCT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.approval").value(DEFAULT_APPROVAL.toString()))
            .andExpect(jsonPath("$.showStatus").value(DEFAULT_SHOW_STATUS.booleanValue()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.productDate").value(DEFAULT_PRODUCT_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME));
    }


    @Test
    @Transactional
    public void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName equals to DEFAULT_PRODUCT_NAME
        defaultProductShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName not equals to DEFAULT_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.notEquals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName not equals to UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.notEquals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the productList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName is not null
        defaultProductShouldBeFound("productName.specified=true");

        // Get all the productList where productName is null
        defaultProductShouldNotBeFound("productName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByProductNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName contains DEFAULT_PRODUCT_NAME
        defaultProductShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName contains UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName does not contain UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByProductDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDescription equals to DEFAULT_PRODUCT_DESCRIPTION
        defaultProductShouldBeFound("productDescription.equals=" + DEFAULT_PRODUCT_DESCRIPTION);

        // Get all the productList where productDescription equals to UPDATED_PRODUCT_DESCRIPTION
        defaultProductShouldNotBeFound("productDescription.equals=" + UPDATED_PRODUCT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDescription not equals to DEFAULT_PRODUCT_DESCRIPTION
        defaultProductShouldNotBeFound("productDescription.notEquals=" + DEFAULT_PRODUCT_DESCRIPTION);

        // Get all the productList where productDescription not equals to UPDATED_PRODUCT_DESCRIPTION
        defaultProductShouldBeFound("productDescription.notEquals=" + UPDATED_PRODUCT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDescription in DEFAULT_PRODUCT_DESCRIPTION or UPDATED_PRODUCT_DESCRIPTION
        defaultProductShouldBeFound("productDescription.in=" + DEFAULT_PRODUCT_DESCRIPTION + "," + UPDATED_PRODUCT_DESCRIPTION);

        // Get all the productList where productDescription equals to UPDATED_PRODUCT_DESCRIPTION
        defaultProductShouldNotBeFound("productDescription.in=" + UPDATED_PRODUCT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDescription is not null
        defaultProductShouldBeFound("productDescription.specified=true");

        // Get all the productList where productDescription is null
        defaultProductShouldNotBeFound("productDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByProductDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDescription contains DEFAULT_PRODUCT_DESCRIPTION
        defaultProductShouldBeFound("productDescription.contains=" + DEFAULT_PRODUCT_DESCRIPTION);

        // Get all the productList where productDescription contains UPDATED_PRODUCT_DESCRIPTION
        defaultProductShouldNotBeFound("productDescription.contains=" + UPDATED_PRODUCT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDescription does not contain DEFAULT_PRODUCT_DESCRIPTION
        defaultProductShouldNotBeFound("productDescription.doesNotContain=" + DEFAULT_PRODUCT_DESCRIPTION);

        // Get all the productList where productDescription does not contain UPDATED_PRODUCT_DESCRIPTION
        defaultProductShouldBeFound("productDescription.doesNotContain=" + UPDATED_PRODUCT_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProductsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price equals to DEFAULT_PRICE
        defaultProductShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price not equals to DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productList where price not equals to UPDATED_PRICE
        defaultProductShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is not null
        defaultProductShouldBeFound("price.specified=true");

        // Get all the productList where price is null
        defaultProductShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than or equal to (DEFAULT_PRICE + 1)
        defaultProductShouldNotBeFound("price.greaterThanOrEqual=" + (DEFAULT_PRICE + 1));
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is less than or equal to SMALLER_PRICE
        defaultProductShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productList where price is less than (DEFAULT_PRICE + 1)
        defaultProductShouldBeFound("price.lessThan=" + (DEFAULT_PRICE + 1));
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than SMALLER_PRICE
        defaultProductShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductsByApprovalIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where approval equals to DEFAULT_APPROVAL
        defaultProductShouldBeFound("approval.equals=" + DEFAULT_APPROVAL);

        // Get all the productList where approval equals to UPDATED_APPROVAL
        defaultProductShouldNotBeFound("approval.equals=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    public void getAllProductsByApprovalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where approval not equals to DEFAULT_APPROVAL
        defaultProductShouldNotBeFound("approval.notEquals=" + DEFAULT_APPROVAL);

        // Get all the productList where approval not equals to UPDATED_APPROVAL
        defaultProductShouldBeFound("approval.notEquals=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    public void getAllProductsByApprovalIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where approval in DEFAULT_APPROVAL or UPDATED_APPROVAL
        defaultProductShouldBeFound("approval.in=" + DEFAULT_APPROVAL + "," + UPDATED_APPROVAL);

        // Get all the productList where approval equals to UPDATED_APPROVAL
        defaultProductShouldNotBeFound("approval.in=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    public void getAllProductsByApprovalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where approval is not null
        defaultProductShouldBeFound("approval.specified=true");

        // Get all the productList where approval is null
        defaultProductShouldNotBeFound("approval.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByShowStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where showStatus equals to DEFAULT_SHOW_STATUS
        defaultProductShouldBeFound("showStatus.equals=" + DEFAULT_SHOW_STATUS);

        // Get all the productList where showStatus equals to UPDATED_SHOW_STATUS
        defaultProductShouldNotBeFound("showStatus.equals=" + UPDATED_SHOW_STATUS);
    }

    @Test
    @Transactional
    public void getAllProductsByShowStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where showStatus not equals to DEFAULT_SHOW_STATUS
        defaultProductShouldNotBeFound("showStatus.notEquals=" + DEFAULT_SHOW_STATUS);

        // Get all the productList where showStatus not equals to UPDATED_SHOW_STATUS
        defaultProductShouldBeFound("showStatus.notEquals=" + UPDATED_SHOW_STATUS);
    }

    @Test
    @Transactional
    public void getAllProductsByShowStatusIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where showStatus in DEFAULT_SHOW_STATUS or UPDATED_SHOW_STATUS
        defaultProductShouldBeFound("showStatus.in=" + DEFAULT_SHOW_STATUS + "," + UPDATED_SHOW_STATUS);

        // Get all the productList where showStatus equals to UPDATED_SHOW_STATUS
        defaultProductShouldNotBeFound("showStatus.in=" + UPDATED_SHOW_STATUS);
    }

    @Test
    @Transactional
    public void getAllProductsByShowStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where showStatus is not null
        defaultProductShouldBeFound("showStatus.specified=true");

        // Get all the productList where showStatus is null
        defaultProductShouldNotBeFound("showStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultProductShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType not equals to DEFAULT_PRODUCT_TYPE
        defaultProductShouldNotBeFound("productType.notEquals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productList where productType not equals to UPDATED_PRODUCT_TYPE
        defaultProductShouldBeFound("productType.notEquals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultProductShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the productList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType is not null
        defaultProductShouldBeFound("productType.specified=true");

        // Get all the productList where productType is null
        defaultProductShouldNotBeFound("productType.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate equals to DEFAULT_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.equals=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where productDate equals to UPDATED_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.equals=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate not equals to DEFAULT_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.notEquals=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where productDate not equals to UPDATED_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.notEquals=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate in DEFAULT_PRODUCT_DATE or UPDATED_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.in=" + DEFAULT_PRODUCT_DATE + "," + UPDATED_PRODUCT_DATE);

        // Get all the productList where productDate equals to UPDATED_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.in=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate is not null
        defaultProductShouldBeFound("productDate.specified=true");

        // Get all the productList where productDate is null
        defaultProductShouldNotBeFound("productDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate is greater than or equal to DEFAULT_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.greaterThanOrEqual=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where productDate is greater than or equal to UPDATED_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.greaterThanOrEqual=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate is less than or equal to DEFAULT_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.lessThanOrEqual=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where productDate is less than or equal to SMALLER_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.lessThanOrEqual=" + SMALLER_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate is less than DEFAULT_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.lessThan=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where productDate is less than UPDATED_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.lessThan=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productDate is greater than DEFAULT_PRODUCT_DATE
        defaultProductShouldNotBeFound("productDate.greaterThan=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where productDate is greater than SMALLER_PRODUCT_DATE
        defaultProductShouldBeFound("productDate.greaterThan=" + SMALLER_PRODUCT_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the productList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the productList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the productList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate is not null
        defaultProductShouldBeFound("updatedDate.specified=true");

        // Get all the productList where updatedDate is null
        defaultProductShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate is greater than or equal to DEFAULT_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.greaterThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the productList where updatedDate is greater than or equal to UPDATED_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.greaterThanOrEqual=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate is less than or equal to DEFAULT_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.lessThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the productList where updatedDate is less than or equal to SMALLER_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.lessThanOrEqual=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate is less than DEFAULT_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.lessThan=" + DEFAULT_UPDATED_DATE);

        // Get all the productList where updatedDate is less than UPDATED_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.lessThan=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where updatedDate is greater than DEFAULT_UPDATED_DATE
        defaultProductShouldNotBeFound("updatedDate.greaterThan=" + DEFAULT_UPDATED_DATE);

        // Get all the productList where updatedDate is greater than SMALLER_UPDATED_DATE
        defaultProductShouldBeFound("updatedDate.greaterThan=" + SMALLER_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where userName equals to DEFAULT_USER_NAME
        defaultProductShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the productList where userName equals to UPDATED_USER_NAME
        defaultProductShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where userName not equals to DEFAULT_USER_NAME
        defaultProductShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the productList where userName not equals to UPDATED_USER_NAME
        defaultProductShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultProductShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the productList where userName equals to UPDATED_USER_NAME
        defaultProductShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where userName is not null
        defaultProductShouldBeFound("userName.specified=true");

        // Get all the productList where userName is null
        defaultProductShouldNotBeFound("userName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByUserNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where userName contains DEFAULT_USER_NAME
        defaultProductShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the productList where userName contains UPDATED_USER_NAME
        defaultProductShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where userName does not contain DEFAULT_USER_NAME
        defaultProductShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the productList where userName does not contain UPDATED_USER_NAME
        defaultProductShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByImagesIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Images images = ImagesResourceIT.createEntity(em);
        em.persist(images);
        em.flush();
        product.addImages(images);
        productRepository.saveAndFlush(product);
        Long imagesId = images.getId();

        // Get all the productList where images equals to imagesId
        defaultProductShouldBeFound("imagesId.equals=" + imagesId);

        // Get all the productList where images equals to imagesId + 1
        defaultProductShouldNotBeFound("imagesId.equals=" + (imagesId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        FeedBack rating = FeedBackResourceIT.createEntity(em);
        em.persist(rating);
        em.flush();
        product.addRating(rating);
        productRepository.saveAndFlush(product);
        Long ratingId = rating.getId();

        // Get all the productList where rating equals to ratingId
        defaultProductShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the productList where rating equals to ratingId + 1
        defaultProductShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByNurseryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Nursery nursery = NurseryResourceIT.createEntity(em);
        em.persist(nursery);
        em.flush();
        product.setNursery(nursery);
        productRepository.saveAndFlush(product);
        Long nurseryId = nursery.getId();

        // Get all the productList where nursery equals to nurseryId
        defaultProductShouldBeFound("nurseryId.equals=" + nurseryId);

        // Get all the productList where nursery equals to nurseryId + 1
        defaultProductShouldNotBeFound("nurseryId.equals=" + (nurseryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].approval").value(hasItem(DEFAULT_APPROVAL.toString())))
            .andExpect(jsonPath("$.[*].showStatus").value(hasItem(DEFAULT_SHOW_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].productDate").value(hasItem(DEFAULT_PRODUCT_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));

        // Check, that the count call also returns 1
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .productName(UPDATED_PRODUCT_NAME)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .price(UPDATED_PRICE)
            .approval(UPDATED_APPROVAL)
            .showStatus(UPDATED_SHOW_STATUS)
            .productType(UPDATED_PRODUCT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .productDate(UPDATED_PRODUCT_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .userName(UPDATED_USER_NAME);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getApproval()).isEqualTo(UPDATED_APPROVAL);
        assertThat(testProduct.isShowStatus()).isEqualTo(UPDATED_SHOW_STATUS);
        assertThat(testProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProduct.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testProduct.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testProduct.getProductDate()).isEqualTo(UPDATED_PRODUCT_DATE);
        assertThat(testProduct.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testProduct.getUserName()).isEqualTo(UPDATED_USER_NAME);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        productRepository.saveAndFlush(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 1), 1));

        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].approval").value(hasItem(DEFAULT_APPROVAL.toString())))
            .andExpect(jsonPath("$.[*].showStatus").value(hasItem(DEFAULT_SHOW_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].productDate").value(hasItem(DEFAULT_PRODUCT_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));
    }
}
