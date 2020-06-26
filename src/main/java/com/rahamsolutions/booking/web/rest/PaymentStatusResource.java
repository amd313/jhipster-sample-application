package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.domain.PaymentStatus;
import com.rahamsolutions.booking.service.PaymentStatusService;
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
 * REST controller for managing {@link com.rahamsolutions.booking.domain.PaymentStatus}.
 */
@RestController
@RequestMapping("/api")
public class PaymentStatusResource {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusResource.class);

    private static final String ENTITY_NAME = "paymentStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentStatusService paymentStatusService;

    public PaymentStatusResource(PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    /**
     * {@code POST  /payment-statuses} : Create a new paymentStatus.
     *
     * @param paymentStatus the paymentStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentStatus, or with status {@code 400 (Bad Request)} if the paymentStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-statuses")
    public ResponseEntity<PaymentStatus> createPaymentStatus(@RequestBody PaymentStatus paymentStatus) throws URISyntaxException {
        log.debug("REST request to save PaymentStatus : {}", paymentStatus);
        if (paymentStatus.getId() != null) {
            throw new BadRequestAlertException("A new paymentStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentStatus result = paymentStatusService.save(paymentStatus);
        return ResponseEntity.created(new URI("/api/payment-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-statuses} : Updates an existing paymentStatus.
     *
     * @param paymentStatus the paymentStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentStatus,
     * or with status {@code 400 (Bad Request)} if the paymentStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-statuses")
    public ResponseEntity<PaymentStatus> updatePaymentStatus(@RequestBody PaymentStatus paymentStatus) throws URISyntaxException {
        log.debug("REST request to update PaymentStatus : {}", paymentStatus);
        if (paymentStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentStatus result = paymentStatusService.save(paymentStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-statuses} : get all the paymentStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentStatuses in body.
     */
    @GetMapping("/payment-statuses")
    public List<PaymentStatus> getAllPaymentStatuses() {
        log.debug("REST request to get all PaymentStatuses");
        return paymentStatusService.findAll();
    }

    /**
     * {@code GET  /payment-statuses/:id} : get the "id" paymentStatus.
     *
     * @param id the id of the paymentStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-statuses/{id}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable Long id) {
        log.debug("REST request to get PaymentStatus : {}", id);
        Optional<PaymentStatus> paymentStatus = paymentStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentStatus);
    }

    /**
     * {@code DELETE  /payment-statuses/:id} : delete the "id" paymentStatus.
     *
     * @param id the id of the paymentStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-statuses/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        log.debug("REST request to delete PaymentStatus : {}", id);
        paymentStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
