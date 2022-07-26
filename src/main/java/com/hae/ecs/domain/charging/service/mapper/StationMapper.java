package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Station;
import com.hae.ecs.domain.charging.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Station} and its DTO {@link StationDTO}.
 */
@Mapper(componentModel = "spring")
public interface StationMapper extends EntityMapper<StationDTO, Station> {}
