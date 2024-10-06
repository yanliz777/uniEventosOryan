package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;

public interface CarritoServicio {
    String crearCarrito(CrearCarritoDTO crearCarritoDTO) throws CarritoNoCreadoException;
}
