package com.hae.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hae.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Socket.class);
        Socket socket1 = new Socket();
        socket1.setId(1L);
        Socket socket2 = new Socket();
        socket2.setId(socket1.getId());
        assertThat(socket1).isEqualTo(socket2);
        socket2.setId(2L);
        assertThat(socket1).isNotEqualTo(socket2);
        socket1.setId(null);
        assertThat(socket1).isNotEqualTo(socket2);
    }
}
