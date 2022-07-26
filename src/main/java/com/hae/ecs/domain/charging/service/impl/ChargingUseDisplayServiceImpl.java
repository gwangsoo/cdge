package com.hae.ecs.domain.charging.service.impl;

import com.hae.ecs.domain.charging.domain.ChargingUseDisplay;
import com.hae.ecs.domain.charging.repository.ChargingUseDisplayRepository;
import com.hae.ecs.domain.charging.service.ChargingUseDisplayService;
import com.hae.ecs.domain.charging.service.dto.ChargingUseDisplayDTO;
import com.hae.ecs.domain.charging.service.mapper.ChargingUseDisplayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChargingUseDisplay}.
 */
@Service
@Transactional
public class ChargingUseDisplayServiceImpl implements ChargingUseDisplayService {

    private final Logger log = LoggerFactory.getLogger(ChargingUseDisplayServiceImpl.class);

    private final ChargingUseDisplayRepository chargingUseDisplayRepository;

    private final ChargingUseDisplayMapper chargingUseDisplayMapper;

    public ChargingUseDisplayServiceImpl(
        ChargingUseDisplayRepository chargingUseDisplayRepository,
        ChargingUseDisplayMapper chargingUseDisplayMapper
    ) {
        this.chargingUseDisplayRepository = chargingUseDisplayRepository;
        this.chargingUseDisplayMapper = chargingUseDisplayMapper;
    }

    @Override
    public ChargingUseDisplayDTO save(ChargingUseDisplayDTO chargingUseDisplayDTO) {
        log.debug("Request to save ChargingUseDisplay : {}", chargingUseDisplayDTO);
        ChargingUseDisplay chargingUseDisplay = chargingUseDisplayMapper.toEntity(chargingUseDisplayDTO);
        chargingUseDisplay = chargingUseDisplayRepository.save(chargingUseDisplay);
        return chargingUseDisplayMapper.toDto(chargingUseDisplay);
    }

    @Override
    public ChargingUseDisplayDTO update(ChargingUseDisplayDTO chargingUseDisplayDTO) {
        log.debug("Request to save ChargingUseDisplay : {}", chargingUseDisplayDTO);
        ChargingUseDisplay chargingUseDisplay = chargingUseDisplayMapper.toEntity(chargingUseDisplayDTO);
        chargingUseDisplay = chargingUseDisplayRepository.save(chargingUseDisplay);
        return chargingUseDisplayMapper.toDto(chargingUseDisplay);
    }

    @Override
    public Optional<ChargingUseDisplayDTO> partialUpdate(ChargingUseDisplayDTO chargingUseDisplayDTO) {
        log.debug("Request to partially update ChargingUseDisplay : {}", chargingUseDisplayDTO);

        return chargingUseDisplayRepository
            .findById(chargingUseDisplayDTO.getId())
            .map(existingChargingUseDisplay -> {
                chargingUseDisplayMapper.partialUpdate(existingChargingUseDisplay, chargingUseDisplayDTO);

                return existingChargingUseDisplay;
            })
            .map(chargingUseDisplayRepository::save)
            .map(chargingUseDisplayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChargingUseDisplayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChargingUseDisplays");
        return chargingUseDisplayRepository.findAll(pageable).map(chargingUseDisplayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChargingUseDisplayDTO> findOne(Long id) {
        log.debug("Request to get ChargingUseDisplay : {}", id);
        return chargingUseDisplayRepository.findById(id).map(chargingUseDisplayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChargingUseDisplay : {}", id);
        chargingUseDisplayRepository.deleteById(id);
    }
}
