package com.salaogabriel.booking_api.controllers;

import com.salaogabriel.booking_api.dtos.BookingRequestDTO;
import com.salaogabriel.booking_api.entities.BookingEntity;
import com.salaogabriel.booking_api.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            BookingEntity newBooking = bookingService.createBooking(request);
            return ResponseEntity.ok(newBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}