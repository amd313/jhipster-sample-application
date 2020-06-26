package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.JhipsterSampleApplicationApp;
import com.rahamsolutions.booking.domain.PaymentStatus;
import com.rahamsolutions.booking.repository.PaymentStatusRepository;
import com.rahamsolutions.booking.service.PaymentStatusService;

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
 * Integration tests for the {@link PaymentStatusResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentStatusResourceIT {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentStatusMockMvc;

    private PaymentStatus paymentStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentStatus createEntity(EntityManager em) {
        PaymentStatus paymentStatus = new PaymentStatus()
            .statusName(DEFAULT_STATUS_NAME);
        return paymentStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentStatus createUpdatedEntity(EntityManager em) {
        PaymentStatus paymentStatus = new PaymentStatus()
            .statusName(UPDATED_STATUS_NAME);
        return paymentStatus;
    }

    @BeforeEach
    public void initTest() {
        paymentStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentStatus() throws Exception {
        int databaseSizeBeforeCreate = paymentStatusRepository.findAll().size();
        // Create the PaymentStatus
        restPaymentStatusMockMvc.perform(post("/api/payment-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentStatus)))
            .andExpect(status().isCreated());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentStatus testPaymentStatus = paymentStatusList.get(paymentStatusList.size() - 1);
        assertThat(testPaymentStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
    }

    @Test
    @Transactional
    public void createPaymentStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentStatusRepository.findAll().size();

        // Create the PaymentStatus with an existing ID
        paymentStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentStatusMockMvc.perform(post("/api/payment-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentStatuses() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList
        restPaymentStatusMockMvc.perform(get("/api/payment-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)));
    }
    
    @Test
    @Transactional
    public void getPaymentStatus() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get the paymentStatus
        restPaymentStatusMockMvc.perform(get("/api/payment-statuses/{id}", paymentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentStatus() throws Exception {
        // Get the paymentStatus
        restPaymentStatusMockMvc.perform(get("/api/payment-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentStatus() throws Exception {
        // Initialize the database
        paymentStatusService.save(paymentStatus);

        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();

        // Update the paymentStatus
        PaymentStatus updatedPaymentStatus = paymentStatusRepository.findById(paymentStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentStatus are not directly saved in db
        em.detach(updatedPaymentStatus);
        updatedPaymentStatus
            .statusName(UPDATED_STATUS_NAME);

        restPaymentStatusMockMvc.perform(put("/api/payment-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentStatus)))
            .andExpect(status().isOk());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
        PaymentStatus testPaymentStatus = paymentStatusList.get(paymentStatusList.size() - 1);
        assertThat(testPaymentStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc.perform(put("/api/payment-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentStatus() throws Exception {
        // Initialize the database
        paymentStatusService.save(paymentStatus);

        int databaseSizeBeforeDelete = paymentStatusRepository.findAll().size();

        // Delete the paymentStatus
        restPaymentStatusMockMvc.perform(delete("/api/payment-statuses/{id}", paymentStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
