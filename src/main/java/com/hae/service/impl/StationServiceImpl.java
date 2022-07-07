package com.hae.service.impl;

import com.hae.domain.Station;
import com.hae.repository.StationRepository;
import com.hae.service.StationService;
import com.hae.service.dto.StationDTO;
import com.hae.service.mapper.StationMapper;
import java.util.Optional;

import com.hae.util.ObjectSetUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Station}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    @Override
    public StationDTO save(StationDTO stationDTO) {
        log.debug("Request to save Station : {}", stationDTO);
        Station station = stationMapper.toEntity(stationDTO);
        station = stationRepository.save(station);
        return stationMapper.toDto(station);
    }

    @Override
    public StationDTO update(StationDTO stationDTO) {
        log.debug("Request to save Station : {}", stationDTO);
        Station station = stationMapper.toEntity(stationDTO);
        station = stationRepository.save(station);
        return stationMapper.toDto(station);
    }

    @Override
    public Optional<StationDTO> partialUpdate(StationDTO stationDTO) {
        log.debug("Request to partially update Station : {}", stationDTO);

        Station station = stationMapper.toEntity(stationDTO);

        return stationRepository
            .findById(stationDTO.getId())
            .map(existingStation -> {
                //stationMapper.partialUpdate(existingStation, stationDTO);
                ObjectSetUtil.copyProperties(station, existingStation, null, ObjectSetUtil.getNullPropertyNames(existingStation));
                return existingStation;
            })
            .map(stationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stations");
        return stationRepository.findAll(pageable).map(stationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationDTO> findOne(Long id) {
        log.debug("Request to get Station : {}", id);
        return stationRepository.findById(id).map(stationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Station : {}", id);
        stationRepository.deleteById(id);
    }
}
