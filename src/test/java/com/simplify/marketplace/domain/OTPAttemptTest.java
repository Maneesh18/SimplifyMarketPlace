package com.simplify.marketplace.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OTPAttemptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OTPAttempt.class);
        OTPAttempt oTPAttempt1 = new OTPAttempt();
        oTPAttempt1.setId("id1");
        OTPAttempt oTPAttempt2 = new OTPAttempt();
        oTPAttempt2.setId(oTPAttempt1.getId());
        assertThat(oTPAttempt1).isEqualTo(oTPAttempt2);
        oTPAttempt2.setId("id2");
        assertThat(oTPAttempt1).isNotEqualTo(oTPAttempt2);
        oTPAttempt1.setId(null);
        assertThat(oTPAttempt1).isNotEqualTo(oTPAttempt2);
    }
}
