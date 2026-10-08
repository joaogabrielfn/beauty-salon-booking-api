package com.salaogabriel.booking_api.controllers;

import com.salaogabriel.booking_api.entities.ServiceEntity;
import com.salaogabriel.booking_api.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    // Endpoint para listar todos os serviços ativos do salão
    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        List<ServiceEntity> services = serviceRepository.findByActiveTrue();
        return ResponseEntity.ok(services);
    }
}