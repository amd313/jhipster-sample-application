package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.JhipsterSampleApplicationApp;
import com.rahamsolutions.booking.domain.BookedSlots;
import com.rahamsolutions.booking.repository.BookedSlotsRepository;
import com.rahamsolutions.booking.service.BookedSlotsService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BookedSlotsResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookedSlotsResourceIT {

    private static final Instant DEFAULT_SLOT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SLOT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_PAYMENT_STATUS = 1;
    private static final Integer UPDATED_PAYMENT_STATUS = 2;

    @Autowired
    private BookedSlotsRepository bookedSlotsRepository;

    @Autowired
    private BookedSlotsService bookedSlotsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookedSlotsMockMvc;

    private BookedSlots bookedSlots;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookedSlots createEntity(EntityManager em) {
        BookedSlots bookedSlots = new BookedSlots()
            .slot(DEFAULT_SLOT)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .serviceName(DEFAULT_SERVICE_NAME)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .status(DEFAULT_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return bookedSlots;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookedSlots createUpdatedEntity(EntityManager em) {
        BookedSlots bookedSlots = new BookedSlots()
            .slot(UPDATED_SLOT)
            .customerName(UPDATED_CUSTOMER_NAME)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .serviceName(UPDATED_SERVICE_NAME)
            .categoryName(UPDATED_CATEGORY_NAME)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        return bookedSlots;
    }

    @BeforeEach
    public void initTest() {
        bookedSlots = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookedSlots() throws Exception {
        int databaseSizeBeforeCreate = bookedSlotsRepository.findAll().size();
        // Create the BookedSlots
        restBookedSlotsMockMvc.perform(post("/api/booked-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookedSlots)))
            .andExpect(status().isCreated());

        // Validate the BookedSlots in the database
        List<BookedSlots> bookedSlotsList = bookedSlotsRepository.findAll();
        assertThat(bookedSlotsList).hasSize(databaseSizeBeforeCreate + 1);
        BookedSlots testBookedSlots = bookedSlotsList.get(bookedSlotsList.size() - 1);
        assertThat(testBookedSlots.getSlot()).isEqualTo(DEFAULT_SLOT);
        assertThat(testBookedSlots.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testBookedSlots.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testBookedSlots.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testBookedSlots.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testBookedSlots.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBookedSlots.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void createBookedSlotsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookedSlotsRepository.findAll().size();

        // Create the BookedSlots with an existing ID
        bookedSlots.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookedSlotsMockMvc.perform(post("/api/booked-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookedSlots)))
            .andExpect(status().isBadRequest());

        // Validate the BookedSlots in the database
        List<BookedSlots> bookedSlotsList = bookedSlotsRepository.findAll();
        assertThat(bookedSlotsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookedSlots() throws Exception {
        // Initialize the database
        bookedSlotsRepository.saveAndFlush(bookedSlots);

        // Get all the bookedSlotsList
        restBookedSlotsMockMvc.perform(get("/api/booked-slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookedSlots.getId().intValue())))
            .andExpect(jsonPath("$.[*].slot").value(hasItem(DEFAULT_SLOT.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getBookedSlots() throws Exception {
        // Initialize the database
        bookedSlotsRepository.saveAndFlush(bookedSlots);

        // Get the bookedSlots
        restBookedSlotsMockMvc.perform(get("/api/booked-slots/{id}", bookedSlots.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookedSlots.getId().intValue()))
            .andExpect(jsonPath("$.slot").value(DEFAULT_SLOT.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingBookedSlots() throws Exception {
        // Get the bookedSlots
        restBookedSlotsMockMvc.perform(get("/api/booked-slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookedSlots() throws Exception {
        // Initialize the database
        bookedSlotsService.save(bookedSlots);

        int databaseSizeBeforeUpdate = bookedSlotsRepository.findAll().size();

        // Update the bookedSlots
        BookedSlots updatedBookedSlots = bookedSlotsRepository.findById(bookedSlots.getId()).get();
        // Disconnect from session so that the updates on updatedBookedSlots are not directly saved in db
        em.detach(updatedBookedSlots);
        updatedBookedSlots
            .slot(UPDATED_SLOT)
            .customerName(UPDATED_CUSTOMER_NAME)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .serviceName(UPDATED_SERVICE_NAME)
            .categoryName(UPDATED_CATEGORY_NAME)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restBookedSlotsMockMvc.perform(put("/api/booked-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookedSlots)))
            .andExpect(status().isOk());

        // Validate the BookedSlots in the database
        List<BookedSlots> bookedSlotsList = bookedSlotsRepository.findAll();
        assertThat(bookedSlotsList).hasSize(databaseSizeBeforeUpdate);
        BookedSlots testBookedSlots = bookedSlotsList.get(bookedSlotsList.size() - 1);
        assertThat(testBookedSlots.getSlot()).isEqualTo(UPDATED_SLOT);
        assertThat(testBookedSlots.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testBookedSlots.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testBookedSlots.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testBookedSlots.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testBookedSlots.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBookedSlots.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookedSlots() throws Exception {
        int databaseSizeBeforeUpdate = bookedSlotsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookedSlotsMockMvc.perform(put("/api/booked-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookedSlots)))
            .andExpect(status().isBadRequest());

        // Validate the BookedSlots in the database
        List<BookedSlots> bookedSlotsList = bookedSlotsRepository.findAll();
        assertThat(bookedSlotsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookedSlots() throws Exception {
        // Initialize the database
        bookedSlotsService.save(bookedSlots);

        int databaseSizeBeforeDelete = bookedSlotsRepository.findAll().size();

        // Delete the bookedSlots
        restBookedSlotsMockMvc.perform(delete("/api/booked-slots/{id}", bookedSlots.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookedSlots> bookedSlotsList = bookedSlotsRepository.findAll();
        assertThat(bookedSlotsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
