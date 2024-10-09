package co.edu.uniquindio.uniEventos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CrearOrdenDTO(
        String mondeda,
        @NotBlank String idCliente,
        @NotNull LocalDateTime fecha,
        @NotBlank String codigoPasarela,
        @NotBlank @Length(max = 30) String idCupon,
        List<DetalleOrdenDTO> items,
        @NotNull float total,
        PagoDTO pago
   ) {
}
