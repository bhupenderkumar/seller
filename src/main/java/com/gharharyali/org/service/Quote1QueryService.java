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

import com.gharharyali.org.domain.Quote1;
import com.gharharyali.org.domain.*; // for static metamodels
import com.gharharyali.org.repository.Quote1Repository;
import com.gharharyali.org.repository.search.Quote1SearchRepository;
import com.gharharyali.org.service.dto.Quote1Criteria;
import com.gharharyali.org.service.dto.Quote1DTO;
import com.gharharyali.org.service.mapper.Quote1Mapper;

/**
 * Service for executing complex queries for {@link Quote1} entities in the database.
 * The main input is a {@link Quote1Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Quote1DTO} or a {@link Page} of {@link Quote1DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Quote1QueryService extends QueryService<Quote1> {

    private final Logger log = LoggerFactory.getLogger(Quote1QueryService.class);

    private final Quote1Repository quote1Repository;

    private final Quote1Mapper quote1Mapper;

    private final Quote1SearchRepository quote1SearchRepository;

    public Quote1QueryService(Quote1Repository quote1Repository, Quote1Mapper quote1Mapper, Quote1SearchRepository quote1SearchRepository) {
        this.quote1Repository = quote1Repository;
        this.quote1Mapper = quote1Mapper;
        this.quote1SearchRepository = quote1SearchRepository;
    }

    /**
     * Return a {@link List} of {@link Quote1DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Quote1DTO> findByCriteria(Quote1Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Quote1> specification = createSpecification(criteria);
        return quote1Mapper.toDto(quote1Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link Quote1DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Quote1DTO> findByCriteria(Quote1Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Quote1> specification = createSpecification(criteria);
        return quote1Repository.findAll(specification, page)
            .map(quote1Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Quote1Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Quote1> specification = createSpecification(criteria);
        return quote1Repository.count(specification);
    }

    /**
     * Function to convert {@link Quote1Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Quote1> createSpecification(Quote1Criteria criteria) {
        Specification<Quote1> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Quote1_.id));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), Quote1_.symbol));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Quote1_.price));
            }
            if (criteria.getLastTrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastTrade(), Quote1_.lastTrade));
            }
        }
        return specification;
    }
}
