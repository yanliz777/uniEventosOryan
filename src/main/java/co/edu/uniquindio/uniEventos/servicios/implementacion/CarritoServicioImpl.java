package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.repositorios.CarritoRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CarritoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}