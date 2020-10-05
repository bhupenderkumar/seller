package com.gharharyali.org.service.impl;

import com.gharharyali.org.service.ImagesService;
import com.gharharyali.org.domain.Images;
import com.gharharyali.org.repository.ImagesRepository;
import com.gharharyali.org.repository.search.ImagesSearchRepository;
import com.gharharyali.org.service.dto.ImagesDTO;
import com.gharharyali.org.service.mapper.ImagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Images}.
 */
@Service
@Transactional
public class ImagesServiceImpl implements ImagesService {

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);

    private final ImagesRepository imagesRepository;

    private final ImagesMapper imagesMapper;

    private final ImagesSearchRepository imagesSearchRepository;

    public ImagesServiceImpl(ImagesRepository imagesRepository, ImagesMapper imagesMapper, ImagesSearchRepository imagesSearchRepository) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
        this.imagesSearchRepository = imagesSearchRepository;
    }

    @Override
    public ImagesDTO save(ImagesDTO imagesDTO) {
        log.debug("Request to save Images : {}", imagesDTO);
        Images images = imagesMapper.toEntity(imagesDTO);
        images = imagesRepository.save(images);
        ImagesDTO result = imagesMapper.toDto(images);
        imagesSearchRepository.save(images);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imagesRepository.findAll(pageable)
            .map(imagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ImagesDTO> findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        return imagesRepository.findById(id)
            .map(imagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.deleteById(id);
        imagesSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Images for query {}", query);
        return imagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(imagesMapper::toDto);
    }
}
