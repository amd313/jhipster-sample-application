package com.rahamsolutions.booking.service;

import com.rahamsolutions.booking.domain.BookeSlotStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BookeSlotStatus}.
 */
public interface BookeSlotStatusService {

    /**
     * Save a bookeSlotStatus.
     *
     * @param bookeSlotStatus the entity to save.
     * @return the persisted entity.
     */
    BookeSlotStatus save(BookeSlotStatus bookeSlotStatus);

    /**
     * Get all the bookeSlotStatuses.
     *
     * @return the list of entities.
     */
    List<BookeSlotStatus> findAll();


    /**
     * Get the "id" bookeSlotStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookeSlotStatus> findOne(Long id);

    /**
     * Delete the "id" bookeSlotStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
