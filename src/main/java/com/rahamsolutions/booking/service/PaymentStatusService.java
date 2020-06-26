package com.rahamsolutions.booking.service;

import com.rahamsolutions.booking.domain.PaymentStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PaymentStatus}.
 */
public interface PaymentStatusService {

    /**
     * Save a paymentStatus.
     *
     * @param paymentStatus the entity to save.
     * @return the persisted entity.
     */
    PaymentStatus save(PaymentStatus paymentStatus);

    /**
     * Get all the paymentStatuses.
     *
     * @return the list of entities.
     */
    List<PaymentStatus> findAll();


    /**
     * Get the "id" paymentStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentStatus> findOne(Long id);

    /**
     * Delete the "id" paymentStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
