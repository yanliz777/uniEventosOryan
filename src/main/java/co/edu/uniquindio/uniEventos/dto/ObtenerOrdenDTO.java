package co.edu.uniquindio.uniEventos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ObtenerOrdenDTO(
        String id,
        String idCliente,
        LocalDateTime fecha,
        String codigoPasarela,
        List<DetalleOrdenDTO> detalles,
        float total,
        PagoDTO pago
) {
}
