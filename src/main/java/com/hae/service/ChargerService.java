package com.hae.service;

import com.hae.service.dto.ChargerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.domain.Charger}.
 */
public interface ChargerService {
    /**
     * Save a charger.
     *
     * @param chargerDTO the entity to save.
     * @return the persisted entity.
     */
    ChargerDTO save(ChargerDTO chargerDTO);

    /**
     * Updates a charger.
     *
     * @param chargerDTO the entity to update.
     * @return the persisted entity.
     */
    ChargerDTO update(ChargerDTO chargerDTO);

    /**
     * Partially updates a charger.
     *
     * @param chargerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChargerDTO> partialUpdate(ChargerDTO chargerDTO);

    /**
     * Get all the chargers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChargerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" charger.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChargerDTO> findOne(Long id);

    /**
     * Delete the "id" charger.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
