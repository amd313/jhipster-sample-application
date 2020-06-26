package com.rahamsolutions.booking.service.impl;

import com.rahamsolutions.booking.service.CustomerPaymentService;
import com.rahamsolutions.booking.domain.CustomerPayment;
import com.rahamsolutions.booking.repository.CustomerPaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerPayment}.
 */
@Service
@Transactional
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentServiceImpl.class);

    private final CustomerPaymentRepository customerPaymentRepository;

    public CustomerPaymentServiceImpl(CustomerPaymentRepository customerPaymentRepository) {
        this.customerPaymentRepository = customerPaymentRepository;
    }

    /**
     * Save a customerPayment.
     *
     * @param customerPayment the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPayment save(CustomerPayment customerPayment) {
        log.debug("Request to save CustomerPayment : {}", customerPayment);
        return customerPaymentRepository.save(customerPayment);
    }

    /**
     * Get all the customerPayments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPayment> findAll() {
        log.debug("Request to get all CustomerPayments");
        return customerPaymentRepository.findAll();
    }


    /**
     * Get one customerPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPayment> findOne(Long id) {
        log.debug("Request to get CustomerPayment : {}", id);
        return customerPaymentRepository.findById(id);
    }

    /**
     * Delete the customerPayment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPayment : {}", id);
        customerPaymentRepository.deleteById(id);
    }
}
