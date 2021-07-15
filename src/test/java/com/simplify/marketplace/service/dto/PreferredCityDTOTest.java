package com.simplify.marketplace.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredCityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredCityDTO.class);
        PreferredCityDTO preferredCityDTO1 = new PreferredCityDTO();
        preferredCityDTO1.setId("id1");
        PreferredCityDTO preferredCityDTO2 = new PreferredCityDTO();
        assertThat(preferredCityDTO1).isNotEqualTo(preferredCityDTO2);
        preferredCityDTO2.setId(preferredCityDTO1.getId());
        assertThat(preferredCityDTO1).isEqualTo(preferredCityDTO2);
        preferredCityDTO2.setId("id2");
        assertThat(preferredCityDTO1).isNotEqualTo(preferredCityDTO2);
        preferredCityDTO1.setId(null);
        assertThat(preferredCityDTO1).isNotEqualTo(preferredCityDTO2);
    }
}
