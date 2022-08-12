package com.hae.ecs.domain.charging.service.impl;

import com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot;
import com.hae.ecs.domain.charging.repository.ChargingFavoritesSpotRepository;
import com.hae.ecs.domain.charging.service.ChargingFavoritesSpotService;
import com.hae.ecs.domain.charging.service.dto.ChargingFavoritesSpotDTO;
import com.hae.ecs.domain.charging.service.mapper.ChargingFavoritesSpotMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChargingFavoritesSpot}.
 */
@Service
@Transactional
public class ChargingFavoritesSpotServiceImpl implements ChargingFavoritesSpotService {

    private final Logger log = LoggerFactory.getLogger(ChargingFavoritesSpotServiceImpl.class);

    private final ChargingFavoritesSpotRepository chargingFavoritesSpotRepository;

    private final ChargingFavoritesSpotMapper chargingFavoritesSpotMapper;

    public ChargingFavoritesSpotServiceImpl(
        ChargingFavoritesSpotRepository chargingFavoritesSpotRepository,
        ChargingFavoritesSpotMapper chargingFavoritesSpotMapper
    ) {
        this.chargingFavoritesSpotRepository = chargingFavoritesSpotRepository;
        this.chargingFavoritesSpotMapper = chargingFavoritesSpotMapper;
    }

    @Override
    public ChargingFavoritesSpotDTO save(ChargingFavoritesSpotDTO chargingFavoritesSpotDTO) {
        log.debug("Request to save ChargingFavoritesSpot : {}", chargingFavoritesSpotDTO);
        ChargingFavoritesSpot chargingFavoritesSpot = chargingFavoritesSpotMapper.toEntity(chargingFavoritesSpotDTO);
        chargingFavoritesSpot = chargingFavoritesSpotRepository.save(chargingFavoritesSpot);
        return chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);
    }

    @Override
    public ChargingFavoritesSpotDTO update(ChargingFavoritesSpotDTO chargingFavoritesSpotDTO) {
        log.debug("Request to save ChargingFavoritesSpot : {}", chargingFavoritesSpotDTO);
        ChargingFavoritesSpot chargingFavoritesSpot = chargingFavoritesSpotMapper.toEntity(chargingFavoritesSpotDTO);
        chargingFavoritesSpot = chargingFavoritesSpotRepository.save(chargingFavoritesSpot);
        return chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);
    }

    @Override
    public Optional<ChargingFavoritesSpotDTO> partialUpdate(ChargingFavoritesSpotDTO chargingFavoritesSpotDTO) {
        log.debug("Request to partially update ChargingFavoritesSpot : {}", chargingFavoritesSpotDTO);

        return chargingFavoritesSpotRepository
            .findById(chargingFavoritesSpotDTO.getId())
            .map(existingChargingFavoritesSpot -> {
                chargingFavoritesSpotMapper.partialUpdate(existingChargingFavoritesSpot, chargingFavoritesSpotDTO);

                return existingChargingFavoritesSpot;
            })
            .map(chargingFavoritesSpotRepository::save)
            .map(chargingFavoritesSpotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChargingFavoritesSpotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChargingFavoritesSpots");
        return chargingFavoritesSpotRepository.findAll(pageable).map(chargingFavoritesSpotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChargingFavoritesSpotDTO> findOne(Long id) {
        log.debug("Request to get ChargingFavoritesSpot : {}", id);
        return chargingFavoritesSpotRepository.findById(id).map(chargingFavoritesSpotMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChargingFavoritesSpot : {}", id);
        chargingFavoritesSpotRepository.deleteById(id);
    }
}
