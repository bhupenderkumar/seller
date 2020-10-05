package com.gharharyali.org.service.impl;

import com.gharharyali.org.service.FeedBackService;
import com.gharharyali.org.domain.FeedBack;
import com.gharharyali.org.repository.FeedBackRepository;
import com.gharharyali.org.repository.search.FeedBackSearchRepository;
import com.gharharyali.org.service.dto.FeedBackDTO;
import com.gharharyali.org.service.mapper.FeedBackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FeedBack}.
 */
@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    private final Logger log = LoggerFactory.getLogger(FeedBackServiceImpl.class);

    private final FeedBackRepository feedBackRepository;

    private final FeedBackMapper feedBackMapper;

    private final FeedBackSearchRepository feedBackSearchRepository;

    public FeedBackServiceImpl(FeedBackRepository feedBackRepository, FeedBackMapper feedBackMapper, FeedBackSearchRepository feedBackSearchRepository) {
        this.feedBackRepository = feedBackRepository;
        this.feedBackMapper = feedBackMapper;
        this.feedBackSearchRepository = feedBackSearchRepository;
    }

    @Override
    public FeedBackDTO save(FeedBackDTO feedBackDTO) {
        log.debug("Request to save FeedBack : {}", feedBackDTO);
        FeedBack feedBack = feedBackMapper.toEntity(feedBackDTO);
        feedBack = feedBackRepository.save(feedBack);
        FeedBackDTO result = feedBackMapper.toDto(feedBack);
        feedBackSearchRepository.save(feedBack);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedBackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeedBacks");
        return feedBackRepository.findAll(pageable)
            .map(feedBackMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FeedBackDTO> findOne(Long id) {
        log.debug("Request to get FeedBack : {}", id);
        return feedBackRepository.findById(id)
            .map(feedBackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedBack : {}", id);
        feedBackRepository.deleteById(id);
        feedBackSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedBackDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FeedBacks for query {}", query);
        return feedBackSearchRepository.search(queryStringQuery(query), pageable)
            .map(feedBackMapper::toDto);
    }
}
