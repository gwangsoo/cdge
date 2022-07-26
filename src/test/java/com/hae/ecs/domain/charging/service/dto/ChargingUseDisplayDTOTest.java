package com.hae.ecs.domain.charging.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargingUseDisplayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingUseDisplayDTO.class);
        ChargingUseDisplayDTO chargingUseDisplayDTO1 = new ChargingUseDisplayDTO();
        chargingUseDisplayDTO1.setId(1L);
        ChargingUseDisplayDTO chargingUseDisplayDTO2 = new ChargingUseDisplayDTO();
        assertThat(chargingUseDisplayDTO1).isNotEqualTo(chargingUseDisplayDTO2);
        chargingUseDisplayDTO2.setId(chargingUseDisplayDTO1.getId());
        assertThat(chargingUseDisplayDTO1).isEqualTo(chargingUseDisplayDTO2);
        chargingUseDisplayDTO2.setId(2L);
        assertThat(chargingUseDisplayDTO1).isNotEqualTo(chargingUseDisplayDTO2);
        chargingUseDisplayDTO1.setId(null);
        assertThat(chargingUseDisplayDTO1).isNotEqualTo(chargingUseDisplayDTO2);
    }
}
