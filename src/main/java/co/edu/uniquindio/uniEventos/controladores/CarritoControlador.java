package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.MensajeDTO;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.servicios.implementacion.CarritoServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carrito")
public class CarritoControlador {

    private final CarritoServicioImpl carritoServicio;


    @GetMapping("/listarCarrito")
    public ResponseEntity<List<Carrito>> obtenerCarrito() {
        List<Carrito> carrito = carritoServicio.findAll();
        return ResponseEntity.ok(carrito);
    }

    @GetMapping("/obtenerTablaCarrito")
    public ResponseEntity<MensajeDTO<String>> obtenerTablaCarrito(@RequestParam List<DetalleCarrito> listaCarrito) throws Exception {
        return ResponseEntity.ok(new MensajeDTO<>(false,carritoServicio.listaDetallesCarrito(listaCarrito)));
    }
}
