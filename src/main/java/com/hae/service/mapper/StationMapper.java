package com.hae.service.mapper;

import com.hae.domain.Station;
import com.hae.service.dto.EvseDTO;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Station} and its DTO {@link StationDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EvseMapper.class, ChargerMapper.class})
public abstract class StationMapper implements EntityMapper<StationDTO, Station> {
    @BeforeMapping
    protected void autoFillId(StationDTO d) {
        if(d == null) return;

        if(d.getEvses() != null) {
            d.getEvses().forEach(evseDTO -> {
                evseDTO.setStationId(d.getId());
            });
        }

        if(d.getChargers() != null) {
            d.getChargers().forEach(chargerDTO -> {
                chargerDTO.setStationId(d.getId());
            });
        }
    }
}
