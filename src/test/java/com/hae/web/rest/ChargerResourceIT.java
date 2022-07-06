package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Charger;
import com.hae.repository.ChargerRepository;
import com.hae.service.dto.ChargerDTO;
import com.hae.service.mapper.ChargerMapper;
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
 * Integration tests for the {@link ChargerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChargerResourceIT {

    private static final String ENTITY_API_URL = "/api/chargers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChargerRepository chargerRepository;

    @Autowired
    private ChargerMapper chargerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargerMockMvc;

    private Charger charger;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charger createEntity(EntityManager em) {
        Charger charger = new Charger();
        return charger;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charger createUpdatedEntity(EntityManager em) {
        Charger charger = new Charger();
        return charger;
    }

    @BeforeEach
    public void initTest() {
        charger = createEntity(em);
    }

    @Test
    @Transactional
    void createCharger() throws Exception {
        int databaseSizeBeforeCreate = chargerRepository.findAll().size();
        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);
        restChargerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chargerDTO)))
            .andExpect(status().isCreated());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeCreate + 1);
        Charger testCharger = chargerList.get(chargerList.size() - 1);
    }

    @Test
    @Transactional
    void createChargerWithExistingId() throws Exception {
        // Create the Charger with an existing ID
        charger.setId(1L);
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        int databaseSizeBeforeCreate = chargerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chargerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChargers() throws Exception {
        // Initialize the database
        chargerRepository.saveAndFlush(charger);

        // Get all the chargerList
        restChargerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charger.getId().intValue())));
    }

    @Test
    @Transactional
    void getCharger() throws Exception {
        // Initialize the database
        chargerRepository.saveAndFlush(charger);

        // Get the charger
        restChargerMockMvc
            .perform(get(ENTITY_API_URL_ID, charger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(charger.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCharger() throws Exception {
        // Get the charger
        restChargerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCharger() throws Exception {
        // Initialize the database
        chargerRepository.saveAndFlush(charger);

        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();

        // Update the charger
        Charger updatedCharger = chargerRepository.findById(charger.getId()).get();
        // Disconnect from session so that the updates on updatedCharger are not directly saved in db
        em.detach(updatedCharger);
        ChargerDTO chargerDTO = chargerMapper.toDto(updatedCharger);

        restChargerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
        Charger testCharger = chargerList.get(chargerList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCharger() throws Exception {
        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();
        charger.setId(count.incrementAndGet());

        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCharger() throws Exception {
        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();
        charger.setId(count.incrementAndGet());

        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCharger() throws Exception {
        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();
        charger.setId(count.incrementAndGet());

        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chargerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChargerWithPatch() throws Exception {
        // Initialize the database
        chargerRepository.saveAndFlush(charger);

        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();

        // Update the charger using partial update
        Charger partialUpdatedCharger = new Charger();
        partialUpdatedCharger.setId(charger.getId());

        restChargerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharger.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharger))
            )
            .andExpect(status().isOk());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
        Charger testCharger = chargerList.get(chargerList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateChargerWithPatch() throws Exception {
        // Initialize the database
        chargerRepository.saveAndFlush(charger);

        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();

        // Update the charger using partial update
        Charger partialUpdatedCharger = new Charger();
        partialUpdatedCharger.setId(charger.getId());

        restChargerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharger.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharger))
            )
            .andExpect(status().isOk());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
        Charger testCharger = chargerList.get(chargerList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCharger() throws Exception {
        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();
        charger.setId(count.incrementAndGet());

        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chargerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCharger() throws Exception {
        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();
        charger.setId(count.incrementAndGet());

        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCharger() throws Exception {
        int databaseSizeBeforeUpdate = chargerRepository.findAll().size();
        charger.setId(count.incrementAndGet());

        // Create the Charger
        ChargerDTO chargerDTO = chargerMapper.toDto(charger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chargerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Charger in the database
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCharger() throws Exception {
        // Initialize the database
        chargerRepository.saveAndFlush(charger);

        int databaseSizeBeforeDelete = chargerRepository.findAll().size();

        // Delete the charger
        restChargerMockMvc
            .perform(delete(ENTITY_API_URL_ID, charger.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Charger> chargerList = chargerRepository.findAll();
        assertThat(chargerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
