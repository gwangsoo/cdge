package com.hae.service.impl;

import com.hae.domain.Connector;
import com.hae.repository.ConnectorRepository;
import com.hae.service.ConnectorService;
import com.hae.service.dto.ConnectorDTO;
import com.hae.service.mapper.ConnectorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Connector}.
 */
@Service
@Transactional
public class ConnectorServiceImpl implements ConnectorService {

    private final Logger log = LoggerFactory.getLogger(ConnectorServiceImpl.class);

    private final ConnectorRepository connectorRepository;

    private final ConnectorMapper connectorMapper;

    public ConnectorServiceImpl(ConnectorRepository connectorRepository, ConnectorMapper connectorMapper) {
        this.connectorRepository = connectorRepository;
        this.connectorMapper = connectorMapper;
    }

    @Override
    public ConnectorDTO save(ConnectorDTO connectorDTO) {
        log.debug("Request to save Connector : {}", connectorDTO);
        Connector connector = connectorMapper.toEntity(connectorDTO);
        connector = connectorRepository.save(connector);
        return connectorMapper.toDto(connector);
    }

    @Override
    public ConnectorDTO update(ConnectorDTO connectorDTO) {
        log.debug("Request to save Connector : {}", connectorDTO);
        Connector connector = connectorMapper.toEntity(connectorDTO);
        connector = connectorRepository.save(connector);
        return connectorMapper.toDto(connector);
    }

    @Override
    public Optional<ConnectorDTO> partialUpdate(ConnectorDTO connectorDTO) {
        log.debug("Request to partially update Connector : {}", connectorDTO);

        return connectorRepository
            .findById(connectorDTO.getId())
            .map(existingConnector -> {
                connectorMapper.partialUpdate(existingConnector, connectorDTO);

                return existingConnector;
            })
            .map(connectorRepository::save)
            .map(connectorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConnectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Connectors");
        return connectorRepository.findAll(pageable).map(connectorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConnectorDTO> findOne(Long id) {
        log.debug("Request to get Connector : {}", id);
        return connectorRepository.findById(id).map(connectorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Connector : {}", id);
        connectorRepository.deleteById(id);
    }
}
