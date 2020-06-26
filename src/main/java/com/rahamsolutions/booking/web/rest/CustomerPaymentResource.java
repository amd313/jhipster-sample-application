package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.domain.CustomerPayment;
import com.rahamsolutions.booking.service.CustomerPaymentService;
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
 * REST controller for managing {@link com.rahamsolutions.booking.domain.CustomerPayment}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentResource.class);

    private static final String ENTITY_NAME = "customerPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentService customerPaymentService;

    public CustomerPaymentResource(CustomerPaymentService customerPaymentService) {
        this.customerPaymentService = customerPaymentService;
    }

    /**
     * {@code POST  /customer-payments} : Create a new customerPayment.
     *
     * @param customerPayment the customerPayment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPayment, or with status {@code 400 (Bad Request)} if the customerPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payments")
    public ResponseEntity<CustomerPayment> createCustomerPayment(@RequestBody CustomerPayment customerPayment) throws URISyntaxException {
        log.debug("REST request to save CustomerPayment : {}", customerPayment);
        if (customerPayment.getId() != null) {
            throw new BadRequestAlertException("A new customerPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPayment result = customerPaymentService.save(customerPayment);
        return ResponseEntity.created(new URI("/api/customer-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payments} : Updates an existing customerPayment.
     *
     * @param customerPayment the customerPayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPayment,
     * or with status {@code 400 (Bad Request)} if the customerPayment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payments")
    public ResponseEntity<CustomerPayment> updateCustomerPayment(@RequestBody CustomerPayment customerPayment) throws URISyntaxException {
        log.debug("REST request to update CustomerPayment : {}", customerPayment);
        if (customerPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPayment result = customerPaymentService.save(customerPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPayment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payments} : get all the customerPayments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPayments in body.
     */
    @GetMapping("/customer-payments")
    public List<CustomerPayment> getAllCustomerPayments() {
        log.debug("REST request to get all CustomerPayments");
        return customerPaymentService.findAll();
    }

    /**
     * {@code GET  /customer-payments/:id} : get the "id" customerPayment.
     *
     * @param id the id of the customerPayment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPayment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payments/{id}")
    public ResponseEntity<CustomerPayment> getCustomerPayment(@PathVariable Long id) {
        log.debug("REST request to get CustomerPayment : {}", id);
        Optional<CustomerPayment> customerPayment = customerPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPayment);
    }

    /**
     * {@code DELETE  /customer-payments/:id} : delete the "id" customerPayment.
     *
     * @param id the id of the customerPayment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payments/{id}")
    public ResponseEntity<Void> deleteCustomerPayment(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPayment : {}", id);
        customerPaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
