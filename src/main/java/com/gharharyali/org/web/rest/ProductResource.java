package com.gharharyali.org.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gharharyali.org.security.SecurityUtils;
import com.gharharyali.org.security.resource.ProductSecurityValidation;
import com.gharharyali.org.service.ProductQueryService;
import com.gharharyali.org.service.ProductService;
import com.gharharyali.org.service.dto.ProductCriteria;
import com.gharharyali.org.service.dto.ProductDTO;
import com.gharharyali.org.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gharharyali.org.domain.Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

	private final Logger log = LoggerFactory.getLogger(ProductResource.class);

	private static final String ENTITY_NAME = "sellerProduct";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ProductService productService;

	private final ProductQueryService productQueryService;

	public ProductResource(ProductService productService, ProductQueryService productQueryService) {
		this.productService = productService;
		this.productQueryService = productQueryService;
	}

	/**
	 * {@code POST  /products} : Create a new product.
	 *
	 * @param productDTO the productDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new productDTO, or with status {@code 400 (Bad Request)} if
	 *         the product has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO)
			throws URISyntaxException {
		log.debug("REST request to save Product : {}", productDTO);
		if (productDTO.getId() != null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ProductSecurityValidation.productSecurityValidation(productDTO);
		ProductDTO result = productService.save(productDTO);
		return ResponseEntity
				.created(new URI("/api/products/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /products} : Updates an existing product.
	 *
	 * @param productDTO the productDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated productDTO, or with status {@code 400 (Bad Request)} if
	 *         the productDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the productDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO)
			throws URISyntaxException {
		log.debug("REST request to update Product : {}", productDTO);
		if (productDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProductSecurityValidation.productSecurityValidation(productDTO);
		ProductDTO result = productService.save(productDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /products} : get all the products.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(ProductCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Products by criteria: {}", criteria);
		ProductSecurityValidation.productSecurityValidation(criteria);
		Page<ProductDTO> page = productQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /products/count} : count all the products.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/products/count")
	public ResponseEntity<Long> countProducts(ProductCriteria criteria) {
		log.debug("REST request to count Products by criteria: {}", criteria);
		ProductSecurityValidation.productSecurityValidation(criteria);
		return ResponseEntity.ok().body(productQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /products/:id} : get the "id" product.
	 *
	 * @param id the id of the productDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the productDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		log.debug("REST request to get Product : {}", id);
		ProductCriteria criteria = new ProductCriteria();
		ProductSecurityValidation.productSecurityValidation(criteria);
		if (productQueryService.countByCriteria(criteria) < 1) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Optional<ProductDTO> productDTO = productService.findOne(id);
		return ResponseUtil.wrapOrNotFound(productDTO);
	}

	/**
	 * {@code DELETE  /products/:id} : delete the "id" product.
	 *
	 * @param id the id of the productDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		log.debug("REST request to delete Product : {}", id);
		ProductCriteria criteria = new ProductCriteria();
		ProductSecurityValidation.productSecurityValidation(criteria);
		if (productQueryService.countByCriteria(criteria) < 1) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		productService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code SEARCH  /_search/products?query=:query} : search for the product
	 * corresponding to the query.
	 *
	 * @param query    the query of the product search.
	 * @param pageable the pagination information.
	 * @return the result of the search.
	 */
	@GetMapping("/_search/products")
	public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String query, Pageable pageable) {
		log.debug("REST request to search for a page of Products for query {}", query);
		query = query.concat("username=" + (SecurityUtils.getCurrentUserLogin().get()));
		Page<ProductDTO> page = productService.search(query, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
