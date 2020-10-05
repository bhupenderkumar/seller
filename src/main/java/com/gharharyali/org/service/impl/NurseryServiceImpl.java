package com.gharharyali.org.service.impl;

import com.gharharyali.org.service.NurseryService;
import com.gharharyali.org.domain.Nursery;
import com.gharharyali.org.repository.NurseryRepository;
import com.gharharyali.org.repository.search.NurserySearchRepository;
import com.gharharyali.org.service.dto.NurseryDTO;
import com.gharharyali.org.service.mapper.NurseryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Nursery}.
 */
@Service
@Transactional
public class NurseryServiceImpl implements NurseryService {

    private final Logger log = LoggerFactory.getLogger(NurseryServiceImpl.class);

    private final NurseryRepository nurseryRepository;

    private final NurseryMapper nurseryMapper;

    private final NurserySearchRepository nurserySearchRepository;

    public NurseryServiceImpl(NurseryRepository nurseryRepository, NurseryMapper nurseryMapper, NurserySearchRepository nurserySearchRepository) {
        this.nurseryRepository = nurseryRepository;
        this.nurseryMapper = nurseryMapper;
        this.nurserySearchRepository = nurserySearchRepository;
    }

    @Override
    public NurseryDTO save(NurseryDTO nurseryDTO) {
        log.debug("Request to save Nursery : {}", nurseryDTO);
        Nursery nursery = nurseryMapper.toEntity(nurseryDTO);
        nursery = nurseryRepository.save(nursery);
        NurseryDTO result = nurseryMapper.toDto(nursery);
        nurserySearchRepository.save(nursery);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NurseryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nurseries");
        return nurseryRepository.findAll(pageable)
            .map(nurseryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NurseryDTO> findOne(Long id) {
        log.debug("Request to get Nursery : {}", id);
        return nurseryRepository.findById(id)
            .map(nurseryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nursery : {}", id);
        nurseryRepository.deleteById(id);
        nurserySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NurseryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Nurseries for query {}", query);
        return nurserySearchRepository.search(queryStringQuery(query), pageable)
            .map(nurseryMapper::toDto);
    }
}
