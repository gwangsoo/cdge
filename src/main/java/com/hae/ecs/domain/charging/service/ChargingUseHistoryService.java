package com.hae.ecs.domain.charging.service;

import com.hae.ecs.domain.charging.service.dto.ChargingUseHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.ecs.domain.charging.domain.ChargingUseHistory}.
 */
public interface ChargingUseHistoryService {
    /**
     * Save a chargingUseHistory.
     *
     * @param chargingUseHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ChargingUseHistoryDTO save(ChargingUseHistoryDTO chargingUseHistoryDTO);

    /**
     * Updates a chargingUseHistory.
     *
     * @param chargingUseHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    ChargingUseHistoryDTO update(ChargingUseHistoryDTO chargingUseHistoryDTO);

    /**
     * Partially updates a chargingUseHistory.
     *
     * @param chargingUseHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChargingUseHistoryDTO> partialUpdate(ChargingUseHistoryDTO chargingUseHistoryDTO);

    /**
     * Get all the chargingUseHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChargingUseHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" chargingUseHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChargingUseHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" chargingUseHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
