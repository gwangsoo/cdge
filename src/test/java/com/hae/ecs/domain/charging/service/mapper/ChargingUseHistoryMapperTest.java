package com.hae.ecs.domain.charging.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargingUseHistoryMapperTest {

    private ChargingUseHistoryMapper chargingUseHistoryMapper;

    @BeforeEach
    public void setUp() {
        chargingUseHistoryMapper = new ChargingUseHistoryMapperImpl();
    }
}
