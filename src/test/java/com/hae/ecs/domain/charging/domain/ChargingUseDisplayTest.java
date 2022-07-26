package com.hae.ecs.domain.charging.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargingUseDisplayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingUseDisplay.class);
        ChargingUseDisplay chargingUseDisplay1 = new ChargingUseDisplay();
        chargingUseDisplay1.setId(1L);
        ChargingUseDisplay chargingUseDisplay2 = new ChargingUseDisplay();
        chargingUseDisplay2.setId(chargingUseDisplay1.getId());
        assertThat(chargingUseDisplay1).isEqualTo(chargingUseDisplay2);
        chargingUseDisplay2.setId(2L);
        assertThat(chargingUseDisplay1).isNotEqualTo(chargingUseDisplay2);
        chargingUseDisplay1.setId(null);
        assertThat(chargingUseDisplay1).isNotEqualTo(chargingUseDisplay2);
    }
}
