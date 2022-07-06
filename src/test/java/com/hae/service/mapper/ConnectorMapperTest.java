package com.hae.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectorMapperTest {

    private ConnectorMapper connectorMapper;

    @BeforeEach
    public void setUp() {
        connectorMapper = new ConnectorMapperImpl();
    }
}
