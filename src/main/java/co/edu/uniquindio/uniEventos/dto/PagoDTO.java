package co.edu.uniquindio.uniEventos.dto;

import jakarta.validation.constraints.NotBlank;

public record PagoDTO(
        String moneda,
        String codigoAutorizacion,
        String tipoPago,
        String detalleEstado,
        float valorTransaccion,
        String estado

) {
}



