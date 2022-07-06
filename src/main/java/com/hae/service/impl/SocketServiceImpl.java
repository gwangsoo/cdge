package com.hae.service.impl;

import com.hae.domain.Socket;
import com.hae.repository.SocketRepository;
import com.hae.service.SocketService;
import com.hae.service.dto.SocketDTO;
import com.hae.service.mapper.SocketMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Socket}.
 */
@Service
@Transactional
public class SocketServiceImpl implements SocketService {

    private final Logger log = LoggerFactory.getLogger(SocketServiceImpl.class);

    private final SocketRepository socketRepository;

    private final SocketMapper socketMapper;

    public SocketServiceImpl(SocketRepository socketRepository, SocketMapper socketMapper) {
        this.socketRepository = socketRepository;
        this.socketMapper = socketMapper;
    }

    @Override
    public SocketDTO save(SocketDTO socketDTO) {
        log.debug("Request to save Socket : {}", socketDTO);
        Socket socket = socketMapper.toEntity(socketDTO);
        socket = socketRepository.save(socket);
        return socketMapper.toDto(socket);
    }

    @Override
    public SocketDTO update(SocketDTO socketDTO) {
        log.debug("Request to save Socket : {}", socketDTO);
        Socket socket = socketMapper.toEntity(socketDTO);
        socket = socketRepository.save(socket);
        return socketMapper.toDto(socket);
    }

    @Override
    public Optional<SocketDTO> partialUpdate(SocketDTO socketDTO) {
        log.debug("Request to partially update Socket : {}", socketDTO);

        return socketRepository
            .findById(socketDTO.getId())
            .map(existingSocket -> {
                socketMapper.partialUpdate(existingSocket, socketDTO);

                return existingSocket;
            })
            .map(socketRepository::save)
            .map(socketMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sockets");
        return socketRepository.findAll(pageable).map(socketMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocketDTO> findOne(Long id) {
        log.debug("Request to get Socket : {}", id);
        return socketRepository.findById(id).map(socketMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Socket : {}", id);
        socketRepository.deleteById(id);
    }
}
