package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.CrearEventoDTO;
import co.edu.uniquindio.uniEventos.dto.MensajeDTO;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoCreadoException;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/eventos")
public class EventoControlador {

    private final EventoServicio eventoServicio;

    @PostMapping("/crear-evento")
    public ResponseEntity<MensajeDTO<String>> crearEvento(@Valid @RequestBody CrearEventoDTO crearEventoDTO){
        try {
            eventoServicio.crearEvento(crearEventoDTO);
            return ResponseEntity.ok().body( new MensajeDTO<>( false, "El evento fue creado correctamente."));
        } catch (Exception e){
            throw  new EventoNoCreadoException("Evento no creado " + e.getMessage());
        }
    }
}
