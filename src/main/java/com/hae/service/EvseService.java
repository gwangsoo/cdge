package com.hae.service;

import com.hae.service.dto.EvseDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.domain.Evse}.
 */
public interface EvseService {
    /**
     * Save a evse.
     *
     * @param evseDTO the entity to save.
     * @return the persisted entity.
     */
    EvseDTO save(EvseDTO evseDTO);

    /**
     * Updates a evse.
     *
     * @param evseDTO the entity to update.
     * @return the persisted entity.
     */
    EvseDTO update(EvseDTO evseDTO);

    /**
     * Partially updates a evse.
     *
     * @param evseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvseDTO> partialUpdate(EvseDTO evseDTO);

    /**
     * Get all the evses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvseDTO> findOne(Long id);

    /**
     * Delete the "id" evse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
