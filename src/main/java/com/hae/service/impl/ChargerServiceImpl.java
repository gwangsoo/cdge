package com.hae.service.impl;

import com.hae.domain.Charger;
import com.hae.repository.ChargerRepository;
import com.hae.service.ChargerService;
import com.hae.service.dto.ChargerDTO;
import com.hae.service.mapper.ChargerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Charger}.
 */
@Service
@Transactional
public class ChargerServiceImpl implements ChargerService {

    private final Logger log = LoggerFactory.getLogger(ChargerServiceImpl.class);

    private final ChargerRepository chargerRepository;

    private final ChargerMapper chargerMapper;

    public ChargerServiceImpl(ChargerRepository chargerRepository, ChargerMapper chargerMapper) {
        this.chargerRepository = chargerRepository;
        this.chargerMapper = chargerMapper;
    }

    @Override
    public ChargerDTO save(ChargerDTO chargerDTO) {
        log.debug("Request to save Charger : {}", chargerDTO);
        Charger charger = chargerMapper.toEntity(chargerDTO);
        charger = chargerRepository.save(charger);
        return chargerMapper.toDto(charger);
    }

    @Override
    public ChargerDTO update(ChargerDTO chargerDTO) {
        log.debug("Request to save Charger : {}", chargerDTO);
        Charger charger = chargerMapper.toEntity(chargerDTO);
        charger = chargerRepository.save(charger);
        return chargerMapper.toDto(charger);
    }

    @Override
    public Optional<ChargerDTO> partialUpdate(ChargerDTO chargerDTO) {
        log.debug("Request to partially update Charger : {}", chargerDTO);

        return chargerRepository
            .findById(chargerDTO.getId())
            .map(existingCharger -> {
                chargerMapper.partialUpdate(existingCharger, chargerDTO);

                return existingCharger;
            })
            .map(chargerRepository::save)
            .map(chargerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChargerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chargers");
        return chargerRepository.findAll(pageable).map(chargerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChargerDTO> findOne(Long id) {
        log.debug("Request to get Charger : {}", id);
        return chargerRepository.findById(id).map(chargerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Charger : {}", id);
        chargerRepository.deleteById(id);
    }
}
