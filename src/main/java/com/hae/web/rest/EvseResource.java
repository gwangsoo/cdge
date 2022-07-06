package com.hae.web.rest;

import com.hae.repository.EvseRepository;
import com.hae.service.EvseService;
import com.hae.service.dto.EvseDTO;
import com.hae.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hae.domain.Evse}.
 */
@RestController
@RequestMapping("/api")
public class EvseResource {

    private final Logger log = LoggerFactory.getLogger(EvseResource.class);

    private static final String ENTITY_NAME = "evse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvseService evseService;

    private final EvseRepository evseRepository;

    public EvseResource(EvseService evseService, EvseRepository evseRepository) {
        this.evseService = evseService;
        this.evseRepository = evseRepository;
    }

    /**
     * {@code POST  /evses} : Create a new evse.
     *
     * @param evseDTO the evseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evseDTO, or with status {@code 400 (Bad Request)} if the evse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evses")
    public ResponseEntity<EvseDTO> createEvse(@Valid @RequestBody EvseDTO evseDTO) throws URISyntaxException {
        log.debug("REST request to save Evse : {}", evseDTO);
        if (evseDTO.getId() != null) {
            throw new BadRequestAlertException("A new evse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvseDTO result = evseService.save(evseDTO);
        return ResponseEntity
            .created(new URI("/api/evses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evses/:id} : Updates an existing evse.
     *
     * @param id the id of the evseDTO to save.
     * @param evseDTO the evseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evseDTO,
     * or with status {@code 400 (Bad Request)} if the evseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evses/{id}")
    public ResponseEntity<EvseDTO> updateEvse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EvseDTO evseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Evse : {}, {}", id, evseDTO);
        if (evseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvseDTO result = evseService.update(evseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evses/:id} : Partial updates given fields of an existing evse, field will ignore if it is null
     *
     * @param id the id of the evseDTO to save.
     * @param evseDTO the evseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evseDTO,
     * or with status {@code 400 (Bad Request)} if the evseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EvseDTO> partialUpdateEvse(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EvseDTO evseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Evse partially : {}, {}", id, evseDTO);
        if (evseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvseDTO> result = evseService.partialUpdate(evseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evses} : get all the evses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evses in body.
     */
    @GetMapping("/evses")
    public ResponseEntity<List<EvseDTO>> getAllEvses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Evses");
        Page<EvseDTO> page = evseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evses/:id} : get the "id" evse.
     *
     * @param id the id of the evseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evses/{id}")
    public ResponseEntity<EvseDTO> getEvse(@PathVariable Long id) {
        log.debug("REST request to get Evse : {}", id);
        Optional<EvseDTO> evseDTO = evseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evseDTO);
    }

    /**
     * {@code DELETE  /evses/:id} : delete the "id" evse.
     *
     * @param id the id of the evseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evses/{id}")
    public ResponseEntity<Void> deleteEvse(@PathVariable Long id) {
        log.debug("REST request to delete Evse : {}", id);
        evseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
