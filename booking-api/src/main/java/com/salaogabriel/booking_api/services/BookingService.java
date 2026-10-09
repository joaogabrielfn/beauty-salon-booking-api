package com.salaogabriel.booking_api.services;

import com.salaogabriel.booking_api.dtos.BookingRequestDTO;
import com.salaogabriel.booking_api.entities.BookingEntity;
import com.salaogabriel.booking_api.entities.ServiceEntity;
import com.salaogabriel.booking_api.entities.UserEntity;
import com.salaogabriel.booking_api.repositories.BookingRepository;
import com.salaogabriel.booking_api.repositories.ServiceRepository;
import com.salaogabriel.booking_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public BookingEntity createBooking(BookingRequestDTO request) {
        // 1. Procurar as entidades na base de dados (Cliente, Serviço e Profissional)
        UserEntity client = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
                
        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                
        UserEntity staff = userRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        // 2. Calcular o horário de término (início + duração do serviço)
        LocalDateTime start = request.getBookingDateTime();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        // 3. Verificar se o profissional já tem algum agendamento que conflite com este horário
        List<BookingEntity> conflicts = bookingRepository
                .findByStaffIdAndBookingDateTimeLessThanAndEndDateTimeGreaterThan(
                        staff.getId(), end, start);

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("O profissional já tem um agendamento neste horário.");
        }

        // 4. Montar o agendamento e guardar na base de dados
        BookingEntity booking = new BookingEntity();
        booking.setClient(client);
        booking.setService(service);
        booking.setStaff(staff);
        booking.setBookingDateTime(start);
        booking.setEndDateTime(end);
        booking.setTotalPrice(service.getPrice());
        booking.setStatus("SCHEDULED");

        return bookingRepository.save(booking);
    }
}