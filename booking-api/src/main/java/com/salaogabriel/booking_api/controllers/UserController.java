package com.salaogabriel.booking_api.controllers;

import com.salaogabriel.booking_api.dtos.LoginRequestDTO;
import com.salaogabriel.booking_api.dtos.RegisterRequestDTO;
import com.salaogabriel.booking_api.entities.UserEntity;
import com.salaogabriel.booking_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody RegisterRequestDTO dto) {
        // Verifica se o email já existe na base de dados
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Email já está em uso.");
        }

        // Cria o novo cliente
        UserEntity newUser = new UserEntity();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setPhone(dto.getPhone());
        newUser.setPassword(dto.getPassword()); 
        newUser.setRole("ROLE_CLIENT"); // Garante que é sempre um cliente

        userRepository.save(newUser);
        return ResponseEntity.ok("Conta de cliente criada com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody LoginRequestDTO dto) {
        Optional<UserEntity> user = userRepository.findByEmail(dto.getEmail());
      
        if (user.isPresent() && user.get().getPassword().equals(dto.getPassword())) {
            return ResponseEntity.ok(user.get());
        }
        
        return ResponseEntity.status(401).build();
    }
}