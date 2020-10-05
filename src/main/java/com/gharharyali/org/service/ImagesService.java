package com.gharharyali.org.service;

import com.gharharyali.org.service.dto.ImagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gharharyali.org.domain.Images}.
 */
public interface ImagesService {

    /**
     * Save a images.
     *
     * @param imagesDTO the entity to save.
     * @return the persisted entity.
     */
    ImagesDTO save(ImagesDTO imagesDTO);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImagesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" images.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImagesDTO> findOne(Long id);

    /**
     * Delete the "id" images.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the images corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImagesDTO> search(String query, Pageable pageable);
}
