package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;

import java.time.LocalDateTime;

public record InformacionCuponDTO(
        String nombre,
        float descuento,
        String codigo,
        LocalDateTime fechaVencimiento,
        TipoCupon tipo,
        EstadoCupon estado) {
}
