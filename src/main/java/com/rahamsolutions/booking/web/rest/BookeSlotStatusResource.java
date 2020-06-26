package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.domain.BookeSlotStatus;
import com.rahamsolutions.booking.service.BookeSlotStatusService;
import com.rahamsolutions.booking.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.rahamsolutions.booking.domain.BookeSlotStatus}.
 */
@RestController
@RequestMapping("/api")
public class BookeSlotStatusResource {

    private final Logger log = LoggerFactory.getLogger(BookeSlotStatusResource.class);

    private static final String ENTITY_NAME = "bookeSlotStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookeSlotStatusService bookeSlotStatusService;

    public BookeSlotStatusResource(BookeSlotStatusService bookeSlotStatusService) {
        this.bookeSlotStatusService = bookeSlotStatusService;
    }

    /**
     * {@code POST  /booke-slot-statuses} : Create a new bookeSlotStatus.
     *
     * @param bookeSlotStatus the bookeSlotStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookeSlotStatus, or with status {@code 400 (Bad Request)} if the bookeSlotStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/booke-slot-statuses")
    public ResponseEntity<BookeSlotStatus> createBookeSlotStatus(@RequestBody BookeSlotStatus bookeSlotStatus) throws URISyntaxException {
        log.debug("REST request to save BookeSlotStatus : {}", bookeSlotStatus);
        if (bookeSlotStatus.getId() != null) {
            throw new BadRequestAlertException("A new bookeSlotStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookeSlotStatus result = bookeSlotStatusService.save(bookeSlotStatus);
        return ResponseEntity.created(new URI("/api/booke-slot-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /booke-slot-statuses} : Updates an existing bookeSlotStatus.
     *
     * @param bookeSlotStatus the bookeSlotStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookeSlotStatus,
     * or with status {@code 400 (Bad Request)} if the bookeSlotStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookeSlotStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/booke-slot-statuses")
    public ResponseEntity<BookeSlotStatus> updateBookeSlotStatus(@RequestBody BookeSlotStatus bookeSlotStatus) throws URISyntaxException {
        log.debug("REST request to update BookeSlotStatus : {}", bookeSlotStatus);
        if (bookeSlotStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookeSlotStatus result = bookeSlotStatusService.save(bookeSlotStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookeSlotStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /booke-slot-statuses} : get all the bookeSlotStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookeSlotStatuses in body.
     */
    @GetMapping("/booke-slot-statuses")
    public List<BookeSlotStatus> getAllBookeSlotStatuses() {
        log.debug("REST request to get all BookeSlotStatuses");
        return bookeSlotStatusService.findAll();
    }

    /**
     * {@code GET  /booke-slot-statuses/:id} : get the "id" bookeSlotStatus.
     *
     * @param id the id of the bookeSlotStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookeSlotStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/booke-slot-statuses/{id}")
    public ResponseEntity<BookeSlotStatus> getBookeSlotStatus(@PathVariable Long id) {
        log.debug("REST request to get BookeSlotStatus : {}", id);
        Optional<BookeSlotStatus> bookeSlotStatus = bookeSlotStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookeSlotStatus);
    }

    /**
     * {@code DELETE  /booke-slot-statuses/:id} : delete the "id" bookeSlotStatus.
     *
     * @param id the id of the bookeSlotStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/booke-slot-statuses/{id}")
    public ResponseEntity<Void> deleteBookeSlotStatus(@PathVariable Long id) {
        log.debug("REST request to delete BookeSlotStatus : {}", id);
        bookeSlotStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
