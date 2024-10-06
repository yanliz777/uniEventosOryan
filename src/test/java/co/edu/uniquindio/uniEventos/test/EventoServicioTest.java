package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearEventoDTO;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class EventoServicioTest {

    @Autowired
    private EventoServicio eventoServicio;

    @Test
    public void crearEventoTest() throws EventoNoCreadoException {
        CrearEventoDTO eventoDTO = new CrearEventoDTO(
                "Pereira",
                "imagen puerto",
                "imagen localidades",
                "SUSO'S SHOW",
                "suso",
                "cc victoria",
                TipoEvento.CONCIERTO,
                LocalDateTime.of(2024, 12, 14, 16, 00),
                List.of(new Localidad(45000, "GENERAL", 250, 142))
        );

        String respuesta = eventoServicio.crearEvento(eventoDTO);

        Assertions.assertEquals("El evento ha sido creado con éxito.", respuesta);
    }
}
