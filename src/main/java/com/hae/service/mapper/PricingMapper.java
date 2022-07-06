package com.hae.service.mapper;

import com.hae.domain.Evse;
import com.hae.domain.Pricing;
import com.hae.service.dto.EvseDTO;
import com.hae.service.dto.PricingDTO;
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
