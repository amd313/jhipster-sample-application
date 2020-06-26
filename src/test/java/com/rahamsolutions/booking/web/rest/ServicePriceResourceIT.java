package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.JhipsterSampleApplicationApp;
import com.rahamsolutions.booking.domain.ServicePrice;
import com.rahamsolutions.booking.repository.ServicePriceRepository;
import com.rahamsolutions.booking.service.ServicePriceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServicePriceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServicePriceResourceIT {

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    @Autowired
    private ServicePriceRepository servicePriceRepository;

    @Autowired
    private ServicePriceService servicePriceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicePriceMockMvc;

    private ServicePrice servicePrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicePrice createEntity(EntityManager em) {
        ServicePrice servicePrice = new ServicePrice()
            .amount(DEFAULT_AMOUNT);
        return servicePrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicePrice createUpdatedEntity(EntityManager em) {
        ServicePrice servicePrice = new ServicePrice()
            .amount(UPDATED_AMOUNT);
        return servicePrice;
    }

    @BeforeEach
    public void initTest() {
        servicePrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createServicePrice() throws Exception {
        int databaseSizeBeforeCreate = servicePriceRepository.findAll().size();
        // Create the ServicePrice
        restServicePriceMockMvc.perform(post("/api/service-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servicePrice)))
            .andExpect(status().isCreated());

        // Validate the ServicePrice in the database
        List<ServicePrice> servicePriceList = servicePriceRepository.findAll();
        assertThat(servicePriceList).hasSize(databaseSizeBeforeCreate + 1);
        ServicePrice testServicePrice = servicePriceList.get(servicePriceList.size() - 1);
        assertThat(testServicePrice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createServicePriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicePriceRepository.findAll().size();

        // Create the ServicePrice with an existing ID
        servicePrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicePriceMockMvc.perform(post("/api/service-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servicePrice)))
            .andExpect(status().isBadRequest());

        // Validate the ServicePrice in the database
        List<ServicePrice> servicePriceList = servicePriceRepository.findAll();
        assertThat(servicePriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServicePrices() throws Exception {
        // Initialize the database
        servicePriceRepository.saveAndFlush(servicePrice);

        // Get all the servicePriceList
        restServicePriceMockMvc.perform(get("/api/service-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicePrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getServicePrice() throws Exception {
        // Initialize the database
        servicePriceRepository.saveAndFlush(servicePrice);

        // Get the servicePrice
        restServicePriceMockMvc.perform(get("/api/service-prices/{id}", servicePrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicePrice.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingServicePrice() throws Exception {
        // Get the servicePrice
        restServicePriceMockMvc.perform(get("/api/service-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicePrice() throws Exception {
        // Initialize the database
        servicePriceService.save(servicePrice);

        int databaseSizeBeforeUpdate = servicePriceRepository.findAll().size();

        // Update the servicePrice
        ServicePrice updatedServicePrice = servicePriceRepository.findById(servicePrice.getId()).get();
        // Disconnect from session so that the updates on updatedServicePrice are not directly saved in db
        em.detach(updatedServicePrice);
        updatedServicePrice
            .amount(UPDATED_AMOUNT);

        restServicePriceMockMvc.perform(put("/api/service-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServicePrice)))
            .andExpect(status().isOk());

        // Validate the ServicePrice in the database
        List<ServicePrice> servicePriceList = servicePriceRepository.findAll();
        assertThat(servicePriceList).hasSize(databaseSizeBeforeUpdate);
        ServicePrice testServicePrice = servicePriceList.get(servicePriceList.size() - 1);
        assertThat(testServicePrice.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingServicePrice() throws Exception {
        int databaseSizeBeforeUpdate = servicePriceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicePriceMockMvc.perform(put("/api/service-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servicePrice)))
            .andExpect(status().isBadRequest());

        // Validate the ServicePrice in the database
        List<ServicePrice> servicePriceList = servicePriceRepository.findAll();
        assertThat(servicePriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServicePrice() throws Exception {
        // Initialize the database
        servicePriceService.save(servicePrice);

        int databaseSizeBeforeDelete = servicePriceRepository.findAll().size();

        // Delete the servicePrice
        restServicePriceMockMvc.perform(delete("/api/service-prices/{id}", servicePrice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServicePrice> servicePriceList = servicePriceRepository.findAll();
        assertThat(servicePriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
