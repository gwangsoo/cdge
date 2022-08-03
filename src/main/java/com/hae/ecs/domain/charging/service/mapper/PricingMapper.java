package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Evse;
import com.hae.ecs.domain.charging.domain.Pricing;
import com.hae.ecs.domain.charging.service.dto.EvseDTO;
import com.hae.ecs.domain.charging.service.dto.PricingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pricing} and its DTO {@link PricingDTO}.
 */
@Mapper(componentModel = "spring")
public interface PricingMapper extends EntityMapper<PricingDTO, Pricing> {
    @Mapping(target = "evse", source = "evse", qualifiedByName = "evseId")
    PricingDTO toDto(Pricing s);

    @Named("evseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EvseDTO toDtoEvseId(Evse evse);
}
