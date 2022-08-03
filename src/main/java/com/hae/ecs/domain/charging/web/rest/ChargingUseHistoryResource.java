package com.hae.ecs.domain.charging.web.rest;

import com.hae.ecs.domain.charging.repository.ChargingUseHistoryRepository;
import com.hae.ecs.domain.charging.service.ChargingUseHistoryService;
import com.hae.ecs.domain.charging.service.dto.ChargingUseHistoryDTO;
import com.hae.ecs.domain.charging.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.hae.ecs.domain.charging.domain.ChargingUseHistory}.
 */
@RestController
@RequestMapping("/api")
public class ChargingUseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ChargingUseHistoryResource.class);

    private static final String ENTITY_NAME = "chargingUseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargingUseHistoryService chargingUseHistoryService;

    private final ChargingUseHistoryRepository chargingUseHistoryRepository;

    public ChargingUseHistoryResource(
        ChargingUseHistoryService chargingUseHistoryService,
        ChargingUseHistoryRepository chargingUseHistoryRepository
    ) {
        this.chargingUseHistoryService = chargingUseHistoryService;
        this.chargingUseHistoryRepository = chargingUseHistoryRepository;
    }

    /**
     * {@code POST  /charging-use-histories} : Create a new chargingUseHistory.
     *
     * @param chargingUseHistoryDTO the chargingUseHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chargingUseHistoryDTO, or with status {@code 400 (Bad Request)} if the chargingUseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charging-use-histories")
    public ResponseEntity<ChargingUseHistoryDTO> createChargingUseHistory(@Valid @RequestBody ChargingUseHistoryDTO chargingUseHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChargingUseHistory : {}", chargingUseHistoryDTO);
        if (chargingUseHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new chargingUseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargingUseHistoryDTO result = chargingUseHistoryService.save(chargingUseHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/charging-use-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charging-use-histories/:id} : Updates an existing chargingUseHistory.
     *
     * @param id the id of the chargingUseHistoryDTO to save.
     * @param chargingUseHistoryDTO the chargingUseHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargingUseHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the chargingUseHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chargingUseHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charging-use-histories/{id}")
    public ResponseEntity<ChargingUseHistoryDTO> updateChargingUseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChargingUseHistoryDTO chargingUseHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChargingUseHistory : {}, {}", id, chargingUseHistoryDTO);
        if (chargingUseHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargingUseHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargingUseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChargingUseHistoryDTO result = chargingUseHistoryService.update(chargingUseHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargingUseHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /charging-use-histories/:id} : Partial updates given fields of an existing chargingUseHistory, field will ignore if it is null
     *
     * @param id the id of the chargingUseHistoryDTO to save.
     * @param chargingUseHistoryDTO the chargingUseHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargingUseHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the chargingUseHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chargingUseHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chargingUseHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/charging-use-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChargingUseHistoryDTO> partialUpdateChargingUseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChargingUseHistoryDTO chargingUseHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChargingUseHistory partially : {}, {}", id, chargingUseHistoryDTO);
        if (chargingUseHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargingUseHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargingUseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChargingUseHistoryDTO> result = chargingUseHistoryService.partialUpdate(chargingUseHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargingUseHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /charging-use-histories} : get all the chargingUseHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chargingUseHistories in body.
     */
    @GetMapping("/charging-use-histories")
    public ResponseEntity<List<ChargingUseHistoryDTO>> getAllChargingUseHistories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ChargingUseHistories");
        Page<ChargingUseHistoryDTO> page = chargingUseHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /charging-use-histories/:id} : get the "id" chargingUseHistory.
     *
     * @param id the id of the chargingUseHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chargingUseHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charging-use-histories/{id}")
    public ResponseEntity<ChargingUseHistoryDTO> getChargingUseHistory(@PathVariable Long id) {
        log.debug("REST request to get ChargingUseHistory : {}", id);
        Optional<ChargingUseHistoryDTO> chargingUseHistoryDTO = chargingUseHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargingUseHistoryDTO);
    }

    /**
     * {@code DELETE  /charging-use-histories/:id} : delete the "id" chargingUseHistory.
     *
     * @param id the id of the chargingUseHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charging-use-histories/{id}")
    public ResponseEntity<Void> deleteChargingUseHistory(@PathVariable Long id) {
        log.debug("REST request to delete ChargingUseHistory : {}", id);
        chargingUseHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
