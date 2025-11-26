package com.sociedadmedica.reserva.repository;

import com.sociedadmedica.reserva.model.ReservaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<ReservaModel,Long> {
}
