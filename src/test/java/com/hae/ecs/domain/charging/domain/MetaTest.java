package com.hae.ecs.domain.charging.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meta.class);
        Meta meta1 = new Meta();
        meta1.setId(1L);
        Meta meta2 = new Meta();
        meta2.setId(meta1.getId());
        assertThat(meta1).isEqualTo(meta2);
        meta2.setId(2L);
        assertThat(meta1).isNotEqualTo(meta2);
        meta1.setId(null);
        assertThat(meta1).isNotEqualTo(meta2);
    }
}
