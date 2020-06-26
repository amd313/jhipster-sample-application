package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.domain.BookedSlots;
import com.rahamsolutions.booking.service.BookedSlotsService;
import com.rahamsolutions.booking.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.rahamsolutions.booking.domain.BookedSlots}.
 */
@RestController
@RequestMapping("/api")
public class BookedSlotsResource {

    private final Logger log = LoggerFactory.getLogger(BookedSlotsResource.class);

    private static final String ENTITY_NAME = "bookedSlots";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookedSlotsService bookedSlotsService;

    public BookedSlotsResource(BookedSlotsService bookedSlotsService) {
        this.bookedSlotsService = bookedSlotsService;
    }

    /**
     * {@code POST  /booked-slots} : Create a new bookedSlots.
     *
     * @param bookedSlots the bookedSlots to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookedSlots, or with status {@code 400 (Bad Request)} if the bookedSlots has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/booked-slots")
    public ResponseEntity<BookedSlots> createBookedSlots(@RequestBody BookedSlots bookedSlots) throws URISyntaxException {
        log.debug("REST request to save BookedSlots : {}", bookedSlots);
        if (bookedSlots.getId() != null) {
            throw new BadRequestAlertException("A new bookedSlots cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookedSlots result = bookedSlotsService.save(bookedSlots);
        return ResponseEntity.created(new URI("/api/booked-slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /booked-slots} : Updates an existing bookedSlots.
     *
     * @param bookedSlots the bookedSlots to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookedSlots,
     * or with status {@code 400 (Bad Request)} if the bookedSlots is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookedSlots couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/booked-slots")
    public ResponseEntity<BookedSlots> updateBookedSlots(@RequestBody BookedSlots bookedSlots) throws URISyntaxException {
        log.debug("REST request to update BookedSlots : {}", bookedSlots);
        if (bookedSlots.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookedSlots result = bookedSlotsService.save(bookedSlots);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookedSlots.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /booked-slots} : get all the bookedSlots.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookedSlots in body.
     */
    @GetMapping("/booked-slots")
    public ResponseEntity<List<BookedSlots>> getAllBookedSlots(Pageable pageable) {
        log.debug("REST request to get a page of BookedSlots");
        Page<BookedSlots> page = bookedSlotsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /booked-slots/:id} : get the "id" bookedSlots.
     *
     * @param id the id of the bookedSlots to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookedSlots, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/booked-slots/{id}")
    public ResponseEntity<BookedSlots> getBookedSlots(@PathVariable Long id) {
        log.debug("REST request to get BookedSlots : {}", id);
        Optional<BookedSlots> bookedSlots = bookedSlotsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookedSlots);
    }

    /**
     * {@code DELETE  /booked-slots/:id} : delete the "id" bookedSlots.
     *
     * @param id the id of the bookedSlots to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/booked-slots/{id}")
    public ResponseEntity<Void> deleteBookedSlots(@PathVariable Long id) {
        log.debug("REST request to delete BookedSlots : {}", id);
        bookedSlotsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
