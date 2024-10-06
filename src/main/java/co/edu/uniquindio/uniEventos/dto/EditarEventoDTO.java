package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record EditarEventoDTO(
        @NotBlank String id,
        String imagenPortada,
        String imagenLocalidades,
        @Length(max = 500) String descripcion,
        EstadoEvento estado,
        String direccion,
        String ciudad,
        LocalDateTime fechaEvento,
        String nombre

) {
}
