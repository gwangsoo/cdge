package com.hae.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evse.class);
        Evse evse1 = new Evse();
        evse1.setId(1L);
        Evse evse2 = new Evse();
        evse2.setId(evse1.getId());
        assertThat(evse1).isEqualTo(evse2);
        evse2.setId(2L);
        assertThat(evse1).isNotEqualTo(evse2);
        evse1.setId(null);
        assertThat(evse1).isNotEqualTo(evse2);
    }
}
