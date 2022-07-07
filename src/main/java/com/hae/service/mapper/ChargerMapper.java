package com.hae.service.mapper;

import com.hae.domain.Charger;
import com.hae.domain.Meta;
import com.hae.domain.Station;
import com.hae.service.dto.ChargerDTO;
import com.hae.service.dto.MetaDTO;
import com.hae.service.dto.StationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Charger} and its DTO {@link ChargerDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargerMapper extends EntityMapper<ChargerDTO, Charger> {}
