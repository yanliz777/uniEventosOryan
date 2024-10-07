package co.edu.uniquindio.uniEventos.dto;

import org.bson.types.ObjectId;

public record DetalleCarritoDTO(
        int cantidad,
        String nombreLocalidad,
        ObjectId idEvento
) {
}
