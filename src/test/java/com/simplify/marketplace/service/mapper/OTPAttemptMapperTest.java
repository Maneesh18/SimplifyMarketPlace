package com.simplify.marketplace.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OTPAttemptMapperTest {

    private OTPAttemptMapper oTPAttemptMapper;

    @BeforeEach
    public void setUp() {
        oTPAttemptMapper = new OTPAttemptMapperImpl();
    }
}
