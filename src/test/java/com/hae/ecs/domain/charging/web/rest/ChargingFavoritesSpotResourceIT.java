package com.hae.ecs.domain.charging.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.ecs.domain.charging.IntegrationTest;
import com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot;
import com.hae.ecs.domain.charging.domain.enumeration.ChargingFavoriteStatus;
import com.hae.ecs.domain.charging.repository.ChargingFavoritesSpotRepository;
import com.hae.ecs.domain.charging.service.dto.ChargingFavoritesSpotDTO;
import com.hae.ecs.domain.charging.service.mapper.ChargingFavoritesSpotMapper;
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
 * Integration tests for the {@link ChargingFavoritesSpotResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChargingFavoritesSpotResourceIT {

    private static final String DEFAULT_MEM_NO = "AAAAAAAAAA";
    private static final String UPDATED_MEM_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CPO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CPO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_ID = "BBBBBBBBBB";

    private static final ChargingFavoriteStatus DEFAULT_STATUS = ChargingFavoriteStatus.CHECK;
    private static final ChargingFavoriteStatus UPDATED_STATUS = ChargingFavoriteStatus.UNCHECK;

    private static final String ENTITY_API_URL = "/api/charging-favorites-spots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChargingFavoritesSpotRepository chargingFavoritesSpotRepository;

    @Autowired
    private ChargingFavoritesSpotMapper chargingFavoritesSpotMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargingFavoritesSpotMockMvc;

    private ChargingFavoritesSpot chargingFavoritesSpot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingFavoritesSpot createEntity(EntityManager em) {
        ChargingFavoritesSpot chargingFavoritesSpot = new ChargingFavoritesSpot()
            .memNo(DEFAULT_MEM_NO)
            .cpoName(DEFAULT_CPO_NAME)
            .locationId(DEFAULT_LOCATION_ID)
            .status(DEFAULT_STATUS);
        return chargingFavoritesSpot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingFavoritesSpot createUpdatedEntity(EntityManager em) {
        ChargingFavoritesSpot chargingFavoritesSpot = new ChargingFavoritesSpot()
            .memNo(UPDATED_MEM_NO)
            .cpoName(UPDATED_CPO_NAME)
            .locationId(UPDATED_LOCATION_ID)
            .status(UPDATED_STATUS);
        return chargingFavoritesSpot;
    }

    @BeforeEach
    public void initTest() {
        chargingFavoritesSpot = createEntity(em);
    }

    @Test
    @Transactional
    void createChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeCreate = chargingFavoritesSpotRepository.findAll().size();
        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);
        restChargingFavoritesSpotMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeCreate + 1);
        ChargingFavoritesSpot testChargingFavoritesSpot = chargingFavoritesSpotList.get(chargingFavoritesSpotList.size() - 1);
        assertThat(testChargingFavoritesSpot.getMemNo()).isEqualTo(DEFAULT_MEM_NO);
        assertThat(testChargingFavoritesSpot.getCpoName()).isEqualTo(DEFAULT_CPO_NAME);
        assertThat(testChargingFavoritesSpot.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testChargingFavoritesSpot.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createChargingFavoritesSpotWithExistingId() throws Exception {
        // Create the ChargingFavoritesSpot with an existing ID
        chargingFavoritesSpot.setId(1L);
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        int databaseSizeBeforeCreate = chargingFavoritesSpotRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargingFavoritesSpotMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMemNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingFavoritesSpotRepository.findAll().size();
        // set the field null
        chargingFavoritesSpot.setMemNo(null);

        // Create the ChargingFavoritesSpot, which fails.
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        restChargingFavoritesSpotMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingFavoritesSpotRepository.findAll().size();
        // set the field null
        chargingFavoritesSpot.setCpoName(null);

        // Create the ChargingFavoritesSpot, which fails.
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        restChargingFavoritesSpotMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingFavoritesSpotRepository.findAll().size();
        // set the field null
        chargingFavoritesSpot.setLocationId(null);

        // Create the ChargingFavoritesSpot, which fails.
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        restChargingFavoritesSpotMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingFavoritesSpotRepository.findAll().size();
        // set the field null
        chargingFavoritesSpot.setStatus(null);

        // Create the ChargingFavoritesSpot, which fails.
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        restChargingFavoritesSpotMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChargingFavoritesSpots() throws Exception {
        // Initialize the database
        chargingFavoritesSpotRepository.saveAndFlush(chargingFavoritesSpot);

        // Get all the chargingFavoritesSpotList
        restChargingFavoritesSpotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingFavoritesSpot.getId().intValue())))
            .andExpect(jsonPath("$.[*].memNo").value(hasItem(DEFAULT_MEM_NO)))
            .andExpect(jsonPath("$.[*].cpoName").value(hasItem(DEFAULT_CPO_NAME)))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getChargingFavoritesSpot() throws Exception {
        // Initialize the database
        chargingFavoritesSpotRepository.saveAndFlush(chargingFavoritesSpot);

        // Get the chargingFavoritesSpot
        restChargingFavoritesSpotMockMvc
            .perform(get(ENTITY_API_URL_ID, chargingFavoritesSpot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chargingFavoritesSpot.getId().intValue()))
            .andExpect(jsonPath("$.memNo").value(DEFAULT_MEM_NO))
            .andExpect(jsonPath("$.cpoName").value(DEFAULT_CPO_NAME))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChargingFavoritesSpot() throws Exception {
        // Get the chargingFavoritesSpot
        restChargingFavoritesSpotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChargingFavoritesSpot() throws Exception {
        // Initialize the database
        chargingFavoritesSpotRepository.saveAndFlush(chargingFavoritesSpot);

        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();

        // Update the chargingFavoritesSpot
        ChargingFavoritesSpot updatedChargingFavoritesSpot = chargingFavoritesSpotRepository.findById(chargingFavoritesSpot.getId()).get();
        // Disconnect from session so that the updates on updatedChargingFavoritesSpot are not directly saved in db
        em.detach(updatedChargingFavoritesSpot);
        updatedChargingFavoritesSpot.memNo(UPDATED_MEM_NO).cpoName(UPDATED_CPO_NAME).locationId(UPDATED_LOCATION_ID).status(UPDATED_STATUS);
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(updatedChargingFavoritesSpot);

        restChargingFavoritesSpotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargingFavoritesSpotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
        ChargingFavoritesSpot testChargingFavoritesSpot = chargingFavoritesSpotList.get(chargingFavoritesSpotList.size() - 1);
        assertThat(testChargingFavoritesSpot.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testChargingFavoritesSpot.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingFavoritesSpot.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testChargingFavoritesSpot.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();
        chargingFavoritesSpot.setId(count.incrementAndGet());

        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingFavoritesSpotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargingFavoritesSpotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();
        chargingFavoritesSpot.setId(count.incrementAndGet());

        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingFavoritesSpotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();
        chargingFavoritesSpot.setId(count.incrementAndGet());

        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingFavoritesSpotMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChargingFavoritesSpotWithPatch() throws Exception {
        // Initialize the database
        chargingFavoritesSpotRepository.saveAndFlush(chargingFavoritesSpot);

        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();

        // Update the chargingFavoritesSpot using partial update
        ChargingFavoritesSpot partialUpdatedChargingFavoritesSpot = new ChargingFavoritesSpot();
        partialUpdatedChargingFavoritesSpot.setId(chargingFavoritesSpot.getId());

        partialUpdatedChargingFavoritesSpot.status(UPDATED_STATUS);

        restChargingFavoritesSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChargingFavoritesSpot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChargingFavoritesSpot))
            )
            .andExpect(status().isOk());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
        ChargingFavoritesSpot testChargingFavoritesSpot = chargingFavoritesSpotList.get(chargingFavoritesSpotList.size() - 1);
        assertThat(testChargingFavoritesSpot.getMemNo()).isEqualTo(DEFAULT_MEM_NO);
        assertThat(testChargingFavoritesSpot.getCpoName()).isEqualTo(DEFAULT_CPO_NAME);
        assertThat(testChargingFavoritesSpot.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testChargingFavoritesSpot.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateChargingFavoritesSpotWithPatch() throws Exception {
        // Initialize the database
        chargingFavoritesSpotRepository.saveAndFlush(chargingFavoritesSpot);

        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();

        // Update the chargingFavoritesSpot using partial update
        ChargingFavoritesSpot partialUpdatedChargingFavoritesSpot = new ChargingFavoritesSpot();
        partialUpdatedChargingFavoritesSpot.setId(chargingFavoritesSpot.getId());

        partialUpdatedChargingFavoritesSpot
            .memNo(UPDATED_MEM_NO)
            .cpoName(UPDATED_CPO_NAME)
            .locationId(UPDATED_LOCATION_ID)
            .status(UPDATED_STATUS);

        restChargingFavoritesSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChargingFavoritesSpot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChargingFavoritesSpot))
            )
            .andExpect(status().isOk());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
        ChargingFavoritesSpot testChargingFavoritesSpot = chargingFavoritesSpotList.get(chargingFavoritesSpotList.size() - 1);
        assertThat(testChargingFavoritesSpot.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testChargingFavoritesSpot.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingFavoritesSpot.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testChargingFavoritesSpot.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();
        chargingFavoritesSpot.setId(count.incrementAndGet());

        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingFavoritesSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chargingFavoritesSpotDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();
        chargingFavoritesSpot.setId(count.incrementAndGet());

        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingFavoritesSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChargingFavoritesSpot() throws Exception {
        int databaseSizeBeforeUpdate = chargingFavoritesSpotRepository.findAll().size();
        chargingFavoritesSpot.setId(count.incrementAndGet());

        // Create the ChargingFavoritesSpot
        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = chargingFavoritesSpotMapper.toDto(chargingFavoritesSpot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingFavoritesSpotMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingFavoritesSpotDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChargingFavoritesSpot in the database
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChargingFavoritesSpot() throws Exception {
        // Initialize the database
        chargingFavoritesSpotRepository.saveAndFlush(chargingFavoritesSpot);

        int databaseSizeBeforeDelete = chargingFavoritesSpotRepository.findAll().size();

        // Delete the chargingFavoritesSpot
        restChargingFavoritesSpotMockMvc
            .perform(delete(ENTITY_API_URL_ID, chargingFavoritesSpot.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChargingFavoritesSpot> chargingFavoritesSpotList = chargingFavoritesSpotRepository.findAll();
        assertThat(chargingFavoritesSpotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
