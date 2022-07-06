package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Evse;
import com.hae.repository.EvseRepository;
import com.hae.service.dto.EvseDTO;
import com.hae.service.mapper.EvseMapper;
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
 * Integration tests for the {@link EvseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvseResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final Boolean DEFAULT_RESERVABLE = false;
    private static final Boolean UPDATED_RESERVABLE = true;

    private static final Boolean DEFAULT_ONE_TIME_PAYMENT = false;
    private static final Boolean UPDATED_ONE_TIME_PAYMENT = true;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String ENTITY_API_URL = "/api/evses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvseRepository evseRepository;

    @Autowired
    private EvseMapper evseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvseMockMvc;

    private Evse evse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evse createEntity(EntityManager em) {
        Evse evse = new Evse()
            .groupName(DEFAULT_GROUP_NAME)
            .available(DEFAULT_AVAILABLE)
            .reservable(DEFAULT_RESERVABLE)
            .oneTimePayment(DEFAULT_ONE_TIME_PAYMENT)
            .status(DEFAULT_STATUS);
        return evse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evse createUpdatedEntity(EntityManager em) {
        Evse evse = new Evse()
            .groupName(UPDATED_GROUP_NAME)
            .available(UPDATED_AVAILABLE)
            .reservable(UPDATED_RESERVABLE)
            .oneTimePayment(UPDATED_ONE_TIME_PAYMENT)
            .status(UPDATED_STATUS);
        return evse;
    }

    @BeforeEach
    public void initTest() {
        evse = createEntity(em);
    }

    @Test
    @Transactional
    void createEvse() throws Exception {
        int databaseSizeBeforeCreate = evseRepository.findAll().size();
        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);
        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isCreated());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeCreate + 1);
        Evse testEvse = evseList.get(evseList.size() - 1);
        assertThat(testEvse.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testEvse.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testEvse.getReservable()).isEqualTo(DEFAULT_RESERVABLE);
        assertThat(testEvse.getOneTimePayment()).isEqualTo(DEFAULT_ONE_TIME_PAYMENT);
        assertThat(testEvse.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createEvseWithExistingId() throws Exception {
        // Create the Evse with an existing ID
        evse.setId(1L);
        EvseDTO evseDTO = evseMapper.toDto(evse);

        int databaseSizeBeforeCreate = evseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = evseRepository.findAll().size();
        // set the field null
        evse.setAvailable(null);

        // Create the Evse, which fails.
        EvseDTO evseDTO = evseMapper.toDto(evse);

        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReservableIsRequired() throws Exception {
        int databaseSizeBeforeTest = evseRepository.findAll().size();
        // set the field null
        evse.setReservable(null);

        // Create the Evse, which fails.
        EvseDTO evseDTO = evseMapper.toDto(evse);

        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOneTimePaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = evseRepository.findAll().size();
        // set the field null
        evse.setOneTimePayment(null);

        // Create the Evse, which fails.
        EvseDTO evseDTO = evseMapper.toDto(evse);

        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = evseRepository.findAll().size();
        // set the field null
        evse.setStatus(null);

        // Create the Evse, which fails.
        EvseDTO evseDTO = evseMapper.toDto(evse);

        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvses() throws Exception {
        // Initialize the database
        evseRepository.saveAndFlush(evse);

        // Get all the evseList
        restEvseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evse.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].reservable").value(hasItem(DEFAULT_RESERVABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].oneTimePayment").value(hasItem(DEFAULT_ONE_TIME_PAYMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getEvse() throws Exception {
        // Initialize the database
        evseRepository.saveAndFlush(evse);

        // Get the evse
        restEvseMockMvc
            .perform(get(ENTITY_API_URL_ID, evse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evse.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.reservable").value(DEFAULT_RESERVABLE.booleanValue()))
            .andExpect(jsonPath("$.oneTimePayment").value(DEFAULT_ONE_TIME_PAYMENT.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingEvse() throws Exception {
        // Get the evse
        restEvseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvse() throws Exception {
        // Initialize the database
        evseRepository.saveAndFlush(evse);

        int databaseSizeBeforeUpdate = evseRepository.findAll().size();

        // Update the evse
        Evse updatedEvse = evseRepository.findById(evse.getId()).get();
        // Disconnect from session so that the updates on updatedEvse are not directly saved in db
        em.detach(updatedEvse);
        updatedEvse
            .groupName(UPDATED_GROUP_NAME)
            .available(UPDATED_AVAILABLE)
            .reservable(UPDATED_RESERVABLE)
            .oneTimePayment(UPDATED_ONE_TIME_PAYMENT)
            .status(UPDATED_STATUS);
        EvseDTO evseDTO = evseMapper.toDto(updatedEvse);

        restEvseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
        Evse testEvse = evseList.get(evseList.size() - 1);
        assertThat(testEvse.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testEvse.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testEvse.getReservable()).isEqualTo(UPDATED_RESERVABLE);
        assertThat(testEvse.getOneTimePayment()).isEqualTo(UPDATED_ONE_TIME_PAYMENT);
        assertThat(testEvse.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingEvse() throws Exception {
        int databaseSizeBeforeUpdate = evseRepository.findAll().size();
        evse.setId(count.incrementAndGet());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvse() throws Exception {
        int databaseSizeBeforeUpdate = evseRepository.findAll().size();
        evse.setId(count.incrementAndGet());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvse() throws Exception {
        int databaseSizeBeforeUpdate = evseRepository.findAll().size();
        evse.setId(count.incrementAndGet());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvseWithPatch() throws Exception {
        // Initialize the database
        evseRepository.saveAndFlush(evse);

        int databaseSizeBeforeUpdate = evseRepository.findAll().size();

        // Update the evse using partial update
        Evse partialUpdatedEvse = new Evse();
        partialUpdatedEvse.setId(evse.getId());

        partialUpdatedEvse.groupName(UPDATED_GROUP_NAME).available(UPDATED_AVAILABLE).reservable(UPDATED_RESERVABLE).status(UPDATED_STATUS);

        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvse))
            )
            .andExpect(status().isOk());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
        Evse testEvse = evseList.get(evseList.size() - 1);
        assertThat(testEvse.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testEvse.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testEvse.getReservable()).isEqualTo(UPDATED_RESERVABLE);
        assertThat(testEvse.getOneTimePayment()).isEqualTo(DEFAULT_ONE_TIME_PAYMENT);
        assertThat(testEvse.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateEvseWithPatch() throws Exception {
        // Initialize the database
        evseRepository.saveAndFlush(evse);

        int databaseSizeBeforeUpdate = evseRepository.findAll().size();

        // Update the evse using partial update
        Evse partialUpdatedEvse = new Evse();
        partialUpdatedEvse.setId(evse.getId());

        partialUpdatedEvse
            .groupName(UPDATED_GROUP_NAME)
            .available(UPDATED_AVAILABLE)
            .reservable(UPDATED_RESERVABLE)
            .oneTimePayment(UPDATED_ONE_TIME_PAYMENT)
            .status(UPDATED_STATUS);

        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvse))
            )
            .andExpect(status().isOk());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
        Evse testEvse = evseList.get(evseList.size() - 1);
        assertThat(testEvse.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testEvse.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testEvse.getReservable()).isEqualTo(UPDATED_RESERVABLE);
        assertThat(testEvse.getOneTimePayment()).isEqualTo(UPDATED_ONE_TIME_PAYMENT);
        assertThat(testEvse.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingEvse() throws Exception {
        int databaseSizeBeforeUpdate = evseRepository.findAll().size();
        evse.setId(count.incrementAndGet());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvse() throws Exception {
        int databaseSizeBeforeUpdate = evseRepository.findAll().size();
        evse.setId(count.incrementAndGet());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvse() throws Exception {
        int databaseSizeBeforeUpdate = evseRepository.findAll().size();
        evse.setId(count.incrementAndGet());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(evseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evse in the database
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvse() throws Exception {
        // Initialize the database
        evseRepository.saveAndFlush(evse);

        int databaseSizeBeforeDelete = evseRepository.findAll().size();

        // Delete the evse
        restEvseMockMvc
            .perform(delete(ENTITY_API_URL_ID, evse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evse> evseList = evseRepository.findAll();
        assertThat(evseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
