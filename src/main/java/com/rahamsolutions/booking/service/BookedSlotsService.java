package com.rahamsolutions.booking.service;

import com.rahamsolutions.booking.domain.BookedSlots;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BookedSlots}.
 */
public interface BookedSlotsService {

    /**
     * Save a bookedSlots.
     *
     * @param bookedSlots the entity to save.
     * @return the persisted entity.
     */
    BookedSlots save(BookedSlots bookedSlots);

    /**
     * Get all the bookedSlots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookedSlots> findAll(Pageable pageable);


    /**
     * Get the "id" bookedSlots.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookedSlots> findOne(Long id);

    /**
     * Delete the "id" bookedSlots.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
