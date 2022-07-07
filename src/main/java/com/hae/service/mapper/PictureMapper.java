package com.hae.service.mapper;

import com.hae.domain.Picture;
import com.hae.domain.Station;
import com.hae.service.dto.PictureDTO;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Picture} and its DTO {@link PictureDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper extends EntityMapper<PictureDTO, Picture> {}
