package com.hae.ecs.domain.charging.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargingFavoritesSpotDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingFavoritesSpotDTO.class);
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO1 = new ChargingFavoritesSpotDTO();
        chargingFavoritesSpotDTO1.setId(1L);
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO2 = new ChargingFavoritesSpotDTO();
        assertThat(chargingFavoritesSpotDTO1).isNotEqualTo(chargingFavoritesSpotDTO2);
        chargingFavoritesSpotDTO2.setId(chargingFavoritesSpotDTO1.getId());
        assertThat(chargingFavoritesSpotDTO1).isEqualTo(chargingFavoritesSpotDTO2);
        chargingFavoritesSpotDTO2.setId(2L);
        assertThat(chargingFavoritesSpotDTO1).isNotEqualTo(chargingFavoritesSpotDTO2);
        chargingFavoritesSpotDTO1.setId(null);
        assertThat(chargingFavoritesSpotDTO1).isNotEqualTo(chargingFavoritesSpotDTO2);
    }
}
