package com.hae.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Charger.class);
        Charger charger1 = new Charger();
        charger1.setId(1L);
        Charger charger2 = new Charger();
        charger2.setId(charger1.getId());
        assertThat(charger1).isEqualTo(charger2);
        charger2.setId(2L);
        assertThat(charger1).isNotEqualTo(charger2);
        charger1.setId(null);
        assertThat(charger1).isNotEqualTo(charger2);
    }
}
