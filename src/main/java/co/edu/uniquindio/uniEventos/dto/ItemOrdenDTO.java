package co.edu.uniquindio.uniEventos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ItemOrdenDTO(
        String idOrden,
        String idCliente,
        LocalDateTime fecha,
        String codigoPasarela,
        String idCupon,
        float total,
        List<DetalleOrdenResumenDTO> detalles
) {}
