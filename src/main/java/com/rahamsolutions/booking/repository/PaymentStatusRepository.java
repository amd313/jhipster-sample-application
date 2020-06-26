package com.rahamsolutions.booking.repository;

import com.rahamsolutions.booking.domain.PaymentStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
}
