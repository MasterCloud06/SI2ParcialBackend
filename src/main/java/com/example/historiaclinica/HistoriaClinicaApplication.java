package com.example.historiaclinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.historiaclinica.model")  // Asegura que el paquete de entidades sea escaneado
@EnableJpaRepositories("com.example.historiaclinica.repository")  // Escanea el paquete de repositorios
public class HistoriaClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoriaClinicaApplication.class, args);
    }
}
