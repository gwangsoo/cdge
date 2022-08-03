package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Evse;
import com.hae.ecs.domain.charging.domain.Station;
import com.hae.ecs.domain.charging.service.dto.EvseDTO;
import com.hae.ecs.domain.charging.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evse} and its DTO {@link EvseDTO}.
 */
@Mapper(componentModel = "spring")
public interface EvseMapper extends EntityMapper<EvseDTO, Evse> {
    @Mapping(target = "station", source = "station", qualifiedByName = "stationId")
    EvseDTO toDto(Evse s);

    @Named("stationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StationDTO toDtoStationId(Station station);
}
