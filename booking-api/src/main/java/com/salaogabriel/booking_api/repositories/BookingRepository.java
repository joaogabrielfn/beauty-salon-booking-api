package com.salaogabriel.booking_api.repositories;

import com.salaogabriel.booking_api.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    
    List<BookingEntity> findByStaffIdAndBookingDateTimeLessThanAndEndDateTimeGreaterThan(
            Long staffId, LocalDateTime end, LocalDateTime start);
}