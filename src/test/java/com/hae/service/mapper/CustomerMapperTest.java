package com.hae.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class CustomerMapperTest {

    private CustomerMapper chargerMapper;

    @BeforeEach
    public void setUp() {
        chargerMapper = new CustomerMapperImpl();
    }
}
