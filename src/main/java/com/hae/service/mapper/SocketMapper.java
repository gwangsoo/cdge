package com.hae.service.mapper;

import com.hae.domain.Meta;
import com.hae.domain.Socket;
import com.hae.service.dto.MetaDTO;
import com.hae.service.dto.SocketDTO;
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
