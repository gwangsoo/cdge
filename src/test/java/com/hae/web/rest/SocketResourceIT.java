package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Socket;
import com.hae.repository.SocketRepository;
import com.hae.service.dto.SocketDTO;
import com.hae.service.mapper.SocketMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SocketResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocketResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sockets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocketRepository socketRepository;

    @Autowired
    private SocketMapper socketMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocketMockMvc;

    private Socket socket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socket createEntity(EntityManager em) {
        Socket socket = new Socket().name(DEFAULT_NAME);
        return socket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socket createUpdatedEntity(EntityManager em) {
        Socket socket = new Socket().name(UPDATED_NAME);
        return socket;
    }

    @BeforeEach
    public void initTest() {
        socket = createEntity(em);
    }

    @Test
    @Transactional
    void createSocket() throws Exception {
        int databaseSizeBeforeCreate = socketRepository.findAll().size();
        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);
        restSocketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socketDTO)))
            .andExpect(status().isCreated());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeCreate + 1);
        Socket testSocket = socketList.get(socketList.size() - 1);
        assertThat(testSocket.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSocketWithExistingId() throws Exception {
        // Create the Socket with an existing ID
        socket.setId(1L);
        SocketDTO socketDTO = socketMapper.toDto(socket);

        int databaseSizeBeforeCreate = socketRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = socketRepository.findAll().size();
        // set the field null
        socket.setName(null);

        // Create the Socket, which fails.
        SocketDTO socketDTO = socketMapper.toDto(socket);

        restSocketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socketDTO)))
            .andExpect(status().isBadRequest());

        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSockets() throws Exception {
        // Initialize the database
        socketRepository.saveAndFlush(socket);

        // Get all the socketList
        restSocketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socket.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSocket() throws Exception {
        // Initialize the database
        socketRepository.saveAndFlush(socket);

        // Get the socket
        restSocketMockMvc
            .perform(get(ENTITY_API_URL_ID, socket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socket.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSocket() throws Exception {
        // Get the socket
        restSocketMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocket() throws Exception {
        // Initialize the database
        socketRepository.saveAndFlush(socket);

        int databaseSizeBeforeUpdate = socketRepository.findAll().size();

        // Update the socket
        Socket updatedSocket = socketRepository.findById(socket.getId()).get();
        // Disconnect from session so that the updates on updatedSocket are not directly saved in db
        em.detach(updatedSocket);
        updatedSocket.name(UPDATED_NAME);
        SocketDTO socketDTO = socketMapper.toDto(updatedSocket);

        restSocketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socketDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socketDTO))
            )
            .andExpect(status().isOk());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
        Socket testSocket = socketList.get(socketList.size() - 1);
        assertThat(testSocket.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSocket() throws Exception {
        int databaseSizeBeforeUpdate = socketRepository.findAll().size();
        socket.setId(count.incrementAndGet());

        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socketDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocket() throws Exception {
        int databaseSizeBeforeUpdate = socketRepository.findAll().size();
        socket.setId(count.incrementAndGet());

        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocket() throws Exception {
        int databaseSizeBeforeUpdate = socketRepository.findAll().size();
        socket.setId(count.incrementAndGet());

        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocketMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socketDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocketWithPatch() throws Exception {
        // Initialize the database
        socketRepository.saveAndFlush(socket);

        int databaseSizeBeforeUpdate = socketRepository.findAll().size();

        // Update the socket using partial update
        Socket partialUpdatedSocket = new Socket();
        partialUpdatedSocket.setId(socket.getId());

        restSocketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocket))
            )
            .andExpect(status().isOk());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
        Socket testSocket = socketList.get(socketList.size() - 1);
        assertThat(testSocket.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSocketWithPatch() throws Exception {
        // Initialize the database
        socketRepository.saveAndFlush(socket);

        int databaseSizeBeforeUpdate = socketRepository.findAll().size();

        // Update the socket using partial update
        Socket partialUpdatedSocket = new Socket();
        partialUpdatedSocket.setId(socket.getId());

        partialUpdatedSocket.name(UPDATED_NAME);

        restSocketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocket))
            )
            .andExpect(status().isOk());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
        Socket testSocket = socketList.get(socketList.size() - 1);
        assertThat(testSocket.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSocket() throws Exception {
        int databaseSizeBeforeUpdate = socketRepository.findAll().size();
        socket.setId(count.incrementAndGet());

        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socketDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocket() throws Exception {
        int databaseSizeBeforeUpdate = socketRepository.findAll().size();
        socket.setId(count.incrementAndGet());

        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocket() throws Exception {
        int databaseSizeBeforeUpdate = socketRepository.findAll().size();
        socket.setId(count.incrementAndGet());

        // Create the Socket
        SocketDTO socketDTO = socketMapper.toDto(socket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocketMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(socketDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socket in the database
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocket() throws Exception {
        // Initialize the database
        socketRepository.saveAndFlush(socket);

        int databaseSizeBeforeDelete = socketRepository.findAll().size();

        // Delete the socket
        restSocketMockMvc
            .perform(delete(ENTITY_API_URL_ID, socket.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Socket> socketList = socketRepository.findAll();
        assertThat(socketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
