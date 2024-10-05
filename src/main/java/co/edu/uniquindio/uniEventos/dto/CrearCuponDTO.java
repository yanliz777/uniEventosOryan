package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record CrearCuponDTO(
        @NotBlank @Length(min = 3, max = 50) String nombre,
        @NotNull float descuento,
        @NotNull @Future LocalDateTime fechaVencimiento,
        @NotNull TipoCupon tipoCupon) {
}