package com.hae.ecs.domain.charging.service.mapper;

import com.hae.ecs.domain.charging.domain.Meta;
import com.hae.ecs.domain.charging.domain.Socket;
import com.hae.ecs.domain.charging.service.dto.MetaDTO;
import com.hae.ecs.domain.charging.service.dto.SocketDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Socket} and its DTO {@link SocketDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocketMapper extends EntityMapper<SocketDTO, Socket> {
    @Mapping(target = "meta", source = "meta", qualifiedByName = "metaId")
    SocketDTO toDto(Socket s);

    @Named("metaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaDTO toDtoMetaId(Meta meta);
}
