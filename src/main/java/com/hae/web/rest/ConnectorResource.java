package com.hae.web.rest;

import com.hae.repository.ConnectorRepository;
import com.hae.service.ConnectorService;
import com.hae.service.dto.ConnectorDTO;
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
 * REST controller for managing {@link com.hae.domain.Connector}.
 */
@RestController
@RequestMapping("/api")
public class ConnectorResource {

    private final Logger log = LoggerFactory.getLogger(ConnectorResource.class);

    private static final String ENTITY_NAME = "connector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectorService connectorService;

    private final ConnectorRepository connectorRepository;

    public ConnectorResource(ConnectorService connectorService, ConnectorRepository connectorRepository) {
        this.connectorService = connectorService;
        this.connectorRepository = connectorRepository;
    }

    /**
     * {@code POST  /connectors} : Create a new connector.
     *
     * @param connectorDTO the connectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectorDTO, or with status {@code 400 (Bad Request)} if the connector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connectors")
    public ResponseEntity<ConnectorDTO> createConnector(@Valid @RequestBody ConnectorDTO connectorDTO) throws URISyntaxException {
        log.debug("REST request to save Connector : {}", connectorDTO);
        if (connectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new connector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectorDTO result = connectorService.save(connectorDTO);
        return ResponseEntity
            .created(new URI("/api/connectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connectors/:id} : Updates an existing connector.
     *
     * @param id the id of the connectorDTO to save.
     * @param connectorDTO the connectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectorDTO,
     * or with status {@code 400 (Bad Request)} if the connectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connectors/{id}")
    public ResponseEntity<ConnectorDTO> updateConnector(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConnectorDTO connectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Connector : {}, {}", id, connectorDTO);
        if (connectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, connectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!connectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConnectorDTO result = connectorService.update(connectorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /connectors/:id} : Partial updates given fields of an existing connector, field will ignore if it is null
     *
     * @param id the id of the connectorDTO to save.
     * @param connectorDTO the connectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectorDTO,
     * or with status {@code 400 (Bad Request)} if the connectorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the connectorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the connectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/connectors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConnectorDTO> partialUpdateConnector(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConnectorDTO connectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Connector partially : {}, {}", id, connectorDTO);
        if (connectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, connectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!connectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConnectorDTO> result = connectorService.partialUpdate(connectorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /connectors} : get all the connectors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectors in body.
     */
    @GetMapping("/connectors")
    public ResponseEntity<List<ConnectorDTO>> getAllConnectors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Connectors");
        Page<ConnectorDTO> page = connectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connectors/:id} : get the "id" connector.
     *
     * @param id the id of the connectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connectors/{id}")
    public ResponseEntity<ConnectorDTO> getConnector(@PathVariable Long id) {
        log.debug("REST request to get Connector : {}", id);
        Optional<ConnectorDTO> connectorDTO = connectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectorDTO);
    }

    /**
     * {@code DELETE  /connectors/:id} : delete the "id" connector.
     *
     * @param id the id of the connectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connectors/{id}")
    public ResponseEntity<Void> deleteConnector(@PathVariable Long id) {
        log.debug("REST request to delete Connector : {}", id);
        connectorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
