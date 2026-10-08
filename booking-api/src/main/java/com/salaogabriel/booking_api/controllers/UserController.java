package com.salaogabriel.booking_api.controllers;

import com.salaogabriel.booking_api.entities.UserEntity;
import com.salaogabriel.booking_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/staff")
    public ResponseEntity<List<UserEntity>> getStaff() {
        List<UserEntity> staffMembers = userRepository.findByRole("ROLE_STAFF");
        return ResponseEntity.ok(staffMembers);
    }
}