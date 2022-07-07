package com.hae.service.mapper;

import com.hae.domain.Evse;
import com.hae.domain.Pricing;
import com.hae.service.dto.EvseDTO;
import com.hae.service.dto.PricingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pricing} and its DTO {@link PricingDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PricingMapper extends EntityMapper<PricingDTO, Pricing> {}
