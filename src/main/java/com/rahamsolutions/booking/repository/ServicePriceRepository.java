package com.rahamsolutions.booking.repository;

import com.rahamsolutions.booking.domain.ServicePrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServicePrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicePriceRepository extends JpaRepository<ServicePrice, Long> {
}
