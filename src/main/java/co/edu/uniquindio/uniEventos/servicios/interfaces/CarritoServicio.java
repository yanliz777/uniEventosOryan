package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.dto.DetalleCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoEncontradoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;

import java.util.List;

public interface CarritoServicio {

    String crearCarrito(String idUsuario) throws CarritoNoCreadoException;

    String listaDetallesCarrito (List<DetalleCarrito> listaCarrito);

    String eliminarItem(String idCarrito, String idEvento) throws Exception;

    void agregarItem(String idCarrito, DetalleCarritoDTO item) throws Exception;

    String editarItem(String idCarrito, String idEvento, DetalleCarritoDTO detalleActualizado) throws Exception;

    Carrito obtenerCarritoUsuario(String idUsuario) throws CarritoNoEncontradoException;

    List<Carrito> findAll();
}
