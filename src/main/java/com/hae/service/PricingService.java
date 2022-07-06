package com.hae.service;

import com.hae.service.dto.PricingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.domain.Pricing}.
 */
public interface PricingService {
    /**
     * Save a pricing.
     *
     * @param pricingDTO the entity to save.
     * @return the persisted entity.
     */
    PricingDTO save(PricingDTO pricingDTO);

    /**
     * Updates a pricing.
     *
     * @param pricingDTO the entity to update.
     * @return the persisted entity.
     */
    PricingDTO update(PricingDTO pricingDTO);

    /**
     * Partially updates a pricing.
     *
     * @param pricingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PricingDTO> partialUpdate(PricingDTO pricingDTO);

    /**
     * Get all the pricings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PricingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pricing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PricingDTO> findOne(Long id);

    /**
     * Delete the "id" pricing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
