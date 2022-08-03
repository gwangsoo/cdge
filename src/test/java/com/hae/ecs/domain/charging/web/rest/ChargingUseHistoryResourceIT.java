package com.hae.ecs.domain.charging.web.rest;

import static com.hae.ecs.domain.charging.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.ecs.domain.charging.IntegrationTest;
import com.hae.ecs.domain.charging.domain.ChargingUseHistory;
import com.hae.ecs.domain.charging.repository.ChargingUseHistoryRepository;
import com.hae.ecs.domain.charging.service.dto.ChargingUseHistoryDTO;
import com.hae.ecs.domain.charging.service.mapper.ChargingUseHistoryMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link ChargingUseHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChargingUseHistoryResourceIT {

    private static final String DEFAULT_CHARGING_USE_DISPLAY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHARGING_USE_DISPLAY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EVSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVSE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEM_NO = "AAAAAAAAAA";
    private static final String UPDATED_MEM_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CAR_CD = "AAAAAAAAAA";
    private static final String UPDATED_CAR_CD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_FIRST_SOC = 0D;
    private static final Double UPDATED_FIRST_SOC = 1D;

    private static final Double DEFAULT_FINAL_SOC = 0D;
    private static final Double UPDATED_FINAL_SOC = 1D;

    private static final Integer DEFAULT_FIRST_DISTANCE = 0;
    private static final Integer UPDATED_FIRST_DISTANCE = 1;

    private static final Integer DEFAULT_FINAL_DISTANCE = 0;
    private static final Integer UPDATED_FINAL_DISTANCE = 1;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CPO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CPO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POWER_TYPE = "AAAAA";
    private static final String UPDATED_POWER_TYPE = "BBBBB";

    private static final Integer DEFAULT_TOTAL_COST = 0;
    private static final Integer UPDATED_TOTAL_COST = 1;

    private static final Integer DEFAULT_TOTAL_ENERGY = 0;
    private static final Integer UPDATED_TOTAL_ENERGY = 1;

    private static final Integer DEFAULT_TOTAL_TIME = 0;
    private static final Integer UPDATED_TOTAL_TIME = 1;

    private static final String ENTITY_API_URL = "/api/charging-use-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChargingUseHistoryRepository chargingUseHistoryRepository;

    @Autowired
    private ChargingUseHistoryMapper chargingUseHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargingUseHistoryMockMvc;

    private ChargingUseHistory chargingUseHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingUseHistory createEntity(EntityManager em) {
        ChargingUseHistory chargingUseHistory = new ChargingUseHistory()
            .chargingUseDisplayId(DEFAULT_CHARGING_USE_DISPLAY_ID)
            .locationId(DEFAULT_LOCATION_ID)
            .evseId(DEFAULT_EVSE_ID)
            .connectorId(DEFAULT_CONNECTOR_ID)
            .memNo(DEFAULT_MEM_NO)
            .carCd(DEFAULT_CAR_CD)
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .firstSoc(DEFAULT_FIRST_SOC)
            .finalSoc(DEFAULT_FINAL_SOC)
            .firstDistance(DEFAULT_FIRST_DISTANCE)
            .finalDistance(DEFAULT_FINAL_DISTANCE)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .cpoName(DEFAULT_CPO_NAME)
            .powerType(DEFAULT_POWER_TYPE)
            .totalCost(DEFAULT_TOTAL_COST)
            .totalEnergy(DEFAULT_TOTAL_ENERGY)
            .totalTime(DEFAULT_TOTAL_TIME);
        return chargingUseHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingUseHistory createUpdatedEntity(EntityManager em) {
        ChargingUseHistory chargingUseHistory = new ChargingUseHistory()
            .chargingUseDisplayId(UPDATED_CHARGING_USE_DISPLAY_ID)
            .locationId(UPDATED_LOCATION_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .memNo(UPDATED_MEM_NO)
            .carCd(UPDATED_CAR_CD)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .firstSoc(UPDATED_FIRST_SOC)
            .finalSoc(UPDATED_FINAL_SOC)
            .firstDistance(UPDATED_FIRST_DISTANCE)
            .finalDistance(UPDATED_FINAL_DISTANCE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .cpoName(UPDATED_CPO_NAME)
            .powerType(UPDATED_POWER_TYPE)
            .totalCost(UPDATED_TOTAL_COST)
            .totalEnergy(UPDATED_TOTAL_ENERGY)
            .totalTime(UPDATED_TOTAL_TIME);
        return chargingUseHistory;
    }

    @BeforeEach
    public void initTest() {
        chargingUseHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createChargingUseHistory() throws Exception {
        int databaseSizeBeforeCreate = chargingUseHistoryRepository.findAll().size();
        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);
        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ChargingUseHistory testChargingUseHistory = chargingUseHistoryList.get(chargingUseHistoryList.size() - 1);
        assertThat(testChargingUseHistory.getChargingUseDisplayId()).isEqualTo(DEFAULT_CHARGING_USE_DISPLAY_ID);
        assertThat(testChargingUseHistory.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testChargingUseHistory.getEvseId()).isEqualTo(DEFAULT_EVSE_ID);
        assertThat(testChargingUseHistory.getConnectorId()).isEqualTo(DEFAULT_CONNECTOR_ID);
        assertThat(testChargingUseHistory.getMemNo()).isEqualTo(DEFAULT_MEM_NO);
        assertThat(testChargingUseHistory.getCarCd()).isEqualTo(DEFAULT_CAR_CD);
        assertThat(testChargingUseHistory.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testChargingUseHistory.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testChargingUseHistory.getFirstSoc()).isEqualTo(DEFAULT_FIRST_SOC);
        assertThat(testChargingUseHistory.getFinalSoc()).isEqualTo(DEFAULT_FINAL_SOC);
        assertThat(testChargingUseHistory.getFirstDistance()).isEqualTo(DEFAULT_FIRST_DISTANCE);
        assertThat(testChargingUseHistory.getFinalDistance()).isEqualTo(DEFAULT_FINAL_DISTANCE);
        assertThat(testChargingUseHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChargingUseHistory.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testChargingUseHistory.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testChargingUseHistory.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testChargingUseHistory.getCpoName()).isEqualTo(DEFAULT_CPO_NAME);
        assertThat(testChargingUseHistory.getPowerType()).isEqualTo(DEFAULT_POWER_TYPE);
        assertThat(testChargingUseHistory.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testChargingUseHistory.getTotalEnergy()).isEqualTo(DEFAULT_TOTAL_ENERGY);
        assertThat(testChargingUseHistory.getTotalTime()).isEqualTo(DEFAULT_TOTAL_TIME);
    }

    @Test
    @Transactional
    void createChargingUseHistoryWithExistingId() throws Exception {
        // Create the ChargingUseHistory with an existing ID
        chargingUseHistory.setId(1L);
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        int databaseSizeBeforeCreate = chargingUseHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChargingUseDisplayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setChargingUseDisplayId(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setLocationId(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEvseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setEvseId(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConnectorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setConnectorId(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setAddress(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setCity(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setCpoName(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPowerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseHistoryRepository.findAll().size();
        // set the field null
        chargingUseHistory.setPowerType(null);

        // Create the ChargingUseHistory, which fails.
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChargingUseHistories() throws Exception {
        // Initialize the database
        chargingUseHistoryRepository.saveAndFlush(chargingUseHistory);

        // Get all the chargingUseHistoryList
        restChargingUseHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingUseHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].chargingUseDisplayId").value(hasItem(DEFAULT_CHARGING_USE_DISPLAY_ID)))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID)))
            .andExpect(jsonPath("$.[*].evseId").value(hasItem(DEFAULT_EVSE_ID)))
            .andExpect(jsonPath("$.[*].connectorId").value(hasItem(DEFAULT_CONNECTOR_ID)))
            .andExpect(jsonPath("$.[*].memNo").value(hasItem(DEFAULT_MEM_NO)))
            .andExpect(jsonPath("$.[*].carCd").value(hasItem(DEFAULT_CAR_CD)))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(sameInstant(DEFAULT_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(sameInstant(DEFAULT_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].firstSoc").value(hasItem(DEFAULT_FIRST_SOC.doubleValue())))
            .andExpect(jsonPath("$.[*].finalSoc").value(hasItem(DEFAULT_FINAL_SOC.doubleValue())))
            .andExpect(jsonPath("$.[*].firstDistance").value(hasItem(DEFAULT_FIRST_DISTANCE)))
            .andExpect(jsonPath("$.[*].finalDistance").value(hasItem(DEFAULT_FINAL_DISTANCE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].cpoName").value(hasItem(DEFAULT_CPO_NAME)))
            .andExpect(jsonPath("$.[*].powerType").value(hasItem(DEFAULT_POWER_TYPE)))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST)))
            .andExpect(jsonPath("$.[*].totalEnergy").value(hasItem(DEFAULT_TOTAL_ENERGY)))
            .andExpect(jsonPath("$.[*].totalTime").value(hasItem(DEFAULT_TOTAL_TIME)));
    }

    @Test
    @Transactional
    void getChargingUseHistory() throws Exception {
        // Initialize the database
        chargingUseHistoryRepository.saveAndFlush(chargingUseHistory);

        // Get the chargingUseHistory
        restChargingUseHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, chargingUseHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chargingUseHistory.getId().intValue()))
            .andExpect(jsonPath("$.chargingUseDisplayId").value(DEFAULT_CHARGING_USE_DISPLAY_ID))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID))
            .andExpect(jsonPath("$.evseId").value(DEFAULT_EVSE_ID))
            .andExpect(jsonPath("$.connectorId").value(DEFAULT_CONNECTOR_ID))
            .andExpect(jsonPath("$.memNo").value(DEFAULT_MEM_NO))
            .andExpect(jsonPath("$.carCd").value(DEFAULT_CAR_CD))
            .andExpect(jsonPath("$.startDateTime").value(sameInstant(DEFAULT_START_DATE_TIME)))
            .andExpect(jsonPath("$.endDateTime").value(sameInstant(DEFAULT_END_DATE_TIME)))
            .andExpect(jsonPath("$.firstSoc").value(DEFAULT_FIRST_SOC.doubleValue()))
            .andExpect(jsonPath("$.finalSoc").value(DEFAULT_FINAL_SOC.doubleValue()))
            .andExpect(jsonPath("$.firstDistance").value(DEFAULT_FIRST_DISTANCE))
            .andExpect(jsonPath("$.finalDistance").value(DEFAULT_FINAL_DISTANCE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.cpoName").value(DEFAULT_CPO_NAME))
            .andExpect(jsonPath("$.powerType").value(DEFAULT_POWER_TYPE))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST))
            .andExpect(jsonPath("$.totalEnergy").value(DEFAULT_TOTAL_ENERGY))
            .andExpect(jsonPath("$.totalTime").value(DEFAULT_TOTAL_TIME));
    }

    @Test
    @Transactional
    void getNonExistingChargingUseHistory() throws Exception {
        // Get the chargingUseHistory
        restChargingUseHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChargingUseHistory() throws Exception {
        // Initialize the database
        chargingUseHistoryRepository.saveAndFlush(chargingUseHistory);

        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();

        // Update the chargingUseHistory
        ChargingUseHistory updatedChargingUseHistory = chargingUseHistoryRepository.findById(chargingUseHistory.getId()).get();
        // Disconnect from session so that the updates on updatedChargingUseHistory are not directly saved in db
        em.detach(updatedChargingUseHistory);
        updatedChargingUseHistory
            .chargingUseDisplayId(UPDATED_CHARGING_USE_DISPLAY_ID)
            .locationId(UPDATED_LOCATION_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .memNo(UPDATED_MEM_NO)
            .carCd(UPDATED_CAR_CD)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .firstSoc(UPDATED_FIRST_SOC)
            .finalSoc(UPDATED_FINAL_SOC)
            .firstDistance(UPDATED_FIRST_DISTANCE)
            .finalDistance(UPDATED_FINAL_DISTANCE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .cpoName(UPDATED_CPO_NAME)
            .powerType(UPDATED_POWER_TYPE)
            .totalCost(UPDATED_TOTAL_COST)
            .totalEnergy(UPDATED_TOTAL_ENERGY)
            .totalTime(UPDATED_TOTAL_TIME);
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(updatedChargingUseHistory);

        restChargingUseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargingUseHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
        ChargingUseHistory testChargingUseHistory = chargingUseHistoryList.get(chargingUseHistoryList.size() - 1);
        assertThat(testChargingUseHistory.getChargingUseDisplayId()).isEqualTo(UPDATED_CHARGING_USE_DISPLAY_ID);
        assertThat(testChargingUseHistory.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testChargingUseHistory.getEvseId()).isEqualTo(UPDATED_EVSE_ID);
        assertThat(testChargingUseHistory.getConnectorId()).isEqualTo(UPDATED_CONNECTOR_ID);
        assertThat(testChargingUseHistory.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testChargingUseHistory.getCarCd()).isEqualTo(UPDATED_CAR_CD);
        assertThat(testChargingUseHistory.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testChargingUseHistory.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testChargingUseHistory.getFirstSoc()).isEqualTo(UPDATED_FIRST_SOC);
        assertThat(testChargingUseHistory.getFinalSoc()).isEqualTo(UPDATED_FINAL_SOC);
        assertThat(testChargingUseHistory.getFirstDistance()).isEqualTo(UPDATED_FIRST_DISTANCE);
        assertThat(testChargingUseHistory.getFinalDistance()).isEqualTo(UPDATED_FINAL_DISTANCE);
        assertThat(testChargingUseHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChargingUseHistory.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testChargingUseHistory.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testChargingUseHistory.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testChargingUseHistory.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingUseHistory.getPowerType()).isEqualTo(UPDATED_POWER_TYPE);
        assertThat(testChargingUseHistory.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testChargingUseHistory.getTotalEnergy()).isEqualTo(UPDATED_TOTAL_ENERGY);
        assertThat(testChargingUseHistory.getTotalTime()).isEqualTo(UPDATED_TOTAL_TIME);
    }

    @Test
    @Transactional
    void putNonExistingChargingUseHistory() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();
        chargingUseHistory.setId(count.incrementAndGet());

        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingUseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargingUseHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChargingUseHistory() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();
        chargingUseHistory.setId(count.incrementAndGet());

        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChargingUseHistory() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();
        chargingUseHistory.setId(count.incrementAndGet());

        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChargingUseHistoryWithPatch() throws Exception {
        // Initialize the database
        chargingUseHistoryRepository.saveAndFlush(chargingUseHistory);

        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();

        // Update the chargingUseHistory using partial update
        ChargingUseHistory partialUpdatedChargingUseHistory = new ChargingUseHistory();
        partialUpdatedChargingUseHistory.setId(chargingUseHistory.getId());

        partialUpdatedChargingUseHistory
            .chargingUseDisplayId(UPDATED_CHARGING_USE_DISPLAY_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .endDateTime(UPDATED_END_DATE_TIME)
            .firstSoc(UPDATED_FIRST_SOC)
            .firstDistance(UPDATED_FIRST_DISTANCE)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .cpoName(UPDATED_CPO_NAME)
            .totalEnergy(UPDATED_TOTAL_ENERGY);

        restChargingUseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChargingUseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChargingUseHistory))
            )
            .andExpect(status().isOk());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
        ChargingUseHistory testChargingUseHistory = chargingUseHistoryList.get(chargingUseHistoryList.size() - 1);
        assertThat(testChargingUseHistory.getChargingUseDisplayId()).isEqualTo(UPDATED_CHARGING_USE_DISPLAY_ID);
        assertThat(testChargingUseHistory.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testChargingUseHistory.getEvseId()).isEqualTo(UPDATED_EVSE_ID);
        assertThat(testChargingUseHistory.getConnectorId()).isEqualTo(UPDATED_CONNECTOR_ID);
        assertThat(testChargingUseHistory.getMemNo()).isEqualTo(DEFAULT_MEM_NO);
        assertThat(testChargingUseHistory.getCarCd()).isEqualTo(DEFAULT_CAR_CD);
        assertThat(testChargingUseHistory.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testChargingUseHistory.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testChargingUseHistory.getFirstSoc()).isEqualTo(UPDATED_FIRST_SOC);
        assertThat(testChargingUseHistory.getFinalSoc()).isEqualTo(DEFAULT_FINAL_SOC);
        assertThat(testChargingUseHistory.getFirstDistance()).isEqualTo(UPDATED_FIRST_DISTANCE);
        assertThat(testChargingUseHistory.getFinalDistance()).isEqualTo(DEFAULT_FINAL_DISTANCE);
        assertThat(testChargingUseHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChargingUseHistory.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testChargingUseHistory.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testChargingUseHistory.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testChargingUseHistory.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingUseHistory.getPowerType()).isEqualTo(DEFAULT_POWER_TYPE);
        assertThat(testChargingUseHistory.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testChargingUseHistory.getTotalEnergy()).isEqualTo(UPDATED_TOTAL_ENERGY);
        assertThat(testChargingUseHistory.getTotalTime()).isEqualTo(DEFAULT_TOTAL_TIME);
    }

    @Test
    @Transactional
    void fullUpdateChargingUseHistoryWithPatch() throws Exception {
        // Initialize the database
        chargingUseHistoryRepository.saveAndFlush(chargingUseHistory);

        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();

        // Update the chargingUseHistory using partial update
        ChargingUseHistory partialUpdatedChargingUseHistory = new ChargingUseHistory();
        partialUpdatedChargingUseHistory.setId(chargingUseHistory.getId());

        partialUpdatedChargingUseHistory
            .chargingUseDisplayId(UPDATED_CHARGING_USE_DISPLAY_ID)
            .locationId(UPDATED_LOCATION_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .memNo(UPDATED_MEM_NO)
            .carCd(UPDATED_CAR_CD)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .firstSoc(UPDATED_FIRST_SOC)
            .finalSoc(UPDATED_FINAL_SOC)
            .firstDistance(UPDATED_FIRST_DISTANCE)
            .finalDistance(UPDATED_FINAL_DISTANCE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .cpoName(UPDATED_CPO_NAME)
            .powerType(UPDATED_POWER_TYPE)
            .totalCost(UPDATED_TOTAL_COST)
            .totalEnergy(UPDATED_TOTAL_ENERGY)
            .totalTime(UPDATED_TOTAL_TIME);

        restChargingUseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChargingUseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChargingUseHistory))
            )
            .andExpect(status().isOk());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
        ChargingUseHistory testChargingUseHistory = chargingUseHistoryList.get(chargingUseHistoryList.size() - 1);
        assertThat(testChargingUseHistory.getChargingUseDisplayId()).isEqualTo(UPDATED_CHARGING_USE_DISPLAY_ID);
        assertThat(testChargingUseHistory.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testChargingUseHistory.getEvseId()).isEqualTo(UPDATED_EVSE_ID);
        assertThat(testChargingUseHistory.getConnectorId()).isEqualTo(UPDATED_CONNECTOR_ID);
        assertThat(testChargingUseHistory.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testChargingUseHistory.getCarCd()).isEqualTo(UPDATED_CAR_CD);
        assertThat(testChargingUseHistory.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testChargingUseHistory.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testChargingUseHistory.getFirstSoc()).isEqualTo(UPDATED_FIRST_SOC);
        assertThat(testChargingUseHistory.getFinalSoc()).isEqualTo(UPDATED_FINAL_SOC);
        assertThat(testChargingUseHistory.getFirstDistance()).isEqualTo(UPDATED_FIRST_DISTANCE);
        assertThat(testChargingUseHistory.getFinalDistance()).isEqualTo(UPDATED_FINAL_DISTANCE);
        assertThat(testChargingUseHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChargingUseHistory.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testChargingUseHistory.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testChargingUseHistory.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testChargingUseHistory.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingUseHistory.getPowerType()).isEqualTo(UPDATED_POWER_TYPE);
        assertThat(testChargingUseHistory.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testChargingUseHistory.getTotalEnergy()).isEqualTo(UPDATED_TOTAL_ENERGY);
        assertThat(testChargingUseHistory.getTotalTime()).isEqualTo(UPDATED_TOTAL_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingChargingUseHistory() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();
        chargingUseHistory.setId(count.incrementAndGet());

        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingUseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chargingUseHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChargingUseHistory() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();
        chargingUseHistory.setId(count.incrementAndGet());

        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChargingUseHistory() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseHistoryRepository.findAll().size();
        chargingUseHistory.setId(count.incrementAndGet());

        // Create the ChargingUseHistory
        ChargingUseHistoryDTO chargingUseHistoryDTO = chargingUseHistoryMapper.toDto(chargingUseHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChargingUseHistory in the database
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChargingUseHistory() throws Exception {
        // Initialize the database
        chargingUseHistoryRepository.saveAndFlush(chargingUseHistory);

        int databaseSizeBeforeDelete = chargingUseHistoryRepository.findAll().size();

        // Delete the chargingUseHistory
        restChargingUseHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, chargingUseHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChargingUseHistory> chargingUseHistoryList = chargingUseHistoryRepository.findAll();
        assertThat(chargingUseHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
