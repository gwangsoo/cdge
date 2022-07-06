package com.hae.service.mapper;

import com.hae.domain.Connector;
import com.hae.domain.Evse;
import com.hae.service.dto.ConnectorDTO;
import com.hae.service.dto.EvseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Connector} and its DTO {@link ConnectorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConnectorMapper extends EntityMapper<ConnectorDTO, Connector> {
    @Mapping(target = "evse", source = "evse", qualifiedByName = "evseId")
    ConnectorDTO toDto(Connector s);

    @Named("evseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EvseDTO toDtoEvseId(Evse evse);
}
