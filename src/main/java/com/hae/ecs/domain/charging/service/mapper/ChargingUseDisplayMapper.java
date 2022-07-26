package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.ChargingUseDisplay;
import com.hae.ecs.domain.charging.service.dto.ChargingUseDisplayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChargingUseDisplay} and its DTO {@link ChargingUseDisplayDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChargingUseDisplayMapper extends EntityMapper<ChargingUseDisplayDTO, ChargingUseDisplay> {}
