package com.hae.service.mapper;

import com.hae.domain.Evse;
import com.hae.domain.Station;
import com.hae.service.dto.EvseDTO;
import com.hae.service.dto.StationDTO;
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
