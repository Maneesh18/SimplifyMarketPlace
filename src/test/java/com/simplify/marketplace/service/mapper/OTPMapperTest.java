package com.simplify.marketplace.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OTPMapperTest {

    private OTPMapper oTPMapper;

    @BeforeEach
    public void setUp() {
        oTPMapper = new OTPMapperImpl();
    }
}
