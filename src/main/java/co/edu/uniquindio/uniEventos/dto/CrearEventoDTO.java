package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CrearEventoDTO(
        @NotBlank @Length (max = 20) String ciudad,
        @NotBlank String imagenPortada,
        @NotBlank String imagenLocalidades,
        @NotBlank @Length (min = 5, max = 100) String nombre,
        @NotBlank @Length(max = 500) String descripcion,
        @NotBlank @Length (max = 100)String direccion,
        @NotNull TipoEvento tipoEvento,
        @NotNull LocalDateTime fechaEvento,
        @NotNull @NotEmpty List<Localidad> localidades
) {
}
