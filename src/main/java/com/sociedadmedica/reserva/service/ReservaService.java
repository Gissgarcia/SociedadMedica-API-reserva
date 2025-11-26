package com.sociedadmedica.reserva.service;

import com.sociedadmedica.reserva.model.ReservaModel;
import com.sociedadmedica.reserva.model.ReservaRequest;
import com.sociedadmedica.reserva.model.ReservaResponse;
import com.sociedadmedica.reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaResponse crearReserva(ReservaRequest req) {

        ReservaModel entity = ReservaModel.builder()
                .nombre(req.getNombre())
                .apellido(req.getApellido())
                .edad(req.getEdad())
                .tipoDocumento(req.getTipoDocumento())
                .numeroDocumento(req.getNumeroDocumento())
                .correo(req.getCorreo())
                .fechaReserva(req.getFechaReserva())
                .build();

        ReservaModel saved = repository.save(entity);

        return ReservaResponse.builder()
                .id(saved.getId())
                .nombre(saved.getNombre())
                .apellido(saved.getApellido())
                .edad(saved.getEdad())
                .tipoDocumento(saved.getTipoDocumento().name())
                .numeroDocumento(saved.getNumeroDocumento())
                .correo(saved.getCorreo())
                .fechaReserva(saved.getFechaReserva())
                .build();
    }
    public List<ReservaResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ✅ Buscar por id
    public ReservaResponse buscarPorId(Long id) {
        ReservaModel entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return toResponse(entity);
    }

    // ✅ Actualizar
    public ReservaResponse actualizarReserva(Long id, ReservaRequest req) {
        ReservaModel entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        entity.setNombre(req.getNombre());
        entity.setApellido(req.getApellido());
        entity.setEdad(req.getEdad());
        entity.setTipoDocumento(req.getTipoDocumento());
        entity.setNumeroDocumento(req.getNumeroDocumento());
        entity.setCorreo(req.getCorreo());
        entity.setFechaReserva(req.getFechaReserva());

        ReservaModel updated = repository.save(entity);
        return toResponse(updated);
    }

    // ✅ Eliminar
    public void eliminarReserva(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada");
        }
        repository.deleteById(id);
    }

    // 👉 helper para no repetir código
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
