package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoCreadoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEditadoException;
import co.edu.uniquindio.uniEventos.servicios.implementacion.CuponServicioImpl;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import co.edu.uniquindio.uniEventos.servicios.interfaces.ImagenesServicio;
import co.edu.uniquindio.uniEventos.servicios.interfaces.OrdenServicio;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdministradorControlador {

    private final EventoServicio eventoServicio;
    private final OrdenServicio ordenServicio;
    private final ImagenesServicio imagenesServicio;
    private  final CuponServicioImpl cuponServicio;

    @PutMapping("/evento/editar")
    public ResponseEntity<MensajeDTO<String>> editarEvento(@RequestBody EditarEventoDTO editarEventoDTO){
        try {
            return ResponseEntity.ok().body(new MensajeDTO<>(false, eventoServicio.editarEvento(editarEventoDTO)));
        } catch (Exception e){
            throw new EventoNoEditadoException("Evento no editado");
        }
    }

    @PostMapping("/imagen/subir")
    public ResponseEntity<MensajeDTO<String>> subir(@RequestParam("imagen") MultipartFile imagen) throws Exception {
        String respuesta = imagenesServicio.subirImagen(imagen);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta));
    }

    @PostMapping("/evento/crear")
    public ResponseEntity<MensajeDTO<String>> crearEvento(@Valid @RequestBody CrearEventoDTO crearEventoDTO){
        try {
            eventoServicio.crearEvento(crearEventoDTO);
            return ResponseEntity.ok().body( new MensajeDTO<>( false, "El evento fue creado correctamente."));
        } catch (Exception e){
            throw  new EventoNoCreadoException("Evento no creado " + e.getMessage());
        }
    }

    @PostMapping("/cupon/crear")
    public ResponseEntity<MensajeDTO<String>> crearCupon(@Valid @RequestBody CrearCuponDTO cupon) throws Exception {
        cuponServicio.crearCupon(cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta creada exitosamente"));

    }

    @PutMapping("/cupon/editar")
    public ResponseEntity<MensajeDTO<String>> actualizarCupon(@Valid @RequestBody EditarCuponDTO cupon) throws Exception {
        cuponServicio.editarCupon(cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupon actualizado exitosamente"));
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

    @GetMapping("/cupon/obtener-informacion/{id}")
    public ResponseEntity<MensajeDTO<InformacionCuponDTO>> obtenerInformacionCupon(@PathVariable String id) throws Exception {
        // TODO PENDIENTE DE MODIFICAR PARA OBTENER EL CUPON POR EL CODIGO DE CUPON Y NO POR EL ID
        InformacionCuponDTO cuponInfo = cuponServicio.obtenerInformacionCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, cuponInfo));
    }

    @GetMapping("/cupon/obtener-todos")
    public ResponseEntity<MensajeDTO<List<ItemCuponDTO>>> listarCuponesActivos() throws Exception {
        List<ItemCuponDTO> cupones = cuponServicio.listarCuponesDisponibles();
        return ResponseEntity.ok(new MensajeDTO<>(false, cupones));
    }

    @DeleteMapping("/imagen/eliminar")
    public ResponseEntity<MensajeDTO<String>> eliminar(@RequestParam("nombreImagen") String nombreImagen) throws Exception{
        imagenesServicio.eliminarImagen( nombreImagen );
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "La imagen fue eliminada " +
                "correctamente"));
    }
    
    @DeleteMapping("/evento/eliminar/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String id){
        String respuesta = eventoServicio.eliminarEvento(id);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta));
    }

    @DeleteMapping("/eliminar-cupon/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarCupon(@PathVariable String id) throws Exception {
        cuponServicio.eliminarCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupon eliminado exitosamente"));
    }
}
