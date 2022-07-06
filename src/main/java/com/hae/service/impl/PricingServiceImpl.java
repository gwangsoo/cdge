package com.hae.service.impl;

import com.hae.domain.Pricing;
import com.hae.repository.PricingRepository;
import com.hae.service.PricingService;
import com.hae.service.dto.PricingDTO;
import com.hae.service.mapper.PricingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pricing}.
 */
@Service
@Transactional
public class PricingServiceImpl implements PricingService {

    private final Logger log = LoggerFactory.getLogger(PricingServiceImpl.class);

    private final PricingRepository pricingRepository;

    private final PricingMapper pricingMapper;

    public PricingServiceImpl(PricingRepository pricingRepository, PricingMapper pricingMapper) {
        this.pricingRepository = pricingRepository;
        this.pricingMapper = pricingMapper;
    }

    @Override
    public PricingDTO save(PricingDTO pricingDTO) {
        log.debug("Request to save Pricing : {}", pricingDTO);
        Pricing pricing = pricingMapper.toEntity(pricingDTO);
        pricing = pricingRepository.save(pricing);
        return pricingMapper.toDto(pricing);
    }

    @Override
    public PricingDTO update(PricingDTO pricingDTO) {
        log.debug("Request to save Pricing : {}", pricingDTO);
        Pricing pricing = pricingMapper.toEntity(pricingDTO);
        pricing = pricingRepository.save(pricing);
        return pricingMapper.toDto(pricing);
    }

    @Override
    public Optional<PricingDTO> partialUpdate(PricingDTO pricingDTO) {
        log.debug("Request to partially update Pricing : {}", pricingDTO);

        return pricingRepository
            .findById(pricingDTO.getId())
            .map(existingPricing -> {
                pricingMapper.partialUpdate(existingPricing, pricingDTO);

                return existingPricing;
            })
            .map(pricingRepository::save)
            .map(pricingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PricingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pricings");
        return pricingRepository.findAll(pageable).map(pricingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PricingDTO> findOne(Long id) {
        log.debug("Request to get Pricing : {}", id);
        return pricingRepository.findById(id).map(pricingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pricing : {}", id);
        pricingRepository.deleteById(id);
    }
}
