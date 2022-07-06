package com.hae.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvseMapperTest {

    private EvseMapper evseMapper;

    @BeforeEach
    public void setUp() {
        evseMapper = new EvseMapperImpl();
    }
}
