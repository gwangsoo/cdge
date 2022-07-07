package com.hae.service.mapper;

import com.hae.domain.Evse;
import com.hae.domain.Station;
import com.hae.service.dto.EvseDTO;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Mapper for the entity {@link Evse} and its DTO {@link EvseDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ConnectorMapper.class})
public abstract class  EvseMapper implements EntityMapper<EvseDTO, Evse> {
    @BeforeMapping
    protected void autoFillId(EvseDTO d) {
        if(d == null) return;

        if(d.getConnectors() != null) {
            d.getConnectors().forEach(connectorDTO -> {
                connectorDTO.setEvseId(d.getId());
            });
        }
    }
}
