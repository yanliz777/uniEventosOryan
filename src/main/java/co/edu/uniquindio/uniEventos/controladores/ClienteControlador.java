package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.*;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CuentaNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.HistorialOrdenesVacionException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoCancelableException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenYaCanceladaException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.servicios.implementacion.CarritoServicioImpl;
import co.edu.uniquindio.uniEventos.servicios.implementacion.CuponServicioImpl;
import co.edu.uniquindio.uniEventos.servicios.interfaces.OrdenServicio;
import com.mercadopago.resources.preference.Preference;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente")
@SecurityRequirement(name = "bearerAuth")
public class ClienteControlador {

    private final CarritoServicioImpl carritoServicio;
    private final OrdenServicio ordenServicio;
    private  final CuponServicioImpl cuponServicio;
    
    @PutMapping("/carrito/editarItem/{idCarrito}")
    public ResponseEntity<MensajeDTO<String>> editarItem(
            @PathVariable String idCarrito,
            @RequestParam("idEvento") String idEvento,
            @RequestBody DetalleCarritoDTO detalleActualizado) throws Exception {

        carritoServicio.editarItem(idCarrito, idEvento, detalleActualizado);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Item actualizado correctamente"));
    }

    @PostMapping("/carrito/agregarItem/{idCarrito}")
    public ResponseEntity<MensajeDTO<String>> agregarItem(@PathVariable String idCarrito, @RequestBody DetalleCarritoDTO item) throws Exception {
        carritoServicio.agregarItem(idCarrito, item);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Item agregado correctamente"));
    }

    @PostMapping("/orden/realizar-pago")
    public ResponseEntity<MensajeDTO<Preference>> realizarPago(@RequestParam("idOrden") String idOrden) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                ordenServicio.realizarPago(idOrden)));
    }

    // /orden/crear
    @PostMapping("/orden/crear")
    public ResponseEntity<MensajeDTO<String>> crearOrden(@RequestBody @Valid CrearOrdenDTO crearOrdenDTO) {
        try {
            String resultado = ordenServicio.crearOrden(crearOrdenDTO);
            return ResponseEntity.ok(new MensajeDTO<>(false, resultado));
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, "Cliente no encontrado"));
        } catch (EventoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, "Evento no encontrado"));
        } catch (CuponNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, "Cupón no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, "Error al crear la orden"));
        }
    }

    // orden/obtener/{idorden}
    @GetMapping("/orden/obtener/{id}")
    public ResponseEntity<MensajeDTO<ObtenerOrdenDTO>> obtenerOrden(@PathVariable String id) throws Exception {
        ObtenerOrdenDTO orden = ordenServicio.obtenerOrden(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, orden));
    }

   // /orden/historial/{idorden}
   @GetMapping("/orden/historial/{idUsuario}")
   public ResponseEntity<MensajeDTO<List<ItemOrdenDTO>>> historialOrdenes(@PathVariable String idUsuario) throws HistorialOrdenesVacionException, CuentaNoEncontradaException {
       List<ItemOrdenDTO> historial = ordenServicio.historialOrdenes(idUsuario);
       MensajeDTO<List<ItemOrdenDTO>> mensajeDTO = new MensajeDTO<>(false, historial);
       return ResponseEntity.ok(mensajeDTO);
   }

    // /orden/cancelar/- orden
    @PostMapping("/orden/cancelar/{idOrden}")
    public ResponseEntity<MensajeDTO<String>> cancelarOrden(@PathVariable String idOrden) throws OrdenNoEncontradaException, OrdenYaCanceladaException, OrdenNoCancelableException {
        String resultado = ordenServicio.cancelarOrden(idOrden);
        MensajeDTO<String> mensajeDTO = new MensajeDTO<>(false, resultado);
        return ResponseEntity.ok(mensajeDTO);
    }

    // /carrito/crear/{idUsuario}
    @PostMapping("/carrito/crear/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> crearCarrito(@PathVariable String idUsuario) throws CarritoNoEncontradoException {
        try {
            carritoServicio.crearCarrito(idUsuario);
            return ResponseEntity.ok().body( new MensajeDTO<>( false, "El Carrito fue creado correctamente."));
        } catch (Exception e){
            throw  new CarritoNoEncontradoException("Carrito no creado " + e.getMessage());
        }
    }

    // /carrito/obtener/{idUsuario}
    @GetMapping("/carrito/obtener/{idUsuario}")
    public ResponseEntity<MensajeDTO<Carrito>> obtenerCarritoUsuario(@PathVariable String idUsuario) throws CarritoNoEncontradoException {
        try {
            Carrito carrito = carritoServicio.obtenerCarritoUsuario(idUsuario);
            return ResponseEntity.ok().body( new MensajeDTO<>( false, carrito));
        } catch (Exception e){
            throw  new CarritoNoEncontradoException("Carrito no creado " + e.getMessage());
        }
    }

    @DeleteMapping("/carrito/eliminarItem/{idCarrito}")
    public ResponseEntity<MensajeDTO<String>> eliminarItem(
            @PathVariable String idCarrito,
            @RequestParam("idEvento") String idEvento) throws Exception {
        carritoServicio.eliminarItem(idCarrito, idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Item eliminado correctamente"));
    }

    @PostMapping("/orden/notificacion-pago")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> requestBody) {
        ordenServicio.recibirNotificacionMercadoPago(requestBody);
    }

    @PostMapping("/cupon/redimir")
    public ResponseEntity<MensajeDTO<String>> redimirCupon(@RequestParam String codigo) throws Exception {
        boolean resultado = cuponServicio.redimirCupon(codigo);
        return resultado
                ? ResponseEntity.ok(new MensajeDTO<>(false, "Cupon redimido exitosamente"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, "El cupon no pudo ser redimido"));
    }
}
