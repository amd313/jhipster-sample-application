package com.rahamsolutions.booking.service.impl;

import com.rahamsolutions.booking.service.BookeSlotStatusService;
import com.rahamsolutions.booking.domain.BookeSlotStatus;
import com.rahamsolutions.booking.repository.BookeSlotStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BookeSlotStatus}.
 */
@Service
@Transactional
public class BookeSlotStatusServiceImpl implements BookeSlotStatusService {

    private final Logger log = LoggerFactory.getLogger(BookeSlotStatusServiceImpl.class);

    private final BookeSlotStatusRepository bookeSlotStatusRepository;

    public BookeSlotStatusServiceImpl(BookeSlotStatusRepository bookeSlotStatusRepository) {
        this.bookeSlotStatusRepository = bookeSlotStatusRepository;
    }

    /**
     * Save a bookeSlotStatus.
     *
     * @param bookeSlotStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BookeSlotStatus save(BookeSlotStatus bookeSlotStatus) {
        log.debug("Request to save BookeSlotStatus : {}", bookeSlotStatus);
        return bookeSlotStatusRepository.save(bookeSlotStatus);
    }

    /**
     * Get all the bookeSlotStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookeSlotStatus> findAll() {
        log.debug("Request to get all BookeSlotStatuses");
        return bookeSlotStatusRepository.findAll();
    }


    /**
     * Get one bookeSlotStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookeSlotStatus> findOne(Long id) {
        log.debug("Request to get BookeSlotStatus : {}", id);
        return bookeSlotStatusRepository.findById(id);
    }

    /**
     * Delete the bookeSlotStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookeSlotStatus : {}", id);
        bookeSlotStatusRepository.deleteById(id);
    }
}
