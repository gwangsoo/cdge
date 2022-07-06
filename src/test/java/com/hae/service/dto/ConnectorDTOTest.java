package com.hae.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConnectorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectorDTO.class);
        ConnectorDTO connectorDTO1 = new ConnectorDTO();
        connectorDTO1.setId(1L);
        ConnectorDTO connectorDTO2 = new ConnectorDTO();
        assertThat(connectorDTO1).isNotEqualTo(connectorDTO2);
        connectorDTO2.setId(connectorDTO1.getId());
        assertThat(connectorDTO1).isEqualTo(connectorDTO2);
        connectorDTO2.setId(2L);
        assertThat(connectorDTO1).isNotEqualTo(connectorDTO2);
        connectorDTO1.setId(null);
        assertThat(connectorDTO1).isNotEqualTo(connectorDTO2);
    }
}
