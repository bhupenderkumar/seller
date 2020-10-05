package com.gharharyali.org.web.rest;

import com.gharharyali.org.service.Quote1Service;
import com.gharharyali.org.web.rest.errors.BadRequestAlertException;
import com.gharharyali.org.service.dto.Quote1DTO;
import com.gharharyali.org.service.dto.Quote1Criteria;
import com.gharharyali.org.service.Quote1QueryService;

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
 * REST controller for managing {@link com.gharharyali.org.domain.Quote1}.
 */
@RestController
@RequestMapping("/api")
public class Quote1Resource {

    private final Logger log = LoggerFactory.getLogger(Quote1Resource.class);

    private static final String ENTITY_NAME = "sellerQuote1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Quote1Service quote1Service;

    private final Quote1QueryService quote1QueryService;

    public Quote1Resource(Quote1Service quote1Service, Quote1QueryService quote1QueryService) {
        this.quote1Service = quote1Service;
        this.quote1QueryService = quote1QueryService;
    }

    /**
     * {@code POST  /quote-1-s} : Create a new quote1.
     *
     * @param quote1DTO the quote1DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quote1DTO, or with status {@code 400 (Bad Request)} if the quote1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quote-1-s")
    public ResponseEntity<Quote1DTO> createQuote1(@Valid @RequestBody Quote1DTO quote1DTO) throws URISyntaxException {
        log.debug("REST request to save Quote1 : {}", quote1DTO);
        if (quote1DTO.getId() != null) {
            throw new BadRequestAlertException("A new quote1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quote1DTO result = quote1Service.save(quote1DTO);
        return ResponseEntity.created(new URI("/api/quote-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quote-1-s} : Updates an existing quote1.
     *
     * @param quote1DTO the quote1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quote1DTO,
     * or with status {@code 400 (Bad Request)} if the quote1DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quote1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quote-1-s")
    public ResponseEntity<Quote1DTO> updateQuote1(@Valid @RequestBody Quote1DTO quote1DTO) throws URISyntaxException {
        log.debug("REST request to update Quote1 : {}", quote1DTO);
        if (quote1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quote1DTO result = quote1Service.save(quote1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quote1DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quote-1-s} : get all the quote1s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quote1s in body.
     */
    @GetMapping("/quote-1-s")
    public ResponseEntity<List<Quote1DTO>> getAllQuote1s(Quote1Criteria criteria, Pageable pageable) {
        log.debug("REST request to get Quote1s by criteria: {}", criteria);
        Page<Quote1DTO> page = quote1QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quote-1-s/count} : count all the quote1s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/quote-1-s/count")
    public ResponseEntity<Long> countQuote1s(Quote1Criteria criteria) {
        log.debug("REST request to count Quote1s by criteria: {}", criteria);
        return ResponseEntity.ok().body(quote1QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /quote-1-s/:id} : get the "id" quote1.
     *
     * @param id the id of the quote1DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quote1DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quote-1-s/{id}")
    public ResponseEntity<Quote1DTO> getQuote1(@PathVariable Long id) {
        log.debug("REST request to get Quote1 : {}", id);
        Optional<Quote1DTO> quote1DTO = quote1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(quote1DTO);
    }

    /**
     * {@code DELETE  /quote-1-s/:id} : delete the "id" quote1.
     *
     * @param id the id of the quote1DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quote-1-s/{id}")
    public ResponseEntity<Void> deleteQuote1(@PathVariable Long id) {
        log.debug("REST request to delete Quote1 : {}", id);
        quote1Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/quote-1-s?query=:query} : search for the quote1 corresponding
     * to the query.
     *
     * @param query the query of the quote1 search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/quote-1-s")
    public ResponseEntity<List<Quote1DTO>> searchQuote1s(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Quote1s for query {}", query);
        Page<Quote1DTO> page = quote1Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
