package com.hae.service.mapper;

import com.hae.domain.Charger;
import com.hae.service.dto.ChargerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Charger} and its DTO {@link ChargerDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargerMapper extends EntityMapper<ChargerDTO, Charger> {}
