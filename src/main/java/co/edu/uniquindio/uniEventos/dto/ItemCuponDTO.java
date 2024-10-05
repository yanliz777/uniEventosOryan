package co.edu.uniquindio.uniEventos.dto;

import java.time.LocalDateTime;

public record ItemCuponDTO(
        String id,
        String nombre,
        float descuento,
        String codigo,
        LocalDateTime fechaVencimiento) {
}
