package com.hae.service.mapper;

import com.hae.domain.Station;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Station} and its DTO {@link StationDTO}.
 */
@Mapper(componentModel = "spring")
public interface StationMapper extends EntityMapper<StationDTO, Station> {}
