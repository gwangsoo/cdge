package com.hae.ecs.domain.charging.service;

import com.hae.ecs.domain.charging.service.dto.ChargingUseDisplayDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.ecs.domain.charging.domain.ChargingUseDisplay}.
 */
public interface ChargingUseDisplayService {
    /**
     * Save a chargingUseDisplay.
     *
     * @param chargingUseDisplayDTO the entity to save.
     * @return the persisted entity.
     */
    ChargingUseDisplayDTO save(ChargingUseDisplayDTO chargingUseDisplayDTO);

    /**
     * Updates a chargingUseDisplay.
     *
     * @param chargingUseDisplayDTO the entity to update.
     * @return the persisted entity.
     */
    ChargingUseDisplayDTO update(ChargingUseDisplayDTO chargingUseDisplayDTO);

    /**
     * Partially updates a chargingUseDisplay.
     *
     * @param chargingUseDisplayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChargingUseDisplayDTO> partialUpdate(ChargingUseDisplayDTO chargingUseDisplayDTO);

    /**
     * Get all the chargingUseDisplays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChargingUseDisplayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" chargingUseDisplay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChargingUseDisplayDTO> findOne(Long id);

    /**
     * Delete the "id" chargingUseDisplay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
