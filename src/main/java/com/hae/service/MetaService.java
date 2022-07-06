package com.hae.service;

import com.hae.service.dto.MetaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.domain.Meta}.
 */
public interface MetaService {
    /**
     * Save a meta.
     *
     * @param metaDTO the entity to save.
     * @return the persisted entity.
     */
    MetaDTO save(MetaDTO metaDTO);

    /**
     * Updates a meta.
     *
     * @param metaDTO the entity to update.
     * @return the persisted entity.
     */
    MetaDTO update(MetaDTO metaDTO);

    /**
     * Partially updates a meta.
     *
     * @param metaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MetaDTO> partialUpdate(MetaDTO metaDTO);

    /**
     * Get all the metas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MetaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" meta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MetaDTO> findOne(Long id);

    /**
     * Delete the "id" meta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
