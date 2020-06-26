package com.rahamsolutions.booking.service.impl;

import com.rahamsolutions.booking.service.BookedSlotsService;
import com.rahamsolutions.booking.domain.BookedSlots;
import com.rahamsolutions.booking.repository.BookedSlotsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BookedSlots}.
 */
@Service
@Transactional
public class BookedSlotsServiceImpl implements BookedSlotsService {

    private final Logger log = LoggerFactory.getLogger(BookedSlotsServiceImpl.class);

    private final BookedSlotsRepository bookedSlotsRepository;

    public BookedSlotsServiceImpl(BookedSlotsRepository bookedSlotsRepository) {
        this.bookedSlotsRepository = bookedSlotsRepository;
    }

    /**
     * Save a bookedSlots.
     *
     * @param bookedSlots the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BookedSlots save(BookedSlots bookedSlots) {
        log.debug("Request to save BookedSlots : {}", bookedSlots);
        return bookedSlotsRepository.save(bookedSlots);
    }

    /**
     * Get all the bookedSlots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookedSlots> findAll(Pageable pageable) {
        log.debug("Request to get all BookedSlots");
        return bookedSlotsRepository.findAll(pageable);
    }


    /**
     * Get one bookedSlots by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookedSlots> findOne(Long id) {
        log.debug("Request to get BookedSlots : {}", id);
        return bookedSlotsRepository.findById(id);
    }

    /**
     * Delete the bookedSlots by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookedSlots : {}", id);
        bookedSlotsRepository.deleteById(id);
    }
}
