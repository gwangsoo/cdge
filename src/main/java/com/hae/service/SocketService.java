package com.hae.service;

import com.hae.service.dto.SocketDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hae.domain.Socket}.
 */
public interface SocketService {
    /**
     * Save a socket.
     *
     * @param socketDTO the entity to save.
     * @return the persisted entity.
     */
    SocketDTO save(SocketDTO socketDTO);

    /**
     * Updates a socket.
     *
     * @param socketDTO the entity to update.
     * @return the persisted entity.
     */
    SocketDTO update(SocketDTO socketDTO);

    /**
     * Partially updates a socket.
     *
     * @param socketDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocketDTO> partialUpdate(SocketDTO socketDTO);

    /**
     * Get all the sockets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocketDTO> findAll(Pageable pageable);

    /**
     * Get the "id" socket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocketDTO> findOne(Long id);

    /**
     * Delete the "id" socket.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
