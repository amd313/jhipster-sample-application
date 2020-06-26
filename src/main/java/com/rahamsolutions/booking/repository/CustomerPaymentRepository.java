package com.rahamsolutions.booking.repository;

import com.rahamsolutions.booking.domain.CustomerPayment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentRepository extends JpaRepository<CustomerPayment, Long> {
}
