package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.ChargingUseHistory;
import com.hae.ecs.domain.charging.service.dto.ChargingUseHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChargingUseHistory} and its DTO {@link ChargingUseHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChargingUseHistoryMapper extends EntityMapper<ChargingUseHistoryDTO, ChargingUseHistory> {}
