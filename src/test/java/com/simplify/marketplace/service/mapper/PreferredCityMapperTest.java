package com.simplify.marketplace.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreferredCityMapperTest {

    private PreferredCityMapper preferredCityMapper;

    @BeforeEach
    public void setUp() {
        preferredCityMapper = new PreferredCityMapperImpl();
    }
}
