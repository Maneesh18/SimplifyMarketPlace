package com.simplify.marketplace.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredCityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredCity.class);
        PreferredCity preferredCity1 = new PreferredCity();
        preferredCity1.setId("id1");
        PreferredCity preferredCity2 = new PreferredCity();
        preferredCity2.setId(preferredCity1.getId());
        assertThat(preferredCity1).isEqualTo(preferredCity2);
        preferredCity2.setId("id2");
        assertThat(preferredCity1).isNotEqualTo(preferredCity2);
        preferredCity1.setId(null);
        assertThat(preferredCity1).isNotEqualTo(preferredCity2);
    }
}
