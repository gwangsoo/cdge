package com.hae.service.mapper;

import com.hae.domain.Charger;
import com.hae.domain.Customer;
import com.hae.service.dto.ChargerDTO;
import com.hae.service.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Charger} and its DTO {@link ChargerDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
