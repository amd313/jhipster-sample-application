package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.JhipsterSampleApplicationApp;
import com.rahamsolutions.booking.domain.BookeSlotStatus;
import com.rahamsolutions.booking.repository.BookeSlotStatusRepository;
import com.rahamsolutions.booking.service.BookeSlotStatusService;

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
 * Integration tests for the {@link BookeSlotStatusResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookeSlotStatusResourceIT {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    @Autowired
    private BookeSlotStatusRepository bookeSlotStatusRepository;

    @Autowired
    private BookeSlotStatusService bookeSlotStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookeSlotStatusMockMvc;

    private BookeSlotStatus bookeSlotStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookeSlotStatus createEntity(EntityManager em) {
        BookeSlotStatus bookeSlotStatus = new BookeSlotStatus()
            .statusName(DEFAULT_STATUS_NAME);
        return bookeSlotStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookeSlotStatus createUpdatedEntity(EntityManager em) {
        BookeSlotStatus bookeSlotStatus = new BookeSlotStatus()
            .statusName(UPDATED_STATUS_NAME);
        return bookeSlotStatus;
    }

    @BeforeEach
    public void initTest() {
        bookeSlotStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookeSlotStatus() throws Exception {
        int databaseSizeBeforeCreate = bookeSlotStatusRepository.findAll().size();
        // Create the BookeSlotStatus
        restBookeSlotStatusMockMvc.perform(post("/api/booke-slot-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookeSlotStatus)))
            .andExpect(status().isCreated());

        // Validate the BookeSlotStatus in the database
        List<BookeSlotStatus> bookeSlotStatusList = bookeSlotStatusRepository.findAll();
        assertThat(bookeSlotStatusList).hasSize(databaseSizeBeforeCreate + 1);
        BookeSlotStatus testBookeSlotStatus = bookeSlotStatusList.get(bookeSlotStatusList.size() - 1);
        assertThat(testBookeSlotStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
    }

    @Test
    @Transactional
    public void createBookeSlotStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookeSlotStatusRepository.findAll().size();

        // Create the BookeSlotStatus with an existing ID
        bookeSlotStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookeSlotStatusMockMvc.perform(post("/api/booke-slot-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookeSlotStatus)))
            .andExpect(status().isBadRequest());

        // Validate the BookeSlotStatus in the database
        List<BookeSlotStatus> bookeSlotStatusList = bookeSlotStatusRepository.findAll();
        assertThat(bookeSlotStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookeSlotStatuses() throws Exception {
        // Initialize the database
        bookeSlotStatusRepository.saveAndFlush(bookeSlotStatus);

        // Get all the bookeSlotStatusList
        restBookeSlotStatusMockMvc.perform(get("/api/booke-slot-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookeSlotStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)));
    }
    
    @Test
    @Transactional
    public void getBookeSlotStatus() throws Exception {
        // Initialize the database
        bookeSlotStatusRepository.saveAndFlush(bookeSlotStatus);

        // Get the bookeSlotStatus
        restBookeSlotStatusMockMvc.perform(get("/api/booke-slot-statuses/{id}", bookeSlotStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookeSlotStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBookeSlotStatus() throws Exception {
        // Get the bookeSlotStatus
        restBookeSlotStatusMockMvc.perform(get("/api/booke-slot-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookeSlotStatus() throws Exception {
        // Initialize the database
        bookeSlotStatusService.save(bookeSlotStatus);

        int databaseSizeBeforeUpdate = bookeSlotStatusRepository.findAll().size();

        // Update the bookeSlotStatus
        BookeSlotStatus updatedBookeSlotStatus = bookeSlotStatusRepository.findById(bookeSlotStatus.getId()).get();
        // Disconnect from session so that the updates on updatedBookeSlotStatus are not directly saved in db
        em.detach(updatedBookeSlotStatus);
        updatedBookeSlotStatus
            .statusName(UPDATED_STATUS_NAME);

        restBookeSlotStatusMockMvc.perform(put("/api/booke-slot-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookeSlotStatus)))
            .andExpect(status().isOk());

        // Validate the BookeSlotStatus in the database
        List<BookeSlotStatus> bookeSlotStatusList = bookeSlotStatusRepository.findAll();
        assertThat(bookeSlotStatusList).hasSize(databaseSizeBeforeUpdate);
        BookeSlotStatus testBookeSlotStatus = bookeSlotStatusList.get(bookeSlotStatusList.size() - 1);
        assertThat(testBookeSlotStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBookeSlotStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookeSlotStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookeSlotStatusMockMvc.perform(put("/api/booke-slot-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookeSlotStatus)))
            .andExpect(status().isBadRequest());

        // Validate the BookeSlotStatus in the database
        List<BookeSlotStatus> bookeSlotStatusList = bookeSlotStatusRepository.findAll();
        assertThat(bookeSlotStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookeSlotStatus() throws Exception {
        // Initialize the database
        bookeSlotStatusService.save(bookeSlotStatus);

        int databaseSizeBeforeDelete = bookeSlotStatusRepository.findAll().size();

        // Delete the bookeSlotStatus
        restBookeSlotStatusMockMvc.perform(delete("/api/booke-slot-statuses/{id}", bookeSlotStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookeSlotStatus> bookeSlotStatusList = bookeSlotStatusRepository.findAll();
        assertThat(bookeSlotStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
