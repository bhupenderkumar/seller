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

import com.gharharyali.org.domain.Nursery;
import com.gharharyali.org.domain.*; // for static metamodels
import com.gharharyali.org.repository.NurseryRepository;
import com.gharharyali.org.repository.search.NurserySearchRepository;
import com.gharharyali.org.service.dto.NurseryCriteria;
import com.gharharyali.org.service.dto.NurseryDTO;
import com.gharharyali.org.service.mapper.NurseryMapper;

/**
 * Service for executing complex queries for {@link Nursery} entities in the database.
 * The main input is a {@link NurseryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NurseryDTO} or a {@link Page} of {@link NurseryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NurseryQueryService extends QueryService<Nursery> {

    private final Logger log = LoggerFactory.getLogger(NurseryQueryService.class);

    private final NurseryRepository nurseryRepository;

    private final NurseryMapper nurseryMapper;

    private final NurserySearchRepository nurserySearchRepository;

    public NurseryQueryService(NurseryRepository nurseryRepository, NurseryMapper nurseryMapper, NurserySearchRepository nurserySearchRepository) {
        this.nurseryRepository = nurseryRepository;
        this.nurseryMapper = nurseryMapper;
        this.nurserySearchRepository = nurserySearchRepository;
    }

    /**
     * Return a {@link List} of {@link NurseryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NurseryDTO> findByCriteria(NurseryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nursery> specification = createSpecification(criteria);
        return nurseryMapper.toDto(nurseryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NurseryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NurseryDTO> findByCriteria(NurseryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nursery> specification = createSpecification(criteria);
        return nurseryRepository.findAll(specification, page)
            .map(nurseryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NurseryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nursery> specification = createSpecification(criteria);
        return nurseryRepository.count(specification);
    }

    /**
     * Function to convert {@link NurseryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nursery> createSpecification(NurseryCriteria criteria) {
        Specification<Nursery> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Nursery_.id));
            }
            if (criteria.getNurseryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNurseryName(), Nursery_.nurseryName));
            }
            if (criteria.getHouseNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHouseNo(), Nursery_.houseNo));
            }
            if (criteria.getSalutation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalutation(), Nursery_.salutation));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Nursery_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Nursery_.lastName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Nursery_.middleName));
            }
            if (criteria.getStreetNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetNo(), Nursery_.streetNo));
            }
            if (criteria.getDistrictNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictNo(), Nursery_.districtNo));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Nursery_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Nursery_.state));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildSpecification(criteria.getCountry(), Nursery_.country));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), Nursery_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), Nursery_.longitude));
            }
            if (criteria.getAddharNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddharNo(), Nursery_.addharNo));
            }
            if (criteria.getPanCardNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanCardNo(), Nursery_.panCardNo));
            }
            if (criteria.getPayMentMode() != null) {
                specification = specification.and(buildSpecification(criteria.getPayMentMode(), Nursery_.payMentMode));
            }
            if (criteria.getUpiId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpiId(), Nursery_.upiId));
            }
            if (criteria.getPayMentDuration() != null) {
                specification = specification.and(buildSpecification(criteria.getPayMentDuration(), Nursery_.payMentDuration));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), Nursery_.accountNo));
            }
            if (criteria.getBankIFSC() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankIFSC(), Nursery_.bankIFSC));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), Nursery_.bankName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Nursery_.createdDate));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), Nursery_.updatedDate));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), Nursery_.userName));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Nursery_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getTransactionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionsId(),
                    root -> root.join(Nursery_.transactions, JoinType.LEFT).get(Transaction_.id)));
            }
        }
        return specification;
    }
}
