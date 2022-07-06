package com.hae.service.mapper;

import com.hae.domain.Picture;
import com.hae.domain.Station;
import com.hae.service.dto.PictureDTO;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Picture} and its DTO {@link PictureDTO}.
 */
@Mapper(componentModel = "spring")
public interface PictureMapper extends EntityMapper<PictureDTO, Picture> {
    @Mapping(target = "station", source = "station", qualifiedByName = "stationId")
    PictureDTO toDto(Picture s);

    @Named("stationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StationDTO toDtoStationId(Station station);
}
