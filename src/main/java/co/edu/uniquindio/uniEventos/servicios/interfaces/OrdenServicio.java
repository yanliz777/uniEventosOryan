package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.CrearOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.ObtenerOrdenDTO;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoCancelableException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenYaCanceladaException;

public interface OrdenServicio {
    //editar no va aquí
    //eliminar tampoco
    String crearOrden(CrearOrdenDTO crearOrdenDTO);

    ObtenerOrdenDTO obtenerOrdenPorId(String idOrden);

    String cancelarOrden(String idOrden) throws OrdenNoEncontradaException, OrdenYaCanceladaException, OrdenNoCancelableException;
}
