package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Charger;
import com.hae.ecs.domain.charging.domain.Meta;
import com.hae.ecs.domain.charging.domain.Station;
import com.hae.ecs.domain.charging.service.dto.ChargerDTO;
import com.hae.ecs.domain.charging.service.dto.MetaDTO;
import com.hae.ecs.domain.charging.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Charger} and its DTO {@link ChargerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChargerMapper extends EntityMapper<ChargerDTO, Charger> {
    @Mapping(target = "meta", source = "meta", qualifiedByName = "metaId")
    @Mapping(target = "station", source = "station", qualifiedByName = "stationId")
    ChargerDTO toDto(Charger s);

    @Named("metaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaDTO toDtoMetaId(Meta meta);

    @Named("stationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StationDTO toDtoStationId(Station station);
}
