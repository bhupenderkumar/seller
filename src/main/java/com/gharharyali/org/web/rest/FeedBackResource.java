package com.gharharyali.org.web.rest;

import com.gharharyali.org.service.FeedBackService;
import com.gharharyali.org.web.rest.errors.BadRequestAlertException;
import com.gharharyali.org.service.dto.FeedBackDTO;
import com.gharharyali.org.service.dto.FeedBackCriteria;
import com.gharharyali.org.service.FeedBackQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.gharharyali.org.domain.FeedBack}.
 */
@RestController
@RequestMapping("/api")
public class FeedBackResource {

    private final Logger log = LoggerFactory.getLogger(FeedBackResource.class);

    private static final String ENTITY_NAME = "sellerFeedBack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedBackService feedBackService;

    private final FeedBackQueryService feedBackQueryService;

    public FeedBackResource(FeedBackService feedBackService, FeedBackQueryService feedBackQueryService) {
        this.feedBackService = feedBackService;
        this.feedBackQueryService = feedBackQueryService;
    }

    /**
     * {@code POST  /feed-backs} : Create a new feedBack.
     *
     * @param feedBackDTO the feedBackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedBackDTO, or with status {@code 400 (Bad Request)} if the feedBack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feed-backs")
    public ResponseEntity<FeedBackDTO> createFeedBack(@Valid @RequestBody FeedBackDTO feedBackDTO) throws URISyntaxException {
        log.debug("REST request to save FeedBack : {}", feedBackDTO);
        if (feedBackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedBack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity.created(new URI("/api/feed-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feed-backs} : Updates an existing feedBack.
     *
     * @param feedBackDTO the feedBackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackDTO,
     * or with status {@code 400 (Bad Request)} if the feedBackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedBackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feed-backs")
    public ResponseEntity<FeedBackDTO> updateFeedBack(@Valid @RequestBody FeedBackDTO feedBackDTO) throws URISyntaxException {
        log.debug("REST request to update FeedBack : {}", feedBackDTO);
        if (feedBackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feed-backs} : get all the feedBacks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedBacks in body.
     */
    @GetMapping("/feed-backs")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBacks(FeedBackCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FeedBacks by criteria: {}", criteria);
        Page<FeedBackDTO> page = feedBackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feed-backs/count} : count all the feedBacks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feed-backs/count")
    public ResponseEntity<Long> countFeedBacks(FeedBackCriteria criteria) {
        log.debug("REST request to count FeedBacks by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedBackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feed-backs/:id} : get the "id" feedBack.
     *
     * @param id the id of the feedBackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedBackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feed-backs/{id}")
    public ResponseEntity<FeedBackDTO> getFeedBack(@PathVariable Long id) {
        log.debug("REST request to get FeedBack : {}", id);
        Optional<FeedBackDTO> feedBackDTO = feedBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBackDTO);
    }

    /**
     * {@code DELETE  /feed-backs/:id} : delete the "id" feedBack.
     *
     * @param id the id of the feedBackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feed-backs/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable Long id) {
        log.debug("REST request to delete FeedBack : {}", id);
        feedBackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/feed-backs?query=:query} : search for the feedBack corresponding
     * to the query.
     *
     * @param query the query of the feedBack search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/feed-backs")
    public ResponseEntity<List<FeedBackDTO>> searchFeedBacks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FeedBacks for query {}", query);
        Page<FeedBackDTO> page = feedBackService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
