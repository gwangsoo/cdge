package com.hae.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocketMapperTest {

    private SocketMapper socketMapper;

    @BeforeEach
    public void setUp() {
        socketMapper = new SocketMapperImpl();
    }
}
