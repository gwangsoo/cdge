package com.hae.ecs.domain.charging.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargingUseHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingUseHistory.class);
        ChargingUseHistory chargingUseHistory1 = new ChargingUseHistory();
        chargingUseHistory1.setId(1L);
        ChargingUseHistory chargingUseHistory2 = new ChargingUseHistory();
        chargingUseHistory2.setId(chargingUseHistory1.getId());
        assertThat(chargingUseHistory1).isEqualTo(chargingUseHistory2);
        chargingUseHistory2.setId(2L);
        assertThat(chargingUseHistory1).isNotEqualTo(chargingUseHistory2);
        chargingUseHistory1.setId(null);
        assertThat(chargingUseHistory1).isNotEqualTo(chargingUseHistory2);
    }
}
