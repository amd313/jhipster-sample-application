package com.rahamsolutions.booking.web.rest;

import com.rahamsolutions.booking.domain.ServicePrice;
import com.rahamsolutions.booking.service.ServicePriceService;
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
 * REST controller for managing {@link com.rahamsolutions.booking.domain.ServicePrice}.
 */
@RestController
@RequestMapping("/api")
public class ServicePriceResource {

    private final Logger log = LoggerFactory.getLogger(ServicePriceResource.class);

    private static final String ENTITY_NAME = "servicePrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicePriceService servicePriceService;

    public ServicePriceResource(ServicePriceService servicePriceService) {
        this.servicePriceService = servicePriceService;
    }

    /**
     * {@code POST  /service-prices} : Create a new servicePrice.
     *
     * @param servicePrice the servicePrice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicePrice, or with status {@code 400 (Bad Request)} if the servicePrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-prices")
    public ResponseEntity<ServicePrice> createServicePrice(@RequestBody ServicePrice servicePrice) throws URISyntaxException {
        log.debug("REST request to save ServicePrice : {}", servicePrice);
        if (servicePrice.getId() != null) {
            throw new BadRequestAlertException("A new servicePrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicePrice result = servicePriceService.save(servicePrice);
        return ResponseEntity.created(new URI("/api/service-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-prices} : Updates an existing servicePrice.
     *
     * @param servicePrice the servicePrice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicePrice,
     * or with status {@code 400 (Bad Request)} if the servicePrice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicePrice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-prices")
    public ResponseEntity<ServicePrice> updateServicePrice(@RequestBody ServicePrice servicePrice) throws URISyntaxException {
        log.debug("REST request to update ServicePrice : {}", servicePrice);
        if (servicePrice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServicePrice result = servicePriceService.save(servicePrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicePrice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-prices} : get all the servicePrices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicePrices in body.
     */
    @GetMapping("/service-prices")
    public List<ServicePrice> getAllServicePrices() {
        log.debug("REST request to get all ServicePrices");
        return servicePriceService.findAll();
    }

    /**
     * {@code GET  /service-prices/:id} : get the "id" servicePrice.
     *
     * @param id the id of the servicePrice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicePrice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-prices/{id}")
    public ResponseEntity<ServicePrice> getServicePrice(@PathVariable Long id) {
        log.debug("REST request to get ServicePrice : {}", id);
        Optional<ServicePrice> servicePrice = servicePriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicePrice);
    }

    /**
     * {@code DELETE  /service-prices/:id} : delete the "id" servicePrice.
     *
     * @param id the id of the servicePrice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-prices/{id}")
    public ResponseEntity<Void> deleteServicePrice(@PathVariable Long id) {
        log.debug("REST request to delete ServicePrice : {}", id);
        servicePriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
