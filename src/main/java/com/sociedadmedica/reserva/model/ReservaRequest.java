package com.sociedadmedica.reserva.model;

import lombok.Data;

@Data
public class ReservaRequest {
    private String nombre;
    private String apellido;
    private Integer edad;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String correo;
    private String fechaReserva;

}
