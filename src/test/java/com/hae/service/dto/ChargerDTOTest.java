package com.hae.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargerDTO.class);
        ChargerDTO chargerDTO1 = new ChargerDTO();
        chargerDTO1.setId(1L);
        ChargerDTO chargerDTO2 = new ChargerDTO();
        assertThat(chargerDTO1).isNotEqualTo(chargerDTO2);
        chargerDTO2.setId(chargerDTO1.getId());
        assertThat(chargerDTO1).isEqualTo(chargerDTO2);
        chargerDTO2.setId(2L);
        assertThat(chargerDTO1).isNotEqualTo(chargerDTO2);
        chargerDTO1.setId(null);
        assertThat(chargerDTO1).isNotEqualTo(chargerDTO2);
    }
}
