package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot;
import com.hae.ecs.domain.charging.service.dto.ChargingFavoritesSpotDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChargingFavoritesSpot} and its DTO {@link ChargingFavoritesSpotDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChargingFavoritesSpotMapper extends EntityMapper<ChargingFavoritesSpotDTO, ChargingFavoritesSpot> {}
