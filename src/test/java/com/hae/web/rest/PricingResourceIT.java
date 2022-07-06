package com.hae.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hae.IntegrationTest;
import com.hae.domain.Pricing;
import com.hae.repository.PricingRepository;
import com.hae.service.dto.PricingDTO;
import com.hae.service.mapper.PricingMapper;
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
 * Integration tests for the {@link PricingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PricingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_CENTS = 0D;
    private static final Double UPDATED_PRICE_CENTS = 1D;

    private static final String DEFAULT_CURRENCY = "AAA";
    private static final String UPDATED_CURRENCY = "BBB";

    private static final Double DEFAULT_PRICE_CENTS_WITHOUT_VAT = 0D;
    private static final Double UPDATED_PRICE_CENTS_WITHOUT_VAT = 1D;

    private static final Double DEFAULT_PRICE_CENTS_VAT = 0D;
    private static final Double UPDATED_PRICE_CENTS_VAT = 1D;

    private static final String ENTITY_API_URL = "/api/pricings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PricingRepository pricingRepository;

    @Autowired
    private PricingMapper pricingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPricingMockMvc;

    private Pricing pricing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pricing createEntity(EntityManager em) {
        Pricing pricing = new Pricing()
            .name(DEFAULT_NAME)
            .priceCents(DEFAULT_PRICE_CENTS)
            .currency(DEFAULT_CURRENCY)
            .priceCentsWithoutVat(DEFAULT_PRICE_CENTS_WITHOUT_VAT)
            .priceCentsVat(DEFAULT_PRICE_CENTS_VAT);
        return pricing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pricing createUpdatedEntity(EntityManager em) {
        Pricing pricing = new Pricing()
            .name(UPDATED_NAME)
            .priceCents(UPDATED_PRICE_CENTS)
            .currency(UPDATED_CURRENCY)
            .priceCentsWithoutVat(UPDATED_PRICE_CENTS_WITHOUT_VAT)
            .priceCentsVat(UPDATED_PRICE_CENTS_VAT);
        return pricing;
    }

    @BeforeEach
    public void initTest() {
        pricing = createEntity(em);
    }

    @Test
    @Transactional
    void createPricing() throws Exception {
        int databaseSizeBeforeCreate = pricingRepository.findAll().size();
        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);
        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isCreated());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeCreate + 1);
        Pricing testPricing = pricingList.get(pricingList.size() - 1);
        assertThat(testPricing.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPricing.getPriceCents()).isEqualTo(DEFAULT_PRICE_CENTS);
        assertThat(testPricing.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPricing.getPriceCentsWithoutVat()).isEqualTo(DEFAULT_PRICE_CENTS_WITHOUT_VAT);
        assertThat(testPricing.getPriceCentsVat()).isEqualTo(DEFAULT_PRICE_CENTS_VAT);
    }

    @Test
    @Transactional
    void createPricingWithExistingId() throws Exception {
        // Create the Pricing with an existing ID
        pricing.setId(1L);
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        int databaseSizeBeforeCreate = pricingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingRepository.findAll().size();
        // set the field null
        pricing.setName(null);

        // Create the Pricing, which fails.
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isBadRequest());

        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceCentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingRepository.findAll().size();
        // set the field null
        pricing.setPriceCents(null);

        // Create the Pricing, which fails.
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isBadRequest());

        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingRepository.findAll().size();
        // set the field null
        pricing.setCurrency(null);

        // Create the Pricing, which fails.
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isBadRequest());

        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceCentsWithoutVatIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingRepository.findAll().size();
        // set the field null
        pricing.setPriceCentsWithoutVat(null);

        // Create the Pricing, which fails.
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isBadRequest());

        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceCentsVatIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingRepository.findAll().size();
        // set the field null
        pricing.setPriceCentsVat(null);

        // Create the Pricing, which fails.
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        restPricingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isBadRequest());

        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPricings() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        // Get all the pricingList
        restPricingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priceCents").value(hasItem(DEFAULT_PRICE_CENTS.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].priceCentsWithoutVat").value(hasItem(DEFAULT_PRICE_CENTS_WITHOUT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].priceCentsVat").value(hasItem(DEFAULT_PRICE_CENTS_VAT.doubleValue())));
    }

    @Test
    @Transactional
    void getPricing() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        // Get the pricing
        restPricingMockMvc
            .perform(get(ENTITY_API_URL_ID, pricing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pricing.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priceCents").value(DEFAULT_PRICE_CENTS.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.priceCentsWithoutVat").value(DEFAULT_PRICE_CENTS_WITHOUT_VAT.doubleValue()))
            .andExpect(jsonPath("$.priceCentsVat").value(DEFAULT_PRICE_CENTS_VAT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPricing() throws Exception {
        // Get the pricing
        restPricingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPricing() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();

        // Update the pricing
        Pricing updatedPricing = pricingRepository.findById(pricing.getId()).get();
        // Disconnect from session so that the updates on updatedPricing are not directly saved in db
        em.detach(updatedPricing);
        updatedPricing
            .name(UPDATED_NAME)
            .priceCents(UPDATED_PRICE_CENTS)
            .currency(UPDATED_CURRENCY)
            .priceCentsWithoutVat(UPDATED_PRICE_CENTS_WITHOUT_VAT)
            .priceCentsVat(UPDATED_PRICE_CENTS_VAT);
        PricingDTO pricingDTO = pricingMapper.toDto(updatedPricing);

        restPricingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pricingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pricingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
        Pricing testPricing = pricingList.get(pricingList.size() - 1);
        assertThat(testPricing.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPricing.getPriceCents()).isEqualTo(UPDATED_PRICE_CENTS);
        assertThat(testPricing.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPricing.getPriceCentsWithoutVat()).isEqualTo(UPDATED_PRICE_CENTS_WITHOUT_VAT);
        assertThat(testPricing.getPriceCentsVat()).isEqualTo(UPDATED_PRICE_CENTS_VAT);
    }

    @Test
    @Transactional
    void putNonExistingPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();
        pricing.setId(count.incrementAndGet());

        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pricingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pricingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();
        pricing.setId(count.incrementAndGet());

        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPricingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pricingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();
        pricing.setId(count.incrementAndGet());

        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPricingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pricingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePricingWithPatch() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();

        // Update the pricing using partial update
        Pricing partialUpdatedPricing = new Pricing();
        partialUpdatedPricing.setId(pricing.getId());

        partialUpdatedPricing.priceCentsWithoutVat(UPDATED_PRICE_CENTS_WITHOUT_VAT).priceCentsVat(UPDATED_PRICE_CENTS_VAT);

        restPricingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPricing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPricing))
            )
            .andExpect(status().isOk());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
        Pricing testPricing = pricingList.get(pricingList.size() - 1);
        assertThat(testPricing.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPricing.getPriceCents()).isEqualTo(DEFAULT_PRICE_CENTS);
        assertThat(testPricing.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPricing.getPriceCentsWithoutVat()).isEqualTo(UPDATED_PRICE_CENTS_WITHOUT_VAT);
        assertThat(testPricing.getPriceCentsVat()).isEqualTo(UPDATED_PRICE_CENTS_VAT);
    }

    @Test
    @Transactional
    void fullUpdatePricingWithPatch() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();

        // Update the pricing using partial update
        Pricing partialUpdatedPricing = new Pricing();
        partialUpdatedPricing.setId(pricing.getId());

        partialUpdatedPricing
            .name(UPDATED_NAME)
            .priceCents(UPDATED_PRICE_CENTS)
            .currency(UPDATED_CURRENCY)
            .priceCentsWithoutVat(UPDATED_PRICE_CENTS_WITHOUT_VAT)
            .priceCentsVat(UPDATED_PRICE_CENTS_VAT);

        restPricingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPricing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPricing))
            )
            .andExpect(status().isOk());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
        Pricing testPricing = pricingList.get(pricingList.size() - 1);
        assertThat(testPricing.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPricing.getPriceCents()).isEqualTo(UPDATED_PRICE_CENTS);
        assertThat(testPricing.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPricing.getPriceCentsWithoutVat()).isEqualTo(UPDATED_PRICE_CENTS_WITHOUT_VAT);
        assertThat(testPricing.getPriceCentsVat()).isEqualTo(UPDATED_PRICE_CENTS_VAT);
    }

    @Test
    @Transactional
    void patchNonExistingPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();
        pricing.setId(count.incrementAndGet());

        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pricingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pricingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();
        pricing.setId(count.incrementAndGet());

        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPricingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pricingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();
        pricing.setId(count.incrementAndGet());

        // Create the Pricing
        PricingDTO pricingDTO = pricingMapper.toDto(pricing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPricingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pricingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePricing() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        int databaseSizeBeforeDelete = pricingRepository.findAll().size();

        // Delete the pricing
        restPricingMockMvc
            .perform(delete(ENTITY_API_URL_ID, pricing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
