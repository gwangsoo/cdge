package com.hae.ecs.domain.charging.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargingFavoritesSpotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingFavoritesSpot.class);
        ChargingFavoritesSpot chargingFavoritesSpot1 = new ChargingFavoritesSpot();
        chargingFavoritesSpot1.setId(1L);
        ChargingFavoritesSpot chargingFavoritesSpot2 = new ChargingFavoritesSpot();
        chargingFavoritesSpot2.setId(chargingFavoritesSpot1.getId());
        assertThat(chargingFavoritesSpot1).isEqualTo(chargingFavoritesSpot2);
        chargingFavoritesSpot2.setId(2L);
        assertThat(chargingFavoritesSpot1).isNotEqualTo(chargingFavoritesSpot2);
        chargingFavoritesSpot1.setId(null);
        assertThat(chargingFavoritesSpot1).isNotEqualTo(chargingFavoritesSpot2);
    }
}
