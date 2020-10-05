package com.gharharyali.org.service.impl;

import com.gharharyali.org.service.Quote1Service;
import com.gharharyali.org.domain.Quote1;
import com.gharharyali.org.repository.Quote1Repository;
import com.gharharyali.org.repository.search.Quote1SearchRepository;
import com.gharharyali.org.service.dto.Quote1DTO;
import com.gharharyali.org.service.mapper.Quote1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Quote1}.
 */
@Service
@Transactional
public class Quote1ServiceImpl implements Quote1Service {

    private final Logger log = LoggerFactory.getLogger(Quote1ServiceImpl.class);

    private final Quote1Repository quote1Repository;

    private final Quote1Mapper quote1Mapper;

    private final Quote1SearchRepository quote1SearchRepository;

    public Quote1ServiceImpl(Quote1Repository quote1Repository, Quote1Mapper quote1Mapper, Quote1SearchRepository quote1SearchRepository) {
        this.quote1Repository = quote1Repository;
        this.quote1Mapper = quote1Mapper;
        this.quote1SearchRepository = quote1SearchRepository;
    }

    @Override
    public Quote1DTO save(Quote1DTO quote1DTO) {
        log.debug("Request to save Quote1 : {}", quote1DTO);
        Quote1 quote1 = quote1Mapper.toEntity(quote1DTO);
        quote1 = quote1Repository.save(quote1);
        Quote1DTO result = quote1Mapper.toDto(quote1);
        quote1SearchRepository.save(quote1);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Quote1DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quote1s");
        return quote1Repository.findAll(pageable)
            .map(quote1Mapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Quote1DTO> findOne(Long id) {
        log.debug("Request to get Quote1 : {}", id);
        return quote1Repository.findById(id)
            .map(quote1Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quote1 : {}", id);
        quote1Repository.deleteById(id);
        quote1SearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Quote1DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Quote1s for query {}", query);
        return quote1SearchRepository.search(queryStringQuery(query), pageable)
            .map(quote1Mapper::toDto);
    }
}
