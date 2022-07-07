package com.hae.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargerMapperTest {

    private ChargerMapper chargerMapper;

    @BeforeEach
    public void setUp() {
        chargerMapper = new ChargerMapperImpl();
    }
}
