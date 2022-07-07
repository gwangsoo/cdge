package com.hae.service.mapper;

import com.hae.domain.Evse;
import com.hae.domain.Station;
import com.hae.service.dto.EvseDTO;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evse} and its DTO {@link EvseDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvseMapper extends EntityMapper<EvseDTO, Evse> {}
