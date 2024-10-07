package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.FiltroEventoDTO;
import co.edu.uniquindio.uniEventos.dto.InformacionEventoDTO;
import co.edu.uniquindio.uniEventos.dto.ItemEventoDTO;
import co.edu.uniquindio.uniEventos.dto.MensajeDTO;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/publico")
public class PublicoControlador {

    private final EventoServicio eventoServicio;

    @PostMapping("/evento/filtrar")
    public ResponseEntity<MensajeDTO<List<ItemEventoDTO>>> filtrarEventos(@RequestBody FiltroEventoDTO filtroEventoDTO) {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, eventoServicio.filtrarEventos(filtroEventoDTO)));
    }
    @GetMapping("/evento/obtener/{id}")
    public ResponseEntity<MensajeDTO<InformacionEventoDTO>> obtenerInfoEvento(@PathVariable String id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                eventoServicio.obtenerInformacionEvento(id)));
    }

    @GetMapping("/evento/obtener-todos/{pagina}")
    public ResponseEntity<MensajeDTO<List<ItemEventoDTO>>> obtenerInfoEvento(
            @PathVariable int pagina,
            @RequestParam(defaultValue = "1") int tamano) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                eventoServicio.listarEventosPaginados(pagina, tamano)));
    }
}
