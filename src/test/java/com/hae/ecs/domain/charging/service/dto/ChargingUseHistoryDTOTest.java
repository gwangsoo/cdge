package com.hae.ecs.domain.charging.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargingUseHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingUseHistoryDTO.class);
        ChargingUseHistoryDTO chargingUseHistoryDTO1 = new ChargingUseHistoryDTO();
        chargingUseHistoryDTO1.setId(1L);
        ChargingUseHistoryDTO chargingUseHistoryDTO2 = new ChargingUseHistoryDTO();
        assertThat(chargingUseHistoryDTO1).isNotEqualTo(chargingUseHistoryDTO2);
        chargingUseHistoryDTO2.setId(chargingUseHistoryDTO1.getId());
        assertThat(chargingUseHistoryDTO1).isEqualTo(chargingUseHistoryDTO2);
        chargingUseHistoryDTO2.setId(2L);
        assertThat(chargingUseHistoryDTO1).isNotEqualTo(chargingUseHistoryDTO2);
        chargingUseHistoryDTO1.setId(null);
        assertThat(chargingUseHistoryDTO1).isNotEqualTo(chargingUseHistoryDTO2);
    }
}
