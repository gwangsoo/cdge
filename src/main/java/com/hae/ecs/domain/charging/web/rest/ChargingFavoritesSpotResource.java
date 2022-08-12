package com.hae.ecs.domain.charging.web.rest;

import com.hae.ecs.domain.charging.repository.ChargingFavoritesSpotRepository;
import com.hae.ecs.domain.charging.service.ChargingFavoritesSpotService;
import com.hae.ecs.domain.charging.service.dto.ChargingFavoritesSpotDTO;
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
 * REST controller for managing {@link com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot}.
 */
@RestController
@RequestMapping("/api")
public class ChargingFavoritesSpotResource {

    private final Logger log = LoggerFactory.getLogger(ChargingFavoritesSpotResource.class);

    private static final String ENTITY_NAME = "chargingFavoritesSpot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargingFavoritesSpotService chargingFavoritesSpotService;

    private final ChargingFavoritesSpotRepository chargingFavoritesSpotRepository;

    public ChargingFavoritesSpotResource(
        ChargingFavoritesSpotService chargingFavoritesSpotService,
        ChargingFavoritesSpotRepository chargingFavoritesSpotRepository
    ) {
        this.chargingFavoritesSpotService = chargingFavoritesSpotService;
        this.chargingFavoritesSpotRepository = chargingFavoritesSpotRepository;
    }

    /**
     * {@code POST  /charging-favorites-spots} : Create a new chargingFavoritesSpot.
     *
     * @param chargingFavoritesSpotDTO the chargingFavoritesSpotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chargingFavoritesSpotDTO, or with status {@code 400 (Bad Request)} if the chargingFavoritesSpot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charging-favorites-spots")
    public ResponseEntity<ChargingFavoritesSpotDTO> createChargingFavoritesSpot(
        @Valid @RequestBody ChargingFavoritesSpotDTO chargingFavoritesSpotDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ChargingFavoritesSpot : {}", chargingFavoritesSpotDTO);
        if (chargingFavoritesSpotDTO.getId() != null) {
            throw new BadRequestAlertException("A new chargingFavoritesSpot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargingFavoritesSpotDTO result = chargingFavoritesSpotService.save(chargingFavoritesSpotDTO);
        return ResponseEntity
            .created(new URI("/api/charging-favorites-spots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charging-favorites-spots/:id} : Updates an existing chargingFavoritesSpot.
     *
     * @param id the id of the chargingFavoritesSpotDTO to save.
     * @param chargingFavoritesSpotDTO the chargingFavoritesSpotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargingFavoritesSpotDTO,
     * or with status {@code 400 (Bad Request)} if the chargingFavoritesSpotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chargingFavoritesSpotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charging-favorites-spots/{id}")
    public ResponseEntity<ChargingFavoritesSpotDTO> updateChargingFavoritesSpot(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChargingFavoritesSpotDTO chargingFavoritesSpotDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChargingFavoritesSpot : {}, {}", id, chargingFavoritesSpotDTO);
        if (chargingFavoritesSpotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargingFavoritesSpotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargingFavoritesSpotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChargingFavoritesSpotDTO result = chargingFavoritesSpotService.update(chargingFavoritesSpotDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargingFavoritesSpotDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /charging-favorites-spots/:id} : Partial updates given fields of an existing chargingFavoritesSpot, field will ignore if it is null
     *
     * @param id the id of the chargingFavoritesSpotDTO to save.
     * @param chargingFavoritesSpotDTO the chargingFavoritesSpotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargingFavoritesSpotDTO,
     * or with status {@code 400 (Bad Request)} if the chargingFavoritesSpotDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chargingFavoritesSpotDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chargingFavoritesSpotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/charging-favorites-spots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChargingFavoritesSpotDTO> partialUpdateChargingFavoritesSpot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChargingFavoritesSpotDTO chargingFavoritesSpotDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChargingFavoritesSpot partially : {}, {}", id, chargingFavoritesSpotDTO);
        if (chargingFavoritesSpotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargingFavoritesSpotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargingFavoritesSpotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChargingFavoritesSpotDTO> result = chargingFavoritesSpotService.partialUpdate(chargingFavoritesSpotDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargingFavoritesSpotDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /charging-favorites-spots} : get all the chargingFavoritesSpots.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chargingFavoritesSpots in body.
     */
    @GetMapping("/charging-favorites-spots")
    public ResponseEntity<List<ChargingFavoritesSpotDTO>> getAllChargingFavoritesSpots(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ChargingFavoritesSpots");
        Page<ChargingFavoritesSpotDTO> page = chargingFavoritesSpotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /charging-favorites-spots/:id} : get the "id" chargingFavoritesSpot.
     *
     * @param id the id of the chargingFavoritesSpotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chargingFavoritesSpotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charging-favorites-spots/{id}")
    public ResponseEntity<ChargingFavoritesSpotDTO> getChargingFavoritesSpot(@PathVariable Long id) {
        log.debug("REST request to get ChargingFavoritesSpot : {}", id);
        Optional<ChargingFavoritesSpotDTO> chargingFavoritesSpotDTO = chargingFavoritesSpotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargingFavoritesSpotDTO);
    }

    /**
     * {@code DELETE  /charging-favorites-spots/:id} : delete the "id" chargingFavoritesSpot.
     *
     * @param id the id of the chargingFavoritesSpotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charging-favorites-spots/{id}")
    public ResponseEntity<Void> deleteChargingFavoritesSpot(@PathVariable Long id) {
        log.debug("REST request to delete ChargingFavoritesSpot : {}", id);
        chargingFavoritesSpotService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
