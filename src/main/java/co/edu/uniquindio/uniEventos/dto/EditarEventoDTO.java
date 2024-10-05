package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EditarEventoDTO(
        @NotBlank String imagenPortada,
        @NotBlank EstadoEvento estado,
        @NotBlank String descripcion,
        @NotBlank String imagenLocalidades,
        @NotBlank LocalDateTime fechaEvento,
        @NotBlank String id
) {
}
