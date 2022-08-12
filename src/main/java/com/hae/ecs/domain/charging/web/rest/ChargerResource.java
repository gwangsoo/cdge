package com.hae.ecs.domain.charging.web.rest;

import com.hae.ecs.domain.charging.repository.ChargerRepository;
import com.hae.ecs.domain.charging.service.ChargerService;
import com.hae.ecs.domain.charging.service.dto.ChargerDTO;
import com.hae.ecs.domain.charging.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.hae.ecs.domain.charging.domain.Charger}.
 */
@RestController
@RequestMapping("/api")
public class ChargerResource {

    private final Logger log = LoggerFactory.getLogger(ChargerResource.class);

    private static final String ENTITY_NAME = "charger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargerService chargerService;

    private final ChargerRepository chargerRepository;

    public ChargerResource(ChargerService chargerService, ChargerRepository chargerRepository) {
        this.chargerService = chargerService;
        this.chargerRepository = chargerRepository;
    }

    /**
     * {@code POST  /chargers} : Create a new charger.
     *
     * @param chargerDTO the chargerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chargerDTO, or with status {@code 400 (Bad Request)} if the charger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chargers")
    public ResponseEntity<ChargerDTO> createCharger(@RequestBody ChargerDTO chargerDTO) throws URISyntaxException {
        log.debug("REST request to save Charger : {}", chargerDTO);
        if (chargerDTO.getId() != null) {
            throw new BadRequestAlertException("A new charger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargerDTO result = chargerService.save(chargerDTO);
        return ResponseEntity
            .created(new URI("/api/chargers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chargers/:id} : Updates an existing charger.
     *
     * @param id the id of the chargerDTO to save.
     * @param chargerDTO the chargerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargerDTO,
     * or with status {@code 400 (Bad Request)} if the chargerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chargerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chargers/{id}")
    public ResponseEntity<ChargerDTO> updateCharger(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChargerDTO chargerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Charger : {}, {}", id, chargerDTO);
        if (chargerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChargerDTO result = chargerService.update(chargerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chargers/:id} : Partial updates given fields of an existing charger, field will ignore if it is null
     *
     * @param id the id of the chargerDTO to save.
     * @param chargerDTO the chargerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargerDTO,
     * or with status {@code 400 (Bad Request)} if the chargerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chargerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chargerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chargers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChargerDTO> partialUpdateCharger(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChargerDTO chargerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Charger partially : {}, {}", id, chargerDTO);
        if (chargerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chargerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chargerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChargerDTO> result = chargerService.partialUpdate(chargerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chargerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chargers} : get all the chargers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chargers in body.
     */
    @GetMapping("/chargers")
    public ResponseEntity<List<ChargerDTO>> getAllChargers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Chargers");
        Page<ChargerDTO> page = chargerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chargers/:id} : get the "id" charger.
     *
     * @param id the id of the chargerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chargerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chargers/{id}")
    public ResponseEntity<ChargerDTO> getCharger(@PathVariable Long id) {
        log.debug("REST request to get Charger : {}", id);
        Optional<ChargerDTO> chargerDTO = chargerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargerDTO);
    }

    /**
     * {@code DELETE  /chargers/:id} : delete the "id" charger.
     *
     * @param id the id of the chargerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chargers/{id}")
    public ResponseEntity<Void> deleteCharger(@PathVariable Long id) {
        log.debug("REST request to delete Charger : {}", id);
        chargerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
