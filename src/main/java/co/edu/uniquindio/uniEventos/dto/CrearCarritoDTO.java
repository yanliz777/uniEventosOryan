package co.edu.uniquindio.uniEventos.dto;

import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public record CrearCarritoDTO(
        @NotNull ObjectId idUsuario,
        @NotNull LocalDateTime fecha,
        List<DetalleCarritoDTO> items) {
}
