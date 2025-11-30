package com.sociedadmedica.reserva.controller;



import com.sociedadmedica.reserva.model.ReservaRequest;

import com.sociedadmedica.reserva.model.ReservaResponse;
import com.sociedadmedica.reserva.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin // para que React pueda pegarle desde http://localhost:3000
public class ReservaController {

    private final ReservaService service;

    // CREATE
    @PostMapping
    public ReservaResponse crearReserva(@RequestBody ReservaRequest request) {
        return service.crearReserva(request);
    }

    // READ - todas
    @GetMapping
    public List<ReservaResponse> listar() {
        return service.listarTodas();
    }

    // READ - una por id
    @GetMapping("/{id}")
    public ReservaResponse obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ReservaResponse actualizar(
            @PathVariable Long id,
            @RequestBody ReservaRequest request
    ) {
        return service.actualizarReserva(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarReserva(id);
    }
}
