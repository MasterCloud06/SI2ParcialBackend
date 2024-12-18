package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.RoleName;
import com.example.historiaclinica.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<Users> findByRoles_Name(RoleName roleName);
    
}
