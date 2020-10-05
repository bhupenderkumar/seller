package com.gharharyali.org.service;

import com.gharharyali.org.service.dto.NurseryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gharharyali.org.domain.Nursery}.
 */
public interface NurseryService {

    /**
     * Save a nursery.
     *
     * @param nurseryDTO the entity to save.
     * @return the persisted entity.
     */
    NurseryDTO save(NurseryDTO nurseryDTO);

    /**
     * Get all the nurseries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NurseryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nursery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NurseryDTO> findOne(Long id);

    /**
     * Delete the "id" nursery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the nursery corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NurseryDTO> search(String query, Pageable pageable);
}
