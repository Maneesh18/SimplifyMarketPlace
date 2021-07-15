package com.simplify.marketplace.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmploymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employment.class);
        Employment employment1 = new Employment();
        employment1.setId("id1");
        Employment employment2 = new Employment();
        employment2.setId(employment1.getId());
        assertThat(employment1).isEqualTo(employment2);
        employment2.setId("id2");
        assertThat(employment1).isNotEqualTo(employment2);
        employment1.setId(null);
        assertThat(employment1).isNotEqualTo(employment2);
    }
}
