package com.rahamsolutions.booking.service.impl;

import com.rahamsolutions.booking.service.ServicePriceService;
import com.rahamsolutions.booking.domain.ServicePrice;
import com.rahamsolutions.booking.repository.ServicePriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServicePrice}.
 */
@Service
@Transactional
public class ServicePriceServiceImpl implements ServicePriceService {

    private final Logger log = LoggerFactory.getLogger(ServicePriceServiceImpl.class);

    private final ServicePriceRepository servicePriceRepository;

    public ServicePriceServiceImpl(ServicePriceRepository servicePriceRepository) {
        this.servicePriceRepository = servicePriceRepository;
    }

    /**
     * Save a servicePrice.
     *
     * @param servicePrice the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServicePrice save(ServicePrice servicePrice) {
        log.debug("Request to save ServicePrice : {}", servicePrice);
        return servicePriceRepository.save(servicePrice);
    }

    /**
     * Get all the servicePrices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServicePrice> findAll() {
        log.debug("Request to get all ServicePrices");
        return servicePriceRepository.findAll();
    }


    /**
     * Get one servicePrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServicePrice> findOne(Long id) {
        log.debug("Request to get ServicePrice : {}", id);
        return servicePriceRepository.findById(id);
    }

    /**
     * Delete the servicePrice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServicePrice : {}", id);
        servicePriceRepository.deleteById(id);
    }
}
