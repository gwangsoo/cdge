package com.hae.service.impl;

import com.hae.domain.Evse;
import com.hae.repository.EvseRepository;
import com.hae.service.EvseService;
import com.hae.service.dto.EvseDTO;
import com.hae.service.mapper.EvseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Evse}.
 */
@Service
@Transactional
public class EvseServiceImpl implements EvseService {

    private final Logger log = LoggerFactory.getLogger(EvseServiceImpl.class);

    private final EvseRepository evseRepository;

    private final EvseMapper evseMapper;

    public EvseServiceImpl(EvseRepository evseRepository, EvseMapper evseMapper) {
        this.evseRepository = evseRepository;
        this.evseMapper = evseMapper;
    }

    @Override
    public EvseDTO save(EvseDTO evseDTO) {
        log.debug("Request to save Evse : {}", evseDTO);
        Evse evse = evseMapper.toEntity(evseDTO);
        evse = evseRepository.save(evse);
        return evseMapper.toDto(evse);
    }

    @Override
    public EvseDTO update(EvseDTO evseDTO) {
        log.debug("Request to save Evse : {}", evseDTO);
        Evse evse = evseMapper.toEntity(evseDTO);
        evse = evseRepository.save(evse);
        return evseMapper.toDto(evse);
    }

    @Override
    public Optional<EvseDTO> partialUpdate(EvseDTO evseDTO) {
        log.debug("Request to partially update Evse : {}", evseDTO);

        return evseRepository
            .findById(evseDTO.getId())
            .map(existingEvse -> {
                evseMapper.partialUpdate(existingEvse, evseDTO);

                return existingEvse;
            })
            .map(evseRepository::save)
            .map(evseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Evses");
        return evseRepository.findAll(pageable).map(evseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvseDTO> findOne(Long id) {
        log.debug("Request to get Evse : {}", id);
        return evseRepository.findById(id).map(evseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evse : {}", id);
        evseRepository.deleteById(id);
    }
}
