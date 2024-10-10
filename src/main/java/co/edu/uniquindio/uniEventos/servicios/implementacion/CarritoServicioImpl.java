package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.DetalleCarritoDTO;
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
import java.util.stream.Collectors;

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
        Carrito carrito = carritoRepo.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        List<DetalleCarrito> itemsActualizados = carrito.getItems().stream()
                .filter(item -> !item.getIdEvento().toString().equals(idEvento))
                .collect(Collectors.toList());

        if (itemsActualizados.size() == carrito.getItems().size()) {
            throw new Exception("Evento no encontrado en el carrito");
        }

        carrito.setItems(itemsActualizados);
        carritoRepo.save(carrito);

        return "Item eliminado correctamente";
    }

    @Override
    public void agregarItem(String idCarrito, DetalleCarritoDTO itemDTO) throws Exception {
        Optional<Carrito> carrito = carritoRepo.findById(idCarrito);

        if(carrito.isEmpty()){
            throw new Exception("Carrito no encontrado.");
        }

        DetalleCarrito item = new DetalleCarrito(
                itemDTO.idEvento(),
                itemDTO.cantidad(),
                itemDTO.nombreLocalidad()
        );

        carrito.get().getItems().add(item);

        carritoRepo.save(carrito.get());
    }

    @Override
    public String editarItem(String idCarrito, String idEvento, DetalleCarritoDTO detalleActualizado) throws Exception {
        Carrito carrito = carritoRepo.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        // Encontrar el item que necesita ser actualizado
        boolean itemEncontrado = false;

        for (DetalleCarrito item : carrito.getItems()) {
            if (item.getIdEvento().toString().equals(idEvento)) {
                // Actualizar las propiedades del item con la información del detalleActualizado
                item.setCantidad(detalleActualizado.cantidad());
                item.setNombreLocalidad(detalleActualizado.nombreLocalidad());
                itemEncontrado = true;
                break;
            }
        }

        // Si no se encontró el item, lanzar una excepción
        if (!itemEncontrado) {
            throw new Exception("Evento no encontrado en el carrito");
        }

        // Guardar los cambios en el repositorio
        carritoRepo.save(carrito);

        return "Item actualizado correctamente";
    }

    @Override
    public Carrito obtenerCarritoUsuario(String idUsuario) throws CarritoNoEncontradoException {
        try {

            Optional<Carrito> carritoBuscado = carritoRepo.findByIdUsuario( new ObjectId(idUsuario));

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