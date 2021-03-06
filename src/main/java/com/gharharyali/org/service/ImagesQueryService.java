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

import com.gharharyali.org.domain.Images;
import com.gharharyali.org.domain.*; // for static metamodels
import com.gharharyali.org.repository.ImagesRepository;
import com.gharharyali.org.repository.search.ImagesSearchRepository;
import com.gharharyali.org.service.dto.ImagesCriteria;
import com.gharharyali.org.service.dto.ImagesDTO;
import com.gharharyali.org.service.mapper.ImagesMapper;

/**
 * Service for executing complex queries for {@link Images} entities in the database.
 * The main input is a {@link ImagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImagesDTO} or a {@link Page} of {@link ImagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImagesQueryService extends QueryService<Images> {

    private final Logger log = LoggerFactory.getLogger(ImagesQueryService.class);

    private final ImagesRepository imagesRepository;

    private final ImagesMapper imagesMapper;

    private final ImagesSearchRepository imagesSearchRepository;

    public ImagesQueryService(ImagesRepository imagesRepository, ImagesMapper imagesMapper, ImagesSearchRepository imagesSearchRepository) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
        this.imagesSearchRepository = imagesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ImagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImagesDTO> findByCriteria(ImagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Images> specification = createSpecification(criteria);
        return imagesMapper.toDto(imagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ImagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImagesDTO> findByCriteria(ImagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Images> specification = createSpecification(criteria);
        return imagesRepository.findAll(specification, page)
            .map(imagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Images> specification = createSpecification(criteria);
        return imagesRepository.count(specification);
    }

    /**
     * Function to convert {@link ImagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Images> createSpecification(ImagesCriteria criteria) {
        Specification<Images> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Images_.id));
            }
            if (criteria.getAlt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlt(), Images_.alt));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Images_.title));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Images_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
