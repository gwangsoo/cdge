package com.hae.service.impl;

import com.hae.domain.Meta;
import com.hae.repository.MetaRepository;
import com.hae.service.MetaService;
import com.hae.service.dto.MetaDTO;
import com.hae.service.mapper.MetaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Meta}.
 */
@Service
@Transactional
public class MetaServiceImpl implements MetaService {

    private final Logger log = LoggerFactory.getLogger(MetaServiceImpl.class);

    private final MetaRepository metaRepository;

    private final MetaMapper metaMapper;

    public MetaServiceImpl(MetaRepository metaRepository, MetaMapper metaMapper) {
        this.metaRepository = metaRepository;
        this.metaMapper = metaMapper;
    }

    @Override
    public MetaDTO save(MetaDTO metaDTO) {
        log.debug("Request to save Meta : {}", metaDTO);
        Meta meta = metaMapper.toEntity(metaDTO);
        meta = metaRepository.save(meta);
        return metaMapper.toDto(meta);
    }

    @Override
    public MetaDTO update(MetaDTO metaDTO) {
        log.debug("Request to save Meta : {}", metaDTO);
        Meta meta = metaMapper.toEntity(metaDTO);
        meta = metaRepository.save(meta);
        return metaMapper.toDto(meta);
    }

    @Override
    public Optional<MetaDTO> partialUpdate(MetaDTO metaDTO) {
        log.debug("Request to partially update Meta : {}", metaDTO);

        return metaRepository
            .findById(metaDTO.getId())
            .map(existingMeta -> {
                metaMapper.partialUpdate(existingMeta, metaDTO);

                return existingMeta;
            })
            .map(metaRepository::save)
            .map(metaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MetaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Metas");
        return metaRepository.findAll(pageable).map(metaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetaDTO> findOne(Long id) {
        log.debug("Request to get Meta : {}", id);
        return metaRepository.findById(id).map(metaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Meta : {}", id);
        metaRepository.deleteById(id);
    }
}
