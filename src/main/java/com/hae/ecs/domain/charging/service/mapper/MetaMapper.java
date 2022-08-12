package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Meta;
import com.hae.ecs.domain.charging.service.dto.MetaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meta} and its DTO {@link MetaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaMapper extends EntityMapper<MetaDTO, Meta> {}
