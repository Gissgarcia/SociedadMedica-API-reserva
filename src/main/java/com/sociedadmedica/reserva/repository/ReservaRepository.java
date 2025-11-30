package com.sociedadmedica.reserva.repository;

import com.sociedadmedica.reserva.model.ReservaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    // Todas las reservas asociadas a un correo
    List<ReservaModel> findByCorreo(String correo);
}
