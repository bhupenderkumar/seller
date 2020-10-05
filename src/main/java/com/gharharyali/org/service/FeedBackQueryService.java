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

import com.gharharyali.org.domain.FeedBack;
import com.gharharyali.org.domain.*; // for static metamodels
import com.gharharyali.org.repository.FeedBackRepository;
import com.gharharyali.org.repository.search.FeedBackSearchRepository;
import com.gharharyali.org.service.dto.FeedBackCriteria;
import com.gharharyali.org.service.dto.FeedBackDTO;
import com.gharharyali.org.service.mapper.FeedBackMapper;

/**
 * Service for executing complex queries for {@link FeedBack} entities in the database.
 * The main input is a {@link FeedBackCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeedBackDTO} or a {@link Page} of {@link FeedBackDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedBackQueryService extends QueryService<FeedBack> {

    private final Logger log = LoggerFactory.getLogger(FeedBackQueryService.class);

    private final FeedBackRepository feedBackRepository;

    private final FeedBackMapper feedBackMapper;

    private final FeedBackSearchRepository feedBackSearchRepository;

    public FeedBackQueryService(FeedBackRepository feedBackRepository, FeedBackMapper feedBackMapper, FeedBackSearchRepository feedBackSearchRepository) {
        this.feedBackRepository = feedBackRepository;
        this.feedBackMapper = feedBackMapper;
        this.feedBackSearchRepository = feedBackSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FeedBackDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeedBackDTO> findByCriteria(FeedBackCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeedBack> specification = createSpecification(criteria);
        return feedBackMapper.toDto(feedBackRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FeedBackDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedBackDTO> findByCriteria(FeedBackCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeedBack> specification = createSpecification(criteria);
        return feedBackRepository.findAll(specification, page)
            .map(feedBackMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedBackCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeedBack> specification = createSpecification(criteria);
        return feedBackRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedBackCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeedBack> createSpecification(FeedBackCriteria criteria) {
        Specification<FeedBack> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeedBack_.id));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), FeedBack_.rating));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), FeedBack_.userName));
            }
            if (criteria.getUserComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserComments(), FeedBack_.userComments));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(FeedBack_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
