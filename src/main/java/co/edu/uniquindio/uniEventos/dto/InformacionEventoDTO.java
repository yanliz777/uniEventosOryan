package co.edu.uniquindio.uniEventos.dto;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionEventoDTO(
        String id,
        String imagenPortada,
        String direccion,
        String ciudad,
        LocalDateTime fechaEvento,
        TipoEvento tipoEvento,
        EstadoEvento disponibilidad,
        String descripcion
) {
}
