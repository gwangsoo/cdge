package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Connector;
import com.hae.domain.enumeration.ConnectorType;
import com.hae.domain.enumeration.CurrentType;
import com.hae.repository.ConnectorRepository;
import com.hae.service.dto.ConnectorDTO;
import com.hae.service.mapper.ConnectorMapper;
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
 * Integration tests for the {@link ConnectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConnectorResourceIT {

    private static final ConnectorType DEFAULT_TYPE = ConnectorType.Mennekes;
    private static final ConnectorType UPDATED_TYPE = ConnectorType.Pantograph;

    private static final Double DEFAULT_MAX_KWH = 0D;
    private static final Double UPDATED_MAX_KWH = 1D;

    private static final Double DEFAULT_MAX_KW = 0D;
    private static final Double UPDATED_MAX_KW = 1D;

    private static final CurrentType DEFAULT_CURRENT_TYPE = CurrentType.AC;
    private static final CurrentType UPDATED_CURRENT_TYPE = CurrentType.DC;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/connectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConnectorRepository connectorRepository;

    @Autowired
    private ConnectorMapper connectorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectorMockMvc;

    private Connector connector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connector createEntity(EntityManager em) {
        Connector connector = new Connector()
            .type(DEFAULT_TYPE)
            .maxKwh(DEFAULT_MAX_KWH)
            .maxKw(DEFAULT_MAX_KW)
            .currentType(DEFAULT_CURRENT_TYPE)
            .status(DEFAULT_STATUS);
        return connector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connector createUpdatedEntity(EntityManager em) {
        Connector connector = new Connector()
            .type(UPDATED_TYPE)
            .maxKwh(UPDATED_MAX_KWH)
            .maxKw(UPDATED_MAX_KW)
            .currentType(UPDATED_CURRENT_TYPE)
            .status(UPDATED_STATUS);
        return connector;
    }

    @BeforeEach
    public void initTest() {
        connector = createEntity(em);
    }

    @Test
    @Transactional
    void createConnector() throws Exception {
        int databaseSizeBeforeCreate = connectorRepository.findAll().size();
        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);
        restConnectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeCreate + 1);
        Connector testConnector = connectorList.get(connectorList.size() - 1);
        assertThat(testConnector.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConnector.getMaxKwh()).isEqualTo(DEFAULT_MAX_KWH);
        assertThat(testConnector.getMaxKw()).isEqualTo(DEFAULT_MAX_KW);
        assertThat(testConnector.getCurrentType()).isEqualTo(DEFAULT_CURRENT_TYPE);
        assertThat(testConnector.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createConnectorWithExistingId() throws Exception {
        // Create the Connector with an existing ID
        connector.setId(1L);
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        int databaseSizeBeforeCreate = connectorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorRepository.findAll().size();
        // set the field null
        connector.setType(null);

        // Create the Connector, which fails.
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        restConnectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorRepository.findAll().size();
        // set the field null
        connector.setCurrentType(null);

        // Create the Connector, which fails.
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        restConnectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConnectors() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        // Get all the connectorList
        restConnectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connector.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].maxKwh").value(hasItem(DEFAULT_MAX_KWH.doubleValue())))
            .andExpect(jsonPath("$.[*].maxKw").value(hasItem(DEFAULT_MAX_KW.doubleValue())))
            .andExpect(jsonPath("$.[*].currentType").value(hasItem(DEFAULT_CURRENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getConnector() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        // Get the connector
        restConnectorMockMvc
            .perform(get(ENTITY_API_URL_ID, connector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connector.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.maxKwh").value(DEFAULT_MAX_KWH.doubleValue()))
            .andExpect(jsonPath("$.maxKw").value(DEFAULT_MAX_KW.doubleValue()))
            .andExpect(jsonPath("$.currentType").value(DEFAULT_CURRENT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingConnector() throws Exception {
        // Get the connector
        restConnectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConnector() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();

        // Update the connector
        Connector updatedConnector = connectorRepository.findById(connector.getId()).get();
        // Disconnect from session so that the updates on updatedConnector are not directly saved in db
        em.detach(updatedConnector);
        updatedConnector
            .type(UPDATED_TYPE)
            .maxKwh(UPDATED_MAX_KWH)
            .maxKw(UPDATED_MAX_KW)
            .currentType(UPDATED_CURRENT_TYPE)
            .status(UPDATED_STATUS);
        ConnectorDTO connectorDTO = connectorMapper.toDto(updatedConnector);

        restConnectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, connectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(connectorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
        Connector testConnector = connectorList.get(connectorList.size() - 1);
        assertThat(testConnector.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConnector.getMaxKwh()).isEqualTo(UPDATED_MAX_KWH);
        assertThat(testConnector.getMaxKw()).isEqualTo(UPDATED_MAX_KW);
        assertThat(testConnector.getCurrentType()).isEqualTo(UPDATED_CURRENT_TYPE);
        assertThat(testConnector.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();
        connector.setId(count.incrementAndGet());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, connectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();
        connector.setId(count.incrementAndGet());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();
        connector.setId(count.incrementAndGet());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConnectorWithPatch() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();

        // Update the connector using partial update
        Connector partialUpdatedConnector = new Connector();
        partialUpdatedConnector.setId(connector.getId());

        partialUpdatedConnector.type(UPDATED_TYPE).maxKwh(UPDATED_MAX_KWH).maxKw(UPDATED_MAX_KW).status(UPDATED_STATUS);

        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConnector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConnector))
            )
            .andExpect(status().isOk());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
        Connector testConnector = connectorList.get(connectorList.size() - 1);
        assertThat(testConnector.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConnector.getMaxKwh()).isEqualTo(UPDATED_MAX_KWH);
        assertThat(testConnector.getMaxKw()).isEqualTo(UPDATED_MAX_KW);
        assertThat(testConnector.getCurrentType()).isEqualTo(DEFAULT_CURRENT_TYPE);
        assertThat(testConnector.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateConnectorWithPatch() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();

        // Update the connector using partial update
        Connector partialUpdatedConnector = new Connector();
        partialUpdatedConnector.setId(connector.getId());

        partialUpdatedConnector
            .type(UPDATED_TYPE)
            .maxKwh(UPDATED_MAX_KWH)
            .maxKw(UPDATED_MAX_KW)
            .currentType(UPDATED_CURRENT_TYPE)
            .status(UPDATED_STATUS);

        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConnector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConnector))
            )
            .andExpect(status().isOk());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
        Connector testConnector = connectorList.get(connectorList.size() - 1);
        assertThat(testConnector.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConnector.getMaxKwh()).isEqualTo(UPDATED_MAX_KWH);
        assertThat(testConnector.getMaxKw()).isEqualTo(UPDATED_MAX_KW);
        assertThat(testConnector.getCurrentType()).isEqualTo(UPDATED_CURRENT_TYPE);
        assertThat(testConnector.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();
        connector.setId(count.incrementAndGet());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, connectorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();
        connector.setId(count.incrementAndGet());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();
        connector.setId(count.incrementAndGet());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(connectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConnector() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        int databaseSizeBeforeDelete = connectorRepository.findAll().size();

        // Delete the connector
        restConnectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, connector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
