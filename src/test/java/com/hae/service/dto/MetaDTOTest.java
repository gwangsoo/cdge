package com.hae.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaDTO.class);
        MetaDTO metaDTO1 = new MetaDTO();
        metaDTO1.setId(1L);
        MetaDTO metaDTO2 = new MetaDTO();
        assertThat(metaDTO1).isNotEqualTo(metaDTO2);
        metaDTO2.setId(metaDTO1.getId());
        assertThat(metaDTO1).isEqualTo(metaDTO2);
        metaDTO2.setId(2L);
        assertThat(metaDTO1).isNotEqualTo(metaDTO2);
        metaDTO1.setId(null);
        assertThat(metaDTO1).isNotEqualTo(metaDTO2);
    }
}
