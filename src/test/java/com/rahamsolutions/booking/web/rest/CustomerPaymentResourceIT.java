package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.JhipsterSampleApplicationApp;
import com.rahamsolutions.booking.domain.CustomerPayment;
import com.rahamsolutions.booking.repository.CustomerPaymentRepository;
import com.rahamsolutions.booking.service.CustomerPaymentService;

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
 * Integration tests for the {@link CustomerPaymentResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentResourceIT {

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @Autowired
    private CustomerPaymentService customerPaymentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentMockMvc;

    private CustomerPayment customerPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPayment createEntity(EntityManager em) {
        CustomerPayment customerPayment = new CustomerPayment()
            .status(DEFAULT_STATUS)
            .amount(DEFAULT_AMOUNT);
        return customerPayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPayment createUpdatedEntity(EntityManager em) {
        CustomerPayment customerPayment = new CustomerPayment()
            .status(UPDATED_STATUS)
            .amount(UPDATED_AMOUNT);
        return customerPayment;
    }

    @BeforeEach
    public void initTest() {
        customerPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPayment() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentRepository.findAll().size();
        // Create the CustomerPayment
        restCustomerPaymentMockMvc.perform(post("/api/customer-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPayment)))
            .andExpect(status().isCreated());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPayment testCustomerPayment = customerPaymentList.get(customerPaymentList.size() - 1);
        assertThat(testCustomerPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCustomerPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentRepository.findAll().size();

        // Create the CustomerPayment with an existing ID
        customerPayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentMockMvc.perform(post("/api/customer-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPayment)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerPayments() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerPayment() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get the customerPayment
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments/{id}", customerPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPayment.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerPayment() throws Exception {
        // Get the customerPayment
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPayment() throws Exception {
        // Initialize the database
        customerPaymentService.save(customerPayment);

        int databaseSizeBeforeUpdate = customerPaymentRepository.findAll().size();

        // Update the customerPayment
        CustomerPayment updatedCustomerPayment = customerPaymentRepository.findById(customerPayment.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPayment are not directly saved in db
        em.detach(updatedCustomerPayment);
        updatedCustomerPayment
            .status(UPDATED_STATUS)
            .amount(UPDATED_AMOUNT);

        restCustomerPaymentMockMvc.perform(put("/api/customer-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerPayment)))
            .andExpect(status().isOk());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeUpdate);
        CustomerPayment testCustomerPayment = customerPaymentList.get(customerPaymentList.size() - 1);
        assertThat(testCustomerPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPayment() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentMockMvc.perform(put("/api/customer-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPayment)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPayment() throws Exception {
        // Initialize the database
        customerPaymentService.save(customerPayment);

        int databaseSizeBeforeDelete = customerPaymentRepository.findAll().size();

        // Delete the customerPayment
        restCustomerPaymentMockMvc.perform(delete("/api/customer-payments/{id}", customerPayment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
