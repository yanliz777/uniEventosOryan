package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cupon;
import co.edu.uniquindio.uniEventos.servicios.implementacion.CuponServicioImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cupones")
public class CuponControlador {

    private  final CuponServicioImpl cuponServicio;

    @PostMapping("/crear-cupon")
    public ResponseEntity<MensajeDTO<String>> crearCupon(@Valid @RequestBody CrearCuponDTO cupon) throws Exception {
        cuponServicio.crearCupon(cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta creada exitosamente"));

    }
    @PutMapping("/actualizar-cupon")
    public ResponseEntity<MensajeDTO<String>> actualizarCupon(@Valid @RequestBody EditarCuponDTO cupon) throws Exception {
        cuponServicio.editarCupon(cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupon actualizado exitosamente"));
    }

    @DeleteMapping("/eliminar-cupon/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarCupon(@PathVariable String id) throws Exception {
        cuponServicio.eliminarCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupon eliminado exitosamente"));
    }

    @GetMapping("/obtener-informacion/{id}")
    public ResponseEntity<MensajeDTO<InformacionCuponDTO>> obtenerInformacionCupon(@PathVariable String id) throws Exception {
        InformacionCuponDTO cuponInfo = cuponServicio.obtenerInformacionCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, cuponInfo));
    }

    @GetMapping("/listar-cupones-activos")
    public ResponseEntity<MensajeDTO<List<ItemCuponDTO>>> listarCuponesActivos() throws Exception {
        List<ItemCuponDTO> cupones = cuponServicio.listarCuponesDisponibles();
        return ResponseEntity.ok(new MensajeDTO<>(false, cupones));
    }

    @PostMapping("/redimir-cupon/{codigo}")
    public ResponseEntity<MensajeDTO<String>> redimirCupon(@PathVariable String codigo) throws Exception {
        boolean resultado = cuponServicio.redimirCupon(codigo);
        return resultado
                ? ResponseEntity.ok(new MensajeDTO<>(false, "Cupon redimido exitosamente"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, "El cupon no pudo ser redimido"));
    }

}
