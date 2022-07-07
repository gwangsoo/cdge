package com.hae.service.mapper;

import com.hae.domain.Connector;
import com.hae.domain.Evse;
import com.hae.service.dto.ConnectorDTO;
import com.hae.service.dto.EvseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Connector} and its DTO {@link ConnectorDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectorMapper extends EntityMapper<ConnectorDTO, Connector> {}
