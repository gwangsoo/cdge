package com.hae.ecs.domain.charging.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.ecs.domain.charging.IntegrationTest;
import com.hae.ecs.domain.charging.domain.ChargingUseDisplay;
import com.hae.ecs.domain.charging.domain.enumeration.ChargingStatus;
import com.hae.ecs.domain.charging.repository.ChargingUseDisplayRepository;
import com.hae.ecs.domain.charging.service.dto.ChargingUseDisplayDTO;
import com.hae.ecs.domain.charging.service.mapper.ChargingUseDisplayMapper;
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
 * Integration tests for the {@link ChargingUseDisplayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChargingUseDisplayResourceIT {

    private static final String DEFAULT_LOCATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EVSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVSE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTOR_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POSSIBLE_CHARING_FLAG = false;
    private static final Boolean UPDATED_POSSIBLE_CHARING_FLAG = true;

    private static final String DEFAULT_INSTEAD_CHARING = "AAAAAAAAAA";
    private static final String UPDATED_INSTEAD_CHARING = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OPENING_TIMES = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_TIMES = "BBBBBBBBBB";

    private static final ChargingStatus DEFAULT_STATUS = ChargingStatus.CHARGING;
    private static final ChargingStatus UPDATED_STATUS = ChargingStatus.OUTOFORDER;

    private static final String DEFAULT_CPO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CPO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POWER_TYPE = "AAAAA";
    private static final String UPDATED_POWER_TYPE = "BBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_VAT = 1;
    private static final Integer UPDATED_VAT = 2;

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/charging-use-displays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChargingUseDisplayRepository chargingUseDisplayRepository;

    @Autowired
    private ChargingUseDisplayMapper chargingUseDisplayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargingUseDisplayMockMvc;

    private ChargingUseDisplay chargingUseDisplay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingUseDisplay createEntity(EntityManager em) {
        ChargingUseDisplay chargingUseDisplay = new ChargingUseDisplay()
            .locationId(DEFAULT_LOCATION_ID)
            .evseId(DEFAULT_EVSE_ID)
            .connectorId(DEFAULT_CONNECTOR_ID)
            .possibleCharingFlag(DEFAULT_POSSIBLE_CHARING_FLAG)
            .insteadCharing(DEFAULT_INSTEAD_CHARING)
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .openingTimes(DEFAULT_OPENING_TIMES)
            .status(DEFAULT_STATUS)
            .cpoName(DEFAULT_CPO_NAME)
            .powerType(DEFAULT_POWER_TYPE)
            .price(DEFAULT_PRICE)
            .vat(DEFAULT_VAT)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .paymentMethod(DEFAULT_PAYMENT_METHOD);
        return chargingUseDisplay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingUseDisplay createUpdatedEntity(EntityManager em) {
        ChargingUseDisplay chargingUseDisplay = new ChargingUseDisplay()
            .locationId(UPDATED_LOCATION_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .possibleCharingFlag(UPDATED_POSSIBLE_CHARING_FLAG)
            .insteadCharing(UPDATED_INSTEAD_CHARING)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .openingTimes(UPDATED_OPENING_TIMES)
            .status(UPDATED_STATUS)
            .cpoName(UPDATED_CPO_NAME)
            .powerType(UPDATED_POWER_TYPE)
            .price(UPDATED_PRICE)
            .vat(UPDATED_VAT)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .paymentMethod(UPDATED_PAYMENT_METHOD);
        return chargingUseDisplay;
    }

    @BeforeEach
    public void initTest() {
        chargingUseDisplay = createEntity(em);
    }

    @Test
    @Transactional
    void createChargingUseDisplay() throws Exception {
        int databaseSizeBeforeCreate = chargingUseDisplayRepository.findAll().size();
        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);
        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeCreate + 1);
        ChargingUseDisplay testChargingUseDisplay = chargingUseDisplayList.get(chargingUseDisplayList.size() - 1);
        assertThat(testChargingUseDisplay.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testChargingUseDisplay.getEvseId()).isEqualTo(DEFAULT_EVSE_ID);
        assertThat(testChargingUseDisplay.getConnectorId()).isEqualTo(DEFAULT_CONNECTOR_ID);
        assertThat(testChargingUseDisplay.getPossibleCharingFlag()).isEqualTo(DEFAULT_POSSIBLE_CHARING_FLAG);
        assertThat(testChargingUseDisplay.getInsteadCharing()).isEqualTo(DEFAULT_INSTEAD_CHARING);
        assertThat(testChargingUseDisplay.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChargingUseDisplay.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testChargingUseDisplay.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testChargingUseDisplay.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testChargingUseDisplay.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testChargingUseDisplay.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testChargingUseDisplay.getOpeningTimes()).isEqualTo(DEFAULT_OPENING_TIMES);
        assertThat(testChargingUseDisplay.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testChargingUseDisplay.getCpoName()).isEqualTo(DEFAULT_CPO_NAME);
        assertThat(testChargingUseDisplay.getPowerType()).isEqualTo(DEFAULT_POWER_TYPE);
        assertThat(testChargingUseDisplay.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testChargingUseDisplay.getVat()).isEqualTo(DEFAULT_VAT);
        assertThat(testChargingUseDisplay.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testChargingUseDisplay.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void createChargingUseDisplayWithExistingId() throws Exception {
        // Create the ChargingUseDisplay with an existing ID
        chargingUseDisplay.setId(1L);
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        int databaseSizeBeforeCreate = chargingUseDisplayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setLocationId(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEvseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setEvseId(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConnectorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setConnectorId(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPossibleCharingFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setPossibleCharingFlag(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setAddress(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setCity(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setCpoName(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPowerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setPowerType(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setPrice(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setPhoneNumber(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargingUseDisplayRepository.findAll().size();
        // set the field null
        chargingUseDisplay.setPaymentMethod(null);

        // Create the ChargingUseDisplay, which fails.
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChargingUseDisplays() throws Exception {
        // Initialize the database
        chargingUseDisplayRepository.saveAndFlush(chargingUseDisplay);

        // Get all the chargingUseDisplayList
        restChargingUseDisplayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingUseDisplay.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID)))
            .andExpect(jsonPath("$.[*].evseId").value(hasItem(DEFAULT_EVSE_ID)))
            .andExpect(jsonPath("$.[*].connectorId").value(hasItem(DEFAULT_CONNECTOR_ID)))
            .andExpect(jsonPath("$.[*].possibleCharingFlag").value(hasItem(DEFAULT_POSSIBLE_CHARING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].insteadCharing").value(hasItem(DEFAULT_INSTEAD_CHARING)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].openingTimes").value(hasItem(DEFAULT_OPENING_TIMES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].cpoName").value(hasItem(DEFAULT_CPO_NAME)))
            .andExpect(jsonPath("$.[*].powerType").value(hasItem(DEFAULT_POWER_TYPE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)));
    }

    @Test
    @Transactional
    void getChargingUseDisplay() throws Exception {
        // Initialize the database
        chargingUseDisplayRepository.saveAndFlush(chargingUseDisplay);

        // Get the chargingUseDisplay
        restChargingUseDisplayMockMvc
            .perform(get(ENTITY_API_URL_ID, chargingUseDisplay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chargingUseDisplay.getId().intValue()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID))
            .andExpect(jsonPath("$.evseId").value(DEFAULT_EVSE_ID))
            .andExpect(jsonPath("$.connectorId").value(DEFAULT_CONNECTOR_ID))
            .andExpect(jsonPath("$.possibleCharingFlag").value(DEFAULT_POSSIBLE_CHARING_FLAG.booleanValue()))
            .andExpect(jsonPath("$.insteadCharing").value(DEFAULT_INSTEAD_CHARING))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.openingTimes").value(DEFAULT_OPENING_TIMES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.cpoName").value(DEFAULT_CPO_NAME))
            .andExpect(jsonPath("$.powerType").value(DEFAULT_POWER_TYPE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD));
    }

    @Test
    @Transactional
    void getNonExistingChargingUseDisplay() throws Exception {
        // Get the chargingUseDisplay
        restChargingUseDisplayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChargingUseDisplay() throws Exception {
        // Initialize the database
        chargingUseDisplayRepository.saveAndFlush(chargingUseDisplay);

        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();

        // Update the chargingUseDisplay
        ChargingUseDisplay updatedChargingUseDisplay = chargingUseDisplayRepository.findById(chargingUseDisplay.getId()).get();
        // Disconnect from session so that the updates on updatedChargingUseDisplay are not directly saved in db
        em.detach(updatedChargingUseDisplay);
        updatedChargingUseDisplay
            .locationId(UPDATED_LOCATION_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .possibleCharingFlag(UPDATED_POSSIBLE_CHARING_FLAG)
            .insteadCharing(UPDATED_INSTEAD_CHARING)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .openingTimes(UPDATED_OPENING_TIMES)
            .status(UPDATED_STATUS)
            .cpoName(UPDATED_CPO_NAME)
            .powerType(UPDATED_POWER_TYPE)
            .price(UPDATED_PRICE)
            .vat(UPDATED_VAT)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .paymentMethod(UPDATED_PAYMENT_METHOD);
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(updatedChargingUseDisplay);

        restChargingUseDisplayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargingUseDisplayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
        ChargingUseDisplay testChargingUseDisplay = chargingUseDisplayList.get(chargingUseDisplayList.size() - 1);
        assertThat(testChargingUseDisplay.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testChargingUseDisplay.getEvseId()).isEqualTo(UPDATED_EVSE_ID);
        assertThat(testChargingUseDisplay.getConnectorId()).isEqualTo(UPDATED_CONNECTOR_ID);
        assertThat(testChargingUseDisplay.getPossibleCharingFlag()).isEqualTo(UPDATED_POSSIBLE_CHARING_FLAG);
        assertThat(testChargingUseDisplay.getInsteadCharing()).isEqualTo(UPDATED_INSTEAD_CHARING);
        assertThat(testChargingUseDisplay.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChargingUseDisplay.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testChargingUseDisplay.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testChargingUseDisplay.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testChargingUseDisplay.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testChargingUseDisplay.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testChargingUseDisplay.getOpeningTimes()).isEqualTo(UPDATED_OPENING_TIMES);
        assertThat(testChargingUseDisplay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChargingUseDisplay.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingUseDisplay.getPowerType()).isEqualTo(UPDATED_POWER_TYPE);
        assertThat(testChargingUseDisplay.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testChargingUseDisplay.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testChargingUseDisplay.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testChargingUseDisplay.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void putNonExistingChargingUseDisplay() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();
        chargingUseDisplay.setId(count.incrementAndGet());

        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingUseDisplayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargingUseDisplayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChargingUseDisplay() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();
        chargingUseDisplay.setId(count.incrementAndGet());

        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseDisplayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChargingUseDisplay() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();
        chargingUseDisplay.setId(count.incrementAndGet());

        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseDisplayMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChargingUseDisplayWithPatch() throws Exception {
        // Initialize the database
        chargingUseDisplayRepository.saveAndFlush(chargingUseDisplay);

        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();

        // Update the chargingUseDisplay using partial update
        ChargingUseDisplay partialUpdatedChargingUseDisplay = new ChargingUseDisplay();
        partialUpdatedChargingUseDisplay.setId(chargingUseDisplay.getId());

        partialUpdatedChargingUseDisplay
            .evseId(UPDATED_EVSE_ID)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .openingTimes(UPDATED_OPENING_TIMES)
            .status(UPDATED_STATUS)
            .cpoName(UPDATED_CPO_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restChargingUseDisplayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChargingUseDisplay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChargingUseDisplay))
            )
            .andExpect(status().isOk());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
        ChargingUseDisplay testChargingUseDisplay = chargingUseDisplayList.get(chargingUseDisplayList.size() - 1);
        assertThat(testChargingUseDisplay.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testChargingUseDisplay.getEvseId()).isEqualTo(UPDATED_EVSE_ID);
        assertThat(testChargingUseDisplay.getConnectorId()).isEqualTo(DEFAULT_CONNECTOR_ID);
        assertThat(testChargingUseDisplay.getPossibleCharingFlag()).isEqualTo(DEFAULT_POSSIBLE_CHARING_FLAG);
        assertThat(testChargingUseDisplay.getInsteadCharing()).isEqualTo(DEFAULT_INSTEAD_CHARING);
        assertThat(testChargingUseDisplay.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChargingUseDisplay.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testChargingUseDisplay.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testChargingUseDisplay.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testChargingUseDisplay.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testChargingUseDisplay.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testChargingUseDisplay.getOpeningTimes()).isEqualTo(UPDATED_OPENING_TIMES);
        assertThat(testChargingUseDisplay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChargingUseDisplay.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingUseDisplay.getPowerType()).isEqualTo(DEFAULT_POWER_TYPE);
        assertThat(testChargingUseDisplay.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testChargingUseDisplay.getVat()).isEqualTo(DEFAULT_VAT);
        assertThat(testChargingUseDisplay.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testChargingUseDisplay.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void fullUpdateChargingUseDisplayWithPatch() throws Exception {
        // Initialize the database
        chargingUseDisplayRepository.saveAndFlush(chargingUseDisplay);

        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();

        // Update the chargingUseDisplay using partial update
        ChargingUseDisplay partialUpdatedChargingUseDisplay = new ChargingUseDisplay();
        partialUpdatedChargingUseDisplay.setId(chargingUseDisplay.getId());

        partialUpdatedChargingUseDisplay
            .locationId(UPDATED_LOCATION_ID)
            .evseId(UPDATED_EVSE_ID)
            .connectorId(UPDATED_CONNECTOR_ID)
            .possibleCharingFlag(UPDATED_POSSIBLE_CHARING_FLAG)
            .insteadCharing(UPDATED_INSTEAD_CHARING)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .openingTimes(UPDATED_OPENING_TIMES)
            .status(UPDATED_STATUS)
            .cpoName(UPDATED_CPO_NAME)
            .powerType(UPDATED_POWER_TYPE)
            .price(UPDATED_PRICE)
            .vat(UPDATED_VAT)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .paymentMethod(UPDATED_PAYMENT_METHOD);

        restChargingUseDisplayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChargingUseDisplay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChargingUseDisplay))
            )
            .andExpect(status().isOk());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
        ChargingUseDisplay testChargingUseDisplay = chargingUseDisplayList.get(chargingUseDisplayList.size() - 1);
        assertThat(testChargingUseDisplay.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testChargingUseDisplay.getEvseId()).isEqualTo(UPDATED_EVSE_ID);
        assertThat(testChargingUseDisplay.getConnectorId()).isEqualTo(UPDATED_CONNECTOR_ID);
        assertThat(testChargingUseDisplay.getPossibleCharingFlag()).isEqualTo(UPDATED_POSSIBLE_CHARING_FLAG);
        assertThat(testChargingUseDisplay.getInsteadCharing()).isEqualTo(UPDATED_INSTEAD_CHARING);
        assertThat(testChargingUseDisplay.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChargingUseDisplay.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testChargingUseDisplay.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testChargingUseDisplay.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testChargingUseDisplay.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testChargingUseDisplay.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testChargingUseDisplay.getOpeningTimes()).isEqualTo(UPDATED_OPENING_TIMES);
        assertThat(testChargingUseDisplay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChargingUseDisplay.getCpoName()).isEqualTo(UPDATED_CPO_NAME);
        assertThat(testChargingUseDisplay.getPowerType()).isEqualTo(UPDATED_POWER_TYPE);
        assertThat(testChargingUseDisplay.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testChargingUseDisplay.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testChargingUseDisplay.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testChargingUseDisplay.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void patchNonExistingChargingUseDisplay() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();
        chargingUseDisplay.setId(count.incrementAndGet());

        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingUseDisplayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chargingUseDisplayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChargingUseDisplay() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();
        chargingUseDisplay.setId(count.incrementAndGet());

        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseDisplayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChargingUseDisplay() throws Exception {
        int databaseSizeBeforeUpdate = chargingUseDisplayRepository.findAll().size();
        chargingUseDisplay.setId(count.incrementAndGet());

        // Create the ChargingUseDisplay
        ChargingUseDisplayDTO chargingUseDisplayDTO = chargingUseDisplayMapper.toDto(chargingUseDisplay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargingUseDisplayMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chargingUseDisplayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChargingUseDisplay in the database
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChargingUseDisplay() throws Exception {
        // Initialize the database
        chargingUseDisplayRepository.saveAndFlush(chargingUseDisplay);

        int databaseSizeBeforeDelete = chargingUseDisplayRepository.findAll().size();

        // Delete the chargingUseDisplay
        restChargingUseDisplayMockMvc
            .perform(delete(ENTITY_API_URL_ID, chargingUseDisplay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChargingUseDisplay> chargingUseDisplayList = chargingUseDisplayRepository.findAll();
        assertThat(chargingUseDisplayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
