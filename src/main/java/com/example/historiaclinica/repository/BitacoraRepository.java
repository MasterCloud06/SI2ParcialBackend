// src/main/java/com/tu_proyecto/repository/BitacoraRepository.java
package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
}
