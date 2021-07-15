package com.simplify.marketplace.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplify.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OTPDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OTPDTO.class);
        OTPDTO oTPDTO1 = new OTPDTO();
        oTPDTO1.setId("id1");
        OTPDTO oTPDTO2 = new OTPDTO();
        assertThat(oTPDTO1).isNotEqualTo(oTPDTO2);
        oTPDTO2.setId(oTPDTO1.getId());
        assertThat(oTPDTO1).isEqualTo(oTPDTO2);
        oTPDTO2.setId("id2");
        assertThat(oTPDTO1).isNotEqualTo(oTPDTO2);
        oTPDTO1.setId(null);
        assertThat(oTPDTO1).isNotEqualTo(oTPDTO2);
    }
}
