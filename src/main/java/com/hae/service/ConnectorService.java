package com.hae.service;

import com.hae.service.dto.ConnectorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.domain.Connector}.
 */
public interface ConnectorService {
    /**
     * Save a connector.
     *
     * @param connectorDTO the entity to save.
     * @return the persisted entity.
     */
    ConnectorDTO save(ConnectorDTO connectorDTO);

    /**
     * Updates a connector.
     *
     * @param connectorDTO the entity to update.
     * @return the persisted entity.
     */
    ConnectorDTO update(ConnectorDTO connectorDTO);

    /**
     * Partially updates a connector.
     *
     * @param connectorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConnectorDTO> partialUpdate(ConnectorDTO connectorDTO);

    /**
     * Get all the connectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConnectorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" connector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConnectorDTO> findOne(Long id);

    /**
     * Delete the "id" connector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
