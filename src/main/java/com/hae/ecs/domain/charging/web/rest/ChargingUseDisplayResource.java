package com.hae.ecs.domain.charging.web.rest;

import com.hae.ecs.domain.charging.repository.ChargingUseDisplayRepository;
import com.hae.ecs.domain.charging.service.ChargingUseDisplayService;
import com.hae.ecs.domain.charging.service.dto.ChargingUseDisplayDTO;
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
 * REST controller for managing {@link com.hae.ecs.domain.charging.domain.ChargingUseDisplay}.
 */
@RestController
@RequestMapping("/api")
public class ChargingUseDisplayResource {

    private final Logger log = LoggerFactory.getLogger(ChargingUseDisplayResource.class);

    private static final String ENTITY_NAME = "chargingUseDisplay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargingUseDisplayService chargingUseDisplayService;

    private final ChargingUseDisplayRepository chargingUseDisplayRepository;

    public ChargingUseDisplayResource(
        ChargingUseDisplayService chargingUseDisplayService,
        ChargingUseDisplayRepository chargingUseDisplayRepository
    ) {
        this.chargingUseDisplayService = chargingUseDisplayService;
        this.chargingUseDisplayRepository = chargingUseDisplayRepository;
    }

    /**
     * {@code POST  /charging-use-displays} : Create a new chargingUseDisplay.
     *
     * @param chargingUseDisplayDTO the chargingUseDisplayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chargingUseDisplayDTO, or with status {@code 400 (Bad Request)} if the chargingUseDisplay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charging-use-displays")
    public ResponseEntity<ChargingUseDisplayDTO> createChargingUseDisplay(@Valid @RequestBody ChargingUseDisplayDTO chargingUseDisplayDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChargingUseDisplay : {}", chargingUseDisplayDTO);
        if (chargingUseDisplayDTO.getId() != null) {
            throw new BadRequestAlertException("A new chargingUseDisplay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargingUseDisplayDTO result = chargingUseDisplayService.save(chargingUseDisplayDTO);
        return ResponseEntity
            .created(new URI("/api/charging-use-displays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charging-use-displays/:id} : Updates an existing chargingUseDisplay.
     *
     * @param id the id of the chargingUseDisplayDTO to save.
     * @param chargingUseDisplayDTO the chargingUseDisplayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargingUseDisplayDTO,
     * or with status {@code 400 (Bad Request)} if the chargingUseDisplayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chargingUseDisplayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charging-use-displays/{id}")
    public ResponseEntity<ChargingUseDisplayDTO> updateChargingUseDisplay(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChargingUseDisplayDTO chargingUseDisplayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChargingUseDisplay : {}, {}", id, chargingUseDisplayDTO);
        if (chargingUseDisplayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargingUseDisplayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargingUseDisplayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChargingUseDisplayDTO result = chargingUseDisplayService.update(chargingUseDisplayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargingUseDisplayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /charging-use-displays/:id} : Partial updates given fields of an existing chargingUseDisplay, field will ignore if it is null
     *
     * @param id the id of the chargingUseDisplayDTO to save.
     * @param chargingUseDisplayDTO the chargingUseDisplayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargingUseDisplayDTO,
     * or with status {@code 400 (Bad Request)} if the chargingUseDisplayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chargingUseDisplayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chargingUseDisplayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/charging-use-displays/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChargingUseDisplayDTO> partialUpdateChargingUseDisplay(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChargingUseDisplayDTO chargingUseDisplayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChargingUseDisplay partially : {}, {}", id, chargingUseDisplayDTO);
        if (chargingUseDisplayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargingUseDisplayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargingUseDisplayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChargingUseDisplayDTO> result = chargingUseDisplayService.partialUpdate(chargingUseDisplayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargingUseDisplayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /charging-use-displays} : get all the chargingUseDisplays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chargingUseDisplays in body.
     */
    @GetMapping("/charging-use-displays")
    public ResponseEntity<List<ChargingUseDisplayDTO>> getAllChargingUseDisplays(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ChargingUseDisplays");
        Page<ChargingUseDisplayDTO> page = chargingUseDisplayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /charging-use-displays/:id} : get the "id" chargingUseDisplay.
     *
     * @param id the id of the chargingUseDisplayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chargingUseDisplayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charging-use-displays/{id}")
    public ResponseEntity<ChargingUseDisplayDTO> getChargingUseDisplay(@PathVariable Long id) {
        log.debug("REST request to get ChargingUseDisplay : {}", id);
        Optional<ChargingUseDisplayDTO> chargingUseDisplayDTO = chargingUseDisplayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargingUseDisplayDTO);
    }

    /**
     * {@code DELETE  /charging-use-displays/:id} : delete the "id" chargingUseDisplay.
     *
     * @param id the id of the chargingUseDisplayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charging-use-displays/{id}")
    public ResponseEntity<Void> deleteChargingUseDisplay(@PathVariable Long id) {
        log.debug("REST request to delete ChargingUseDisplay : {}", id);
        chargingUseDisplayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
