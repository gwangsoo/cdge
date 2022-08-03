package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Picture;
import com.hae.ecs.domain.charging.domain.Station;
import com.hae.ecs.domain.charging.service.dto.PictureDTO;
import com.hae.ecs.domain.charging.service.dto.StationDTO;
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
