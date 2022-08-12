package com.hae.ecs.domain.charging.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.ecs.domain.charging.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConnectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connector.class);
        Connector connector1 = new Connector();
        connector1.setId(1L);
        Connector connector2 = new Connector();
        connector2.setId(connector1.getId());
        assertThat(connector1).isEqualTo(connector2);
        connector2.setId(2L);
        assertThat(connector1).isNotEqualTo(connector2);
        connector1.setId(null);
        assertThat(connector1).isNotEqualTo(connector2);
    }
}
