package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CrearEventoDTO(
        @NotBlank @Length (max = 20) String ciudad,
        @NotBlank String imagenImportada,
        @NotBlank String imagenLocalidades,
        @NotBlank @Length (min = 5, max = 100) String nombre,
        @NotBlank @Length(max = 500) String descripcion,
        @NotBlank @Length (max = 100)String direccion,
        @NotBlank TipoEvento tipoEvento,
        @NotBlank LocalDateTime fechaEvento,
        @NotBlank List<Localidad> localidades
) {
}
