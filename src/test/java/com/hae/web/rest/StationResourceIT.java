package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Station;
import com.hae.domain.enumeration.Provider;
import com.hae.repository.StationRepository;
import com.hae.service.dto.StationDTO;
import com.hae.service.mapper.StationMapper;
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
 * Integration tests for the {@link StationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StationResourceIT {

    private static final Integer DEFAULT_SELLER_ID = 1;
    private static final Integer UPDATED_SELLER_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITIDE = 1D;
    private static final Double UPDATED_LONGITIDE = 2D;

    private static final Integer DEFAULT_ICON = 1;
    private static final Integer UPDATED_ICON = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_OPEN_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_HOURS = "BBBBBBBBBB";

    private static final Provider DEFAULT_PROVIDER = Provider.Virta;
    private static final Provider UPDATED_PROVIDER = Provider.Hubject;

    private static final String DEFAULT_ALERT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_REMOVED = false;
    private static final Boolean UPDATED_IS_REMOVED = true;

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    private static final String ENTITY_API_URL = "/api/stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStationMockMvc;

    private Station station;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createEntity(EntityManager em) {
        Station station = new Station()
            .sellerId(DEFAULT_SELLER_ID)
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitide(DEFAULT_LONGITIDE)
            .icon(DEFAULT_ICON)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .openHours(DEFAULT_OPEN_HOURS)
            .provider(DEFAULT_PROVIDER)
            .alertMessage(DEFAULT_ALERT_MESSAGE)
            .isRemoved(DEFAULT_IS_REMOVED)
            .isPrivate(DEFAULT_IS_PRIVATE);
        return station;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createUpdatedEntity(EntityManager em) {
        Station station = new Station()
            .sellerId(UPDATED_SELLER_ID)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitide(UPDATED_LONGITIDE)
            .icon(UPDATED_ICON)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .openHours(UPDATED_OPEN_HOURS)
            .provider(UPDATED_PROVIDER)
            .alertMessage(UPDATED_ALERT_MESSAGE)
            .isRemoved(UPDATED_IS_REMOVED)
            .isPrivate(UPDATED_IS_PRIVATE);
        return station;
    }

    @BeforeEach
    public void initTest() {
        station = createEntity(em);
    }

    @Test
    @Transactional
    void createStation() throws Exception {
        int databaseSizeBeforeCreate = stationRepository.findAll().size();
        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);
        restStationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isCreated());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate + 1);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getSellerId()).isEqualTo(DEFAULT_SELLER_ID);
        assertThat(testStation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStation.getLongitide()).isEqualTo(DEFAULT_LONGITIDE);
        assertThat(testStation.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testStation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testStation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStation.getOpenHours()).isEqualTo(DEFAULT_OPEN_HOURS);
        assertThat(testStation.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testStation.getAlertMessage()).isEqualTo(DEFAULT_ALERT_MESSAGE);
        assertThat(testStation.getIsRemoved()).isEqualTo(DEFAULT_IS_REMOVED);
        assertThat(testStation.getIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
    }

    @Test
    @Transactional
    void createStationWithExistingId() throws Exception {
        // Create the Station with an existing ID
        station.setId(1L);
        StationDTO stationDTO = stationMapper.toDto(station);

        int databaseSizeBeforeCreate = stationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStations() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList
        restStationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(station.getId().intValue())))
            .andExpect(jsonPath("$.[*].sellerId").value(hasItem(DEFAULT_SELLER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitide").value(hasItem(DEFAULT_LONGITIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].openHours").value(hasItem(DEFAULT_OPEN_HOURS)))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].alertMessage").value(hasItem(DEFAULT_ALERT_MESSAGE)))
            .andExpect(jsonPath("$.[*].isRemoved").value(hasItem(DEFAULT_IS_REMOVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())));
    }

    @Test
    @Transactional
    void getStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get the station
        restStationMockMvc
            .perform(get(ENTITY_API_URL_ID, station.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(station.getId().intValue()))
            .andExpect(jsonPath("$.sellerId").value(DEFAULT_SELLER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitide").value(DEFAULT_LONGITIDE.doubleValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.openHours").value(DEFAULT_OPEN_HOURS))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER.toString()))
            .andExpect(jsonPath("$.alertMessage").value(DEFAULT_ALERT_MESSAGE))
            .andExpect(jsonPath("$.isRemoved").value(DEFAULT_IS_REMOVED.booleanValue()))
            .andExpect(jsonPath("$.isPrivate").value(DEFAULT_IS_PRIVATE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingStation() throws Exception {
        // Get the station
        restStationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station
        Station updatedStation = stationRepository.findById(station.getId()).get();
        // Disconnect from session so that the updates on updatedStation are not directly saved in db
        em.detach(updatedStation);
        updatedStation
            .sellerId(UPDATED_SELLER_ID)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitide(UPDATED_LONGITIDE)
            .icon(UPDATED_ICON)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .openHours(UPDATED_OPEN_HOURS)
            .provider(UPDATED_PROVIDER)
            .alertMessage(UPDATED_ALERT_MESSAGE)
            .isRemoved(UPDATED_IS_REMOVED)
            .isPrivate(UPDATED_IS_PRIVATE);
        StationDTO stationDTO = stationMapper.toDto(updatedStation);

        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testStation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStation.getLongitide()).isEqualTo(UPDATED_LONGITIDE);
        assertThat(testStation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testStation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStation.getOpenHours()).isEqualTo(UPDATED_OPEN_HOURS);
        assertThat(testStation.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testStation.getAlertMessage()).isEqualTo(UPDATED_ALERT_MESSAGE);
        assertThat(testStation.getIsRemoved()).isEqualTo(UPDATED_IS_REMOVED);
        assertThat(testStation.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    void putNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .sellerId(UPDATED_SELLER_ID)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitide(UPDATED_LONGITIDE)
            .address(UPDATED_ADDRESS)
            .provider(UPDATED_PROVIDER);

        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testStation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStation.getLongitide()).isEqualTo(UPDATED_LONGITIDE);
        assertThat(testStation.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testStation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStation.getOpenHours()).isEqualTo(DEFAULT_OPEN_HOURS);
        assertThat(testStation.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testStation.getAlertMessage()).isEqualTo(DEFAULT_ALERT_MESSAGE);
        assertThat(testStation.getIsRemoved()).isEqualTo(DEFAULT_IS_REMOVED);
        assertThat(testStation.getIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
    }

    @Test
    @Transactional
    void fullUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .sellerId(UPDATED_SELLER_ID)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitide(UPDATED_LONGITIDE)
            .icon(UPDATED_ICON)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .openHours(UPDATED_OPEN_HOURS)
            .provider(UPDATED_PROVIDER)
            .alertMessage(UPDATED_ALERT_MESSAGE)
            .isRemoved(UPDATED_IS_REMOVED)
            .isPrivate(UPDATED_IS_PRIVATE);

        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testStation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStation.getLongitide()).isEqualTo(UPDATED_LONGITIDE);
        assertThat(testStation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testStation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStation.getOpenHours()).isEqualTo(UPDATED_OPEN_HOURS);
        assertThat(testStation.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testStation.getAlertMessage()).isEqualTo(UPDATED_ALERT_MESSAGE);
        assertThat(testStation.getIsRemoved()).isEqualTo(UPDATED_IS_REMOVED);
        assertThat(testStation.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    void patchNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeDelete = stationRepository.findAll().size();

        // Delete the station
        restStationMockMvc
            .perform(delete(ENTITY_API_URL_ID, station.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
