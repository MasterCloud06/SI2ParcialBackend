package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.Role;
import com.example.historiaclinica.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
