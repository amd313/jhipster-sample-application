package com.rahamsolutions.booking.service;

import com.rahamsolutions.booking.domain.CustomerPayment;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomerPayment}.
 */
public interface CustomerPaymentService {

    /**
     * Save a customerPayment.
     *
     * @param customerPayment the entity to save.
     * @return the persisted entity.
     */
    CustomerPayment save(CustomerPayment customerPayment);

    /**
     * Get all the customerPayments.
     *
     * @return the list of entities.
     */
    List<CustomerPayment> findAll();


    /**
     * Get the "id" customerPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPayment> findOne(Long id);

    /**
     * Delete the "id" customerPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
