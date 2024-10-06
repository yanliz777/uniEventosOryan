package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.repositorios.CarritoRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CarritoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoServicioImpl implements CarritoServicio {

    private final CarritoRepo carritoRepo;
    @Override
    public String crearCarrito(CrearCarritoDTO crearCarritoDTO) throws CarritoNoCreadoException {
        try {
            Carrito carrito = new Carrito();
            carrito.setIdUsuario( crearCarritoDTO.idUsuario() );
            carrito.setFecha( crearCarritoDTO.fecha() );

            carritoRepo.save(carrito);

            return "Carrito creado exitosamente.";
        } catch (Exception e){
            throw new CarritoNoCreadoException("El carrito no fue creado. " + e.getMessage());
        }
    }

    @Override
    public String listaDetallesCarrito (List<DetalleCarrito> listaCarrito) {
        return listaCarrito.toString();
    }

    @Override
    public String eliminarItem(String idCarrito, String idEvento) throws Exception {
        Carrito carrito = carritoRepo.findById(idCarrito).orElseThrow(() -> new Exception("Carrito no encontrado"));
        carritoRepo.delete(carrito);
        return "Item eliminado correctamente";
    }

    @Override
    public void agregarItem(String idCarrito, DetalleCarrito item) throws Exception {
        Carrito carrito = carritoRepo.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        carrito.getItems().add(item);

        carritoRepo.save(carrito);
    }

    @Override
    public List<Carrito> findAll() {
        return carritoRepo.findAll();
    }
}