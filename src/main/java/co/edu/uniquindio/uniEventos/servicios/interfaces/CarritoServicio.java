package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;

import java.util.List;

public interface CarritoServicio {
    String crearCarrito(CrearCarritoDTO crearCarritoDTO) throws CarritoNoCreadoException;

    String listaDetallesCarrito (List<DetalleCarrito> listaCarrito);

    String eliminarItem(String idCarrito, String idEvento) throws Exception;

    void agregarItem(String idCarrito, DetalleCarrito item) throws Exception;

    List<Carrito> findAll();
}
