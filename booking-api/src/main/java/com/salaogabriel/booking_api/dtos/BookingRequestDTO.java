package com.salaogabriel.booking_api.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestDTO {

    private Long clientId;
    private Long serviceId;
    private Long staffId;
    
    private LocalDateTime bookingDateTime; 
}