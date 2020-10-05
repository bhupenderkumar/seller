package com.gharharyali.org.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gharharyali.org.security.SecurityUtils;
import com.gharharyali.org.security.resource.NurserySecurityValidation;
import com.gharharyali.org.service.NurseryQueryService;
import com.gharharyali.org.service.NurseryService;
import com.gharharyali.org.service.dto.NurseryCriteria;
import com.gharharyali.org.service.dto.NurseryDTO;
import com.gharharyali.org.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gharharyali.org.domain.Nursery}.
 */
@RestController
@RequestMapping("/api")
public class NurseryResource {

    private final Logger log = LoggerFactory.getLogger(NurseryResource.class);

    private static final String ENTITY_NAME = "sellerNursery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NurseryService nurseryService;

    private final NurseryQueryService nurseryQueryService;

    public NurseryResource(NurseryService nurseryService, NurseryQueryService nurseryQueryService) {
        this.nurseryService = nurseryService;
        this.nurseryQueryService = nurseryQueryService;
    }

    /**
     * {@code POST  /nurseries} : Create a new nursery.
     *
     * @param nurseryDTO the nurseryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nurseryDTO, or with status {@code 400 (Bad Request)} if the nursery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nurseries")
    public ResponseEntity<NurseryDTO> createNursery(@Valid @RequestBody NurseryDTO nurseryDTO) throws URISyntaxException {
        log.debug("REST request to save Nursery : {}", nurseryDTO);
        if (nurseryDTO.getId() != null) {
            throw new BadRequestAlertException("A new nursery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NurserySecurityValidation.nurserySecurityValidation(nurseryDTO);
        NurseryDTO result = nurseryService.save(nurseryDTO);
        return ResponseEntity.created(new URI("/api/nurseries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nurseries} : Updates an existing nursery.
     *
     * @param nurseryDTO the nurseryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nurseryDTO,
     * or with status {@code 400 (Bad Request)} if the nurseryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nurseryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nurseries")
    public ResponseEntity<NurseryDTO> updateNursery(@Valid @RequestBody NurseryDTO nurseryDTO) throws URISyntaxException {
        log.debug("REST request to update Nursery : {}", nurseryDTO);
        if (nurseryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NurserySecurityValidation.nurserySecurityValidation(nurseryDTO);
        NurseryDTO result = nurseryService.save(nurseryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nurseryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nurseries} : get all the nurseries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nurseries in body.
     */
    @GetMapping("/nurseries")
    public ResponseEntity<List<NurseryDTO>> getAllNurseries(NurseryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Nurseries by criteria: {}", criteria);
        NurserySecurityValidation.nurserySecurityValidation(criteria);
        Page<NurseryDTO> page = nurseryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nurseries/count} : count all the nurseries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nurseries/count")
    public ResponseEntity<Long> countNurseries(NurseryCriteria criteria) {
        log.debug("REST request to count Nurseries by criteria: {}", criteria);
        NurserySecurityValidation.nurserySecurityValidation(criteria);
        return ResponseEntity.ok().body(nurseryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nurseries/:id} : get the "id" nursery.
     *
     * @param id the id of the nurseryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nurseryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nurseries/{id}")
    public ResponseEntity<NurseryDTO> getNursery(@PathVariable Long id) {
        log.debug("REST request to get Nursery : {}", id);
        NurseryCriteria criteria =new NurseryCriteria();
        NurserySecurityValidation.nurserySecurityValidation(criteria);
        if(nurseryQueryService.countByCriteria(criteria)<1) {
        	throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<NurseryDTO> nurseryDTO = nurseryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nurseryDTO);
    }

    /**
     * {@code DELETE  /nurseries/:id} : delete the "id" nursery.
     *
     * @param id the id of the nurseryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nurseries/{id}")
    public ResponseEntity<Void> deleteNursery(@PathVariable Long id) {
        log.debug("REST request to delete Nursery : {}", id);
        NurseryCriteria criteria =new NurseryCriteria();
        NurserySecurityValidation.nurserySecurityValidation(criteria);
        if(nurseryQueryService.countByCriteria(criteria)<1) {
        	throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        nurseryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/nurseries?query=:query} : search for the nursery corresponding
     * to the query.
     *
     * @param query the query of the nursery search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/nurseries")
    public ResponseEntity<List<NurseryDTO>> searchNurseries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Nurseries for query {}", query);
        query=query.concat("username="+(SecurityUtils.getCurrentUserLogin().get()));
        Page<NurseryDTO> page = nurseryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
