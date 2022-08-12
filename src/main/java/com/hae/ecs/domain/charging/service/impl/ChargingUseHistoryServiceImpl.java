package com.hae.ecs.domain.charging.service.impl;

import com.hae.ecs.domain.charging.domain.ChargingUseHistory;
import com.hae.ecs.domain.charging.repository.ChargingUseHistoryRepository;
import com.hae.ecs.domain.charging.service.ChargingUseHistoryService;
import com.hae.ecs.domain.charging.service.dto.ChargingUseHistoryDTO;
import com.hae.ecs.domain.charging.service.mapper.ChargingUseHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChargingUseHistory}.
 */
@Service
@Transactional
public class ChargingUseHistoryServiceImpl implements ChargingUseHistoryService {

    private final Logger log = LoggerFactory.getLogger(ChargingUseHistoryServiceImpl.class);

    private final ChargingUseHistoryRepository chargingUseHistoryRepository;

    private final ChargingUseHistoryMapper chargingUseHistoryMapper;

    public ChargingUseHistoryServiceImpl(
        ChargingUseHistoryRepository chargingUseHistoryRepository,
        ChargingUseHistoryMapper chargingUseHistoryMapper
    ) {
        this.chargingUseHistoryRepository = chargingUseHistoryRepository;
        this.chargingUseHistoryMapper = chargingUseHistoryMapper;
    }

    @Override
    public ChargingUseHistoryDTO save(ChargingUseHistoryDTO chargingUseHistoryDTO) {
        log.debug("Request to save ChargingUseHistory : {}", chargingUseHistoryDTO);
        ChargingUseHistory chargingUseHistory = chargingUseHistoryMapper.toEntity(chargingUseHistoryDTO);
        chargingUseHistory = chargingUseHistoryRepository.save(chargingUseHistory);
        return chargingUseHistoryMapper.toDto(chargingUseHistory);
    }

    @Override
    public ChargingUseHistoryDTO update(ChargingUseHistoryDTO chargingUseHistoryDTO) {
        log.debug("Request to save ChargingUseHistory : {}", chargingUseHistoryDTO);
        ChargingUseHistory chargingUseHistory = chargingUseHistoryMapper.toEntity(chargingUseHistoryDTO);
        chargingUseHistory = chargingUseHistoryRepository.save(chargingUseHistory);
        return chargingUseHistoryMapper.toDto(chargingUseHistory);
    }

    @Override
    public Optional<ChargingUseHistoryDTO> partialUpdate(ChargingUseHistoryDTO chargingUseHistoryDTO) {
        log.debug("Request to partially update ChargingUseHistory : {}", chargingUseHistoryDTO);

        return chargingUseHistoryRepository
            .findById(chargingUseHistoryDTO.getId())
            .map(existingChargingUseHistory -> {
                chargingUseHistoryMapper.partialUpdate(existingChargingUseHistory, chargingUseHistoryDTO);

                return existingChargingUseHistory;
            })
            .map(chargingUseHistoryRepository::save)
            .map(chargingUseHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChargingUseHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChargingUseHistories");
        return chargingUseHistoryRepository.findAll(pageable).map(chargingUseHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChargingUseHistoryDTO> findOne(Long id) {
        log.debug("Request to get ChargingUseHistory : {}", id);
        return chargingUseHistoryRepository.findById(id).map(chargingUseHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChargingUseHistory : {}", id);
        chargingUseHistoryRepository.deleteById(id);
    }
}
