package com.rahamsolutions.booking.service.impl;

import com.rahamsolutions.booking.service.PaymentStatusService;
import com.rahamsolutions.booking.domain.PaymentStatus;
import com.rahamsolutions.booking.repository.PaymentStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PaymentStatus}.
 */
@Service
@Transactional
public class PaymentStatusServiceImpl implements PaymentStatusService {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusServiceImpl.class);

    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentStatusServiceImpl(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    /**
     * Save a paymentStatus.
     *
     * @param paymentStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentStatus save(PaymentStatus paymentStatus) {
        log.debug("Request to save PaymentStatus : {}", paymentStatus);
        return paymentStatusRepository.save(paymentStatus);
    }

    /**
     * Get all the paymentStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentStatus> findAll() {
        log.debug("Request to get all PaymentStatuses");
        return paymentStatusRepository.findAll();
    }


    /**
     * Get one paymentStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentStatus> findOne(Long id) {
        log.debug("Request to get PaymentStatus : {}", id);
        return paymentStatusRepository.findById(id);
    }

    /**
     * Delete the paymentStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentStatus : {}", id);
        paymentStatusRepository.deleteById(id);
    }
}
