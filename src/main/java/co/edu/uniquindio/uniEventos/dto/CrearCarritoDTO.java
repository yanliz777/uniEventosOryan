package co.edu.uniquindio.uniEventos.dto;

import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record CrearCarritoDTO(
        @NotNull ObjectId idUsuario,
        @NotNull LocalDateTime fecha) {
}
