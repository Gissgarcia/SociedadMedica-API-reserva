package com.sociedadmedica.reserva.service;

import com.sociedadmedica.reserva.model.ReservaModel;
import com.sociedadmedica.reserva.model.ReservaRequest;
import com.sociedadmedica.reserva.model.ReservaResponse;
import com.sociedadmedica.reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;

    // CREATE
    public ReservaResponse crearReserva(ReservaRequest request) {
        ReservaModel model = ReservaModel.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .edad(request.getEdad())
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .correo(request.getCorreo())
                .fechaReserva(request.getFechaReserva())
                .build();

        ReservaModel guardada = repository.save(model);
        return toResponse(guardada);
    }

    // READ: todas
    public List<ReservaResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // READ: por id
    public ReservaResponse buscarPorId(Long id) {
        ReservaModel model = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return toResponse(model);
    }

    // READ: por correo (para "Mis reservas")
    public List<ReservaResponse> listarPorCorreo(String correo) {
        return repository.findByCorreo(correo)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // UPDATE
    public ReservaResponse actualizarReserva(Long id, ReservaRequest request) {
        ReservaModel model = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        model.setNombre(request.getNombre());
        model.setApellido(request.getApellido());
        model.setEdad(request.getEdad());
        model.setTipoDocumento(request.getTipoDocumento());
        model.setNumeroDocumento(request.getNumeroDocumento());
        model.setCorreo(request.getCorreo());
        model.setFechaReserva(request.getFechaReserva());

        ReservaModel guardada = repository.save(model);
        return toResponse(guardada);
    }

    // DELETE
    public void eliminarReserva(Long id) {
        repository.deleteById(id);
    }

    // helper
    private ReservaResponse toResponse(ReservaModel e) {
        return ReservaResponse.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .edad(e.getEdad())
                .tipoDocumento(e.getTipoDocumento().name())
                .numeroDocumento(e.getNumeroDocumento())
                .correo(e.getCorreo())
                .fechaReserva(e.getFechaReserva())
                .build();
    }
}
