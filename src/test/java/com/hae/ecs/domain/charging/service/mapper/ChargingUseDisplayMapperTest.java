package com.hae.ecs.domain.charging.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargingUseDisplayMapperTest {

    private ChargingUseDisplayMapper chargingUseDisplayMapper;

    @BeforeEach
    public void setUp() {
        chargingUseDisplayMapper = new ChargingUseDisplayMapperImpl();
    }
}
