package com.gharharyali.org.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.gharharyali.org.domain.Product;
import com.gharharyali.org.domain.*; // for static metamodels
import com.gharharyali.org.repository.ProductRepository;
import com.gharharyali.org.repository.search.ProductSearchRepository;
import com.gharharyali.org.service.dto.ProductCriteria;
import com.gharharyali.org.service.dto.ProductDTO;
import com.gharharyali.org.service.mapper.ProductMapper;

/**
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDTO} or a {@link Page} of {@link ProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductSearchRepository productSearchRepository;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper, ProductSearchRepository productSearchRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSearchRepository = productSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productMapper.toDto(productRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page)
            .map(productMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), Product_.productName));
            }
            if (criteria.getProductDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductDescription(), Product_.productDescription));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Product_.price));
            }
            if (criteria.getApproval() != null) {
                specification = specification.and(buildSpecification(criteria.getApproval(), Product_.approval));
            }
            if (criteria.getShowStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getShowStatus(), Product_.showStatus));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildSpecification(criteria.getProductType(), Product_.productType));
            }
            if (criteria.getProductDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductDate(), Product_.productDate));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), Product_.updatedDate));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), Product_.userName));
            }
            if (criteria.getImagesId() != null) {
                specification = specification.and(buildSpecification(criteria.getImagesId(),
                    root -> root.join(Product_.images, JoinType.LEFT).get(Images_.id)));
            }
            if (criteria.getRatingId() != null) {
                specification = specification.and(buildSpecification(criteria.getRatingId(),
                    root -> root.join(Product_.ratings, JoinType.LEFT).get(FeedBack_.id)));
            }
            if (criteria.getNurseryId() != null) {
                specification = specification.and(buildSpecification(criteria.getNurseryId(),
                    root -> root.join(Product_.nursery, JoinType.LEFT).get(Nursery_.id)));
            }
        }
        return specification;
    }
}
