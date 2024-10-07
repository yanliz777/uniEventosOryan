package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoEncontradoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.repositorios.CarritoRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CarritoServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoServicioImpl implements CarritoServicio {

    private final CarritoRepo carritoRepo;
    @Override
    public String crearCarrito(String idUsuario) throws CarritoNoCreadoException {
        try {
            Carrito carrito = new Carrito();
            carrito.setIdUsuario( new ObjectId(idUsuario) );
            carrito.setFecha( LocalDateTime.now() );
            List<DetalleCarrito> detalles = new ArrayList<>();
            carrito.setItems(detalles);

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
    public Carrito obtenerCarritoUsuario(String idUsuario) throws CarritoNoEncontradoException {
        try {

            Optional<Carrito> carritoBuscado = carritoRepo.findByIdUsuario(idUsuario);

            if(carritoBuscado.isEmpty()){
                throw new CarritoNoEncontradoException("El carrito no fue encontrado. ");
            }

            return carritoBuscado.get();
        } catch (Exception e){
            throw new CarritoNoEncontradoException("El carrito no fue encontrado. " + e.getMessage());
        }
    }

    @Override
    public List<Carrito> findAll() {
        return carritoRepo.findAll();
    }
}