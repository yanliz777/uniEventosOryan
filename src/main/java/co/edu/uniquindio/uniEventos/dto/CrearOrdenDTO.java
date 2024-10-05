package co.edu.uniquindio.uniEventos.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CrearOrdenDTO(

        @NotBlank @Length(max = 30)String id,
        String mondeda,
        @NotBlank String idCliente,
        @NotBlank LocalDateTime fecha,
        @NotBlank String codigoPasarela,
        @NotBlank @Length(max = 30) String idCupon,
        List<DetalleOrdenDTO> items,
        @NotBlank float total,
        PagoDTO pago
   ) {
}
