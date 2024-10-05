package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionEventoDTO(
        String id,
        EstadoEvento estado,
        String nombre,
        String descripcion,
        TipoEvento tipo,
        LocalDateTime fechaEvento,
        String ciudad,
        String imagenPortada,
        String imagenLocalidades,
        List<Localidad> localidades
) {
}
