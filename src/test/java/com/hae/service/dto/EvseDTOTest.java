package com.hae.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvseDTO.class);
        EvseDTO evseDTO1 = new EvseDTO();
        evseDTO1.setId(1L);
        EvseDTO evseDTO2 = new EvseDTO();
        assertThat(evseDTO1).isNotEqualTo(evseDTO2);
        evseDTO2.setId(evseDTO1.getId());
        assertThat(evseDTO1).isEqualTo(evseDTO2);
        evseDTO2.setId(2L);
        assertThat(evseDTO1).isNotEqualTo(evseDTO2);
        evseDTO1.setId(null);
        assertThat(evseDTO1).isNotEqualTo(evseDTO2);
    }
}
