package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Meta;
import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import com.hae.repository.MetaRepository;
import com.hae.service.dto.MetaDTO;
import com.hae.service.mapper.MetaMapper;
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
 * Integration tests for the {@link MetaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetaResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Availability DEFAULT_AVAILABILITY = Availability.PUBLIC;
    private static final Availability UPDATED_AVAILABILITY = Availability.SHARED;

    private static final ChargerStatus DEFAULT_STATUS = ChargerStatus.FREE;
    private static final ChargerStatus UPDATED_STATUS = ChargerStatus.INUSE;

    private static final String ENTITY_API_URL = "/api/metas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private MetaMapper metaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaMockMvc;

    private Meta meta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meta createEntity(EntityManager em) {
        Meta meta = new Meta().active(DEFAULT_ACTIVE).availability(DEFAULT_AVAILABILITY).status(DEFAULT_STATUS);
        return meta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meta createUpdatedEntity(EntityManager em) {
        Meta meta = new Meta().active(UPDATED_ACTIVE).availability(UPDATED_AVAILABILITY).status(UPDATED_STATUS);
        return meta;
    }

    @BeforeEach
    public void initTest() {
        meta = createEntity(em);
    }

    @Test
    @Transactional
    void createMeta() throws Exception {
        int databaseSizeBeforeCreate = metaRepository.findAll().size();
        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);
        restMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isCreated());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeCreate + 1);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMeta.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testMeta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createMetaWithExistingId() throws Exception {
        // Create the Meta with an existing ID
        meta.setId(1L);
        MetaDTO metaDTO = metaMapper.toDto(meta);

        int databaseSizeBeforeCreate = metaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetas() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList
        restMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meta.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getMeta() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get the meta
        restMetaMockMvc
            .perform(get(ENTITY_API_URL_ID, meta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meta.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMeta() throws Exception {
        // Get the meta
        restMetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMeta() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeUpdate = metaRepository.findAll().size();

        // Update the meta
        Meta updatedMeta = metaRepository.findById(meta.getId()).get();
        // Disconnect from session so that the updates on updatedMeta are not directly saved in db
        em.detach(updatedMeta);
        updatedMeta.active(UPDATED_ACTIVE).availability(UPDATED_AVAILABILITY).status(UPDATED_STATUS);
        MetaDTO metaDTO = metaMapper.toDto(updatedMeta);

        restMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMeta.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testMeta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(count.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(count.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(count.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaWithPatch() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeUpdate = metaRepository.findAll().size();

        // Update the meta using partial update
        Meta partialUpdatedMeta = new Meta();
        partialUpdatedMeta.setId(meta.getId());

        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeta))
            )
            .andExpect(status().isOk());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMeta.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testMeta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateMetaWithPatch() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeUpdate = metaRepository.findAll().size();

        // Update the meta using partial update
        Meta partialUpdatedMeta = new Meta();
        partialUpdatedMeta.setId(meta.getId());

        partialUpdatedMeta.active(UPDATED_ACTIVE).availability(UPDATED_AVAILABILITY).status(UPDATED_STATUS);

        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeta))
            )
            .andExpect(status().isOk());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMeta.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testMeta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(count.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(count.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(count.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeta() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeDelete = metaRepository.findAll().size();

        // Delete the meta
        restMetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, meta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
