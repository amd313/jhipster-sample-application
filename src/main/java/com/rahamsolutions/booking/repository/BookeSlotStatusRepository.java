package com.rahamsolutions.booking.repository;

import com.rahamsolutions.booking.domain.BookeSlotStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BookeSlotStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookeSlotStatusRepository extends JpaRepository<BookeSlotStatus, Long> {
}
