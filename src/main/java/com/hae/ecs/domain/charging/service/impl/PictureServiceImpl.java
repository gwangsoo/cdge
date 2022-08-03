package com.hae.ecs.domain.charging.service.impl;

import com.hae.ecs.domain.charging.domain.Picture;
import com.hae.ecs.domain.charging.repository.PictureRepository;
import com.hae.ecs.domain.charging.service.PictureService;
import com.hae.ecs.domain.charging.service.dto.PictureDTO;
import com.hae.ecs.domain.charging.service.mapper.PictureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Picture}.
 */
@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final Logger log = LoggerFactory.getLogger(PictureServiceImpl.class);

    private final PictureRepository pictureRepository;

    private final PictureMapper pictureMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    @Override
    public PictureDTO save(PictureDTO pictureDTO) {
        log.debug("Request to save Picture : {}", pictureDTO);
        Picture picture = pictureMapper.toEntity(pictureDTO);
        picture = pictureRepository.save(picture);
        return pictureMapper.toDto(picture);
    }

    @Override
    public PictureDTO update(PictureDTO pictureDTO) {
        log.debug("Request to save Picture : {}", pictureDTO);
        Picture picture = pictureMapper.toEntity(pictureDTO);
        picture = pictureRepository.save(picture);
        return pictureMapper.toDto(picture);
    }

    @Override
    public Optional<PictureDTO> partialUpdate(PictureDTO pictureDTO) {
        log.debug("Request to partially update Picture : {}", pictureDTO);

        return pictureRepository
            .findById(pictureDTO.getId())
            .map(existingPicture -> {
                pictureMapper.partialUpdate(existingPicture, pictureDTO);

                return existingPicture;
            })
            .map(pictureRepository::save)
            .map(pictureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PictureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pictures");
        return pictureRepository.findAll(pageable).map(pictureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PictureDTO> findOne(Long id) {
        log.debug("Request to get Picture : {}", id);
        return pictureRepository.findById(id).map(pictureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Picture : {}", id);
        pictureRepository.deleteById(id);
    }
}
