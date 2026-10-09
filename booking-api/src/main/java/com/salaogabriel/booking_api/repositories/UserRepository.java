package com.salaogabriel.booking_api.repositories;

import com.salaogabriel.booking_api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByRole(String role);
    
    // Novo método para procurar o utilizador pelo email no momento do login
    Optional<UserEntity> findByEmail(String email);
}