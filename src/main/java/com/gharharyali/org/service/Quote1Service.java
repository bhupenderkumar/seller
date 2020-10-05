package com.gharharyali.org.service;

import com.gharharyali.org.service.dto.Quote1DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gharharyali.org.domain.Quote1}.
 */
public interface Quote1Service {

    /**
     * Save a quote1.
     *
     * @param quote1DTO the entity to save.
     * @return the persisted entity.
     */
    Quote1DTO save(Quote1DTO quote1DTO);

    /**
     * Get all the quote1s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Quote1DTO> findAll(Pageable pageable);


    /**
     * Get the "id" quote1.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Quote1DTO> findOne(Long id);

    /**
     * Delete the "id" quote1.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the quote1 corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Quote1DTO> search(String query, Pageable pageable);
}
