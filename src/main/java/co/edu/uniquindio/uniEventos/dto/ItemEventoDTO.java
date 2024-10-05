package co.edu.uniquindio.uniEventos.dto;

import java.time.LocalDateTime;

public record ItemEventoDTO(
        String urlImagenPoster,
        String nombre,
        LocalDateTime fecha,
        String ciudad
) {
}
