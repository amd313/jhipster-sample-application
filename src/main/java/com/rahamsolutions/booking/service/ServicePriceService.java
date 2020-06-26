package com.rahamsolutions.booking.service;

import com.rahamsolutions.booking.domain.ServicePrice;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServicePrice}.
 */
public interface ServicePriceService {

    /**
     * Save a servicePrice.
     *
     * @param servicePrice the entity to save.
     * @return the persisted entity.
     */
    ServicePrice save(ServicePrice servicePrice);

    /**
     * Get all the servicePrices.
     *
     * @return the list of entities.
     */
    List<ServicePrice> findAll();


    /**
     * Get the "id" servicePrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServicePrice> findOne(Long id);

    /**
     * Delete the "id" servicePrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
