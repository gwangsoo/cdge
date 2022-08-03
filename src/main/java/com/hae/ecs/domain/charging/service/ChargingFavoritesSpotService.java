package com.hae.ecs.domain.charging.service;

import com.hae.ecs.domain.charging.service.dto.ChargingFavoritesSpotDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot}.
 */
public interface ChargingFavoritesSpotService {
    /**
     * Save a chargingFavoritesSpot.
     *
     * @param chargingFavoritesSpotDTO the entity to save.
     * @return the persisted entity.
     */
    ChargingFavoritesSpotDTO save(ChargingFavoritesSpotDTO chargingFavoritesSpotDTO);

    /**
     * Updates a chargingFavoritesSpot.
     *
     * @param chargingFavoritesSpotDTO the entity to update.
     * @return the persisted entity.
     */
    ChargingFavoritesSpotDTO update(ChargingFavoritesSpotDTO chargingFavoritesSpotDTO);

    /**
     * Partially updates a chargingFavoritesSpot.
     *
     * @param chargingFavoritesSpotDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChargingFavoritesSpotDTO> partialUpdate(ChargingFavoritesSpotDTO chargingFavoritesSpotDTO);

    /**
     * Get all the chargingFavoritesSpots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChargingFavoritesSpotDTO> findAll(Pageable pageable);

    /**
     * Get the "id" chargingFavoritesSpot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChargingFavoritesSpotDTO> findOne(Long id);

    /**
     * Delete the "id" chargingFavoritesSpot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
