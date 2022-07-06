package com.hae.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocketDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocketDTO.class);
        SocketDTO socketDTO1 = new SocketDTO();
        socketDTO1.setId(1L);
        SocketDTO socketDTO2 = new SocketDTO();
        assertThat(socketDTO1).isNotEqualTo(socketDTO2);
        socketDTO2.setId(socketDTO1.getId());
        assertThat(socketDTO1).isEqualTo(socketDTO2);
        socketDTO2.setId(2L);
        assertThat(socketDTO1).isNotEqualTo(socketDTO2);
        socketDTO1.setId(null);
        assertThat(socketDTO1).isNotEqualTo(socketDTO2);
    }
}
