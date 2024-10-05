package co.edu.uniquindio.uniEventos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record HistorialOrdenDTO(
        String idOrden,
        LocalDateTime fecha,
        float total,
        String codigoPasarela,
        List<DetalleOrdenResumenDTO> items
) {}
