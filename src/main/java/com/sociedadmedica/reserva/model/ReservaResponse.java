package com.sociedadmedica.reserva.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservaResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String tipoDocumento;
    private String numeroDocumento;
    private String correo;
    private String fechaReserva;
}
