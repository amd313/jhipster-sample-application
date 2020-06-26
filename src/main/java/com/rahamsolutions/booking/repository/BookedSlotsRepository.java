package com.rahamsolutions.booking.repository;

import com.rahamsolutions.booking.domain.BookedSlots;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BookedSlots entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookedSlotsRepository extends JpaRepository<BookedSlots, Long> {
}
