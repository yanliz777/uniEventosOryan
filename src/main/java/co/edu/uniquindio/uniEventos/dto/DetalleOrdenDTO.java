package co.edu.uniquindio.uniEventos.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DetalleOrdenDTO(
        @NotBlank @Length(max = 10)String idEvento,
        @NotBlank String nombreLocalidad,
        @NotBlank int cantidad,
        @NotBlank float precio
) {
}
