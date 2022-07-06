package com.hae.web.rest;

import com.hae.repository.MetaRepository;
import com.hae.service.MetaService;
import com.hae.service.dto.MetaDTO;
import com.hae.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.hae.domain.Meta}.
 */
@RestController
@RequestMapping("/api")
public class MetaResource {

    private final Logger log = LoggerFactory.getLogger(MetaResource.class);

    private static final String ENTITY_NAME = "meta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaService metaService;

    private final MetaRepository metaRepository;

    public MetaResource(MetaService metaService, MetaRepository metaRepository) {
        this.metaService = metaService;
        this.metaRepository = metaRepository;
    }

    /**
     * {@code POST  /metas} : Create a new meta.
     *
     * @param metaDTO the metaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaDTO, or with status {@code 400 (Bad Request)} if the meta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/metas")
    public ResponseEntity<MetaDTO> createMeta(@RequestBody MetaDTO metaDTO) throws URISyntaxException {
        log.debug("REST request to save Meta : {}", metaDTO);
        if (metaDTO.getId() != null) {
            throw new BadRequestAlertException("A new meta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaDTO result = metaService.save(metaDTO);
        return ResponseEntity
            .created(new URI("/api/metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /metas/:id} : Updates an existing meta.
     *
     * @param id the id of the metaDTO to save.
     * @param metaDTO the metaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaDTO,
     * or with status {@code 400 (Bad Request)} if the metaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/metas/{id}")
    public ResponseEntity<MetaDTO> updateMeta(@PathVariable(value = "id", required = false) final Long id, @RequestBody MetaDTO metaDTO)
        throws URISyntaxException {
        log.debug("REST request to update Meta : {}, {}", id, metaDTO);
        if (metaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaDTO result = metaService.update(metaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /metas/:id} : Partial updates given fields of an existing meta, field will ignore if it is null
     *
     * @param id the id of the metaDTO to save.
     * @param metaDTO the metaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaDTO,
     * or with status {@code 400 (Bad Request)} if the metaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/metas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetaDTO> partialUpdateMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaDTO metaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Meta partially : {}, {}", id, metaDTO);
        if (metaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaDTO> result = metaService.partialUpdate(metaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /metas} : get all the metas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metas in body.
     */
    @GetMapping("/metas")
    public ResponseEntity<List<MetaDTO>> getAllMetas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Metas");
        Page<MetaDTO> page = metaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /metas/:id} : get the "id" meta.
     *
     * @param id the id of the metaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/metas/{id}")
    public ResponseEntity<MetaDTO> getMeta(@PathVariable Long id) {
        log.debug("REST request to get Meta : {}", id);
        Optional<MetaDTO> metaDTO = metaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaDTO);
    }

    /**
     * {@code DELETE  /metas/:id} : delete the "id" meta.
     *
     * @param id the id of the metaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/metas/{id}")
    public ResponseEntity<Void> deleteMeta(@PathVariable Long id) {
        log.debug("REST request to delete Meta : {}", id);
        metaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
