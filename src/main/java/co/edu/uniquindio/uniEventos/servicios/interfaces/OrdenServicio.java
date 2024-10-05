package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.CrearOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.HistorialOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.ItemOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.ObtenerOrdenDTO;
import co.edu.uniquindio.uniEventos.excepciones.CuponNoEncontradoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEncontradoException;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CuentaNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.HistorialOrdenesVacionException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoCancelableException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenYaCanceladaException;

import java.util.List;
import java.util.Map;
import com.mercadopago.resources.preference.Preference;

public interface OrdenServicio {
   
    String crearOrden(CrearOrdenDTO crearOrdenDTO) throws CuponNoEncontradoException, EventoNoEncontradoException, CuentaNoEncontradaException;

    ObtenerOrdenDTO obtenerOrdenPorId(String idOrden) throws OrdenNoEncontradaException;

    String cancelarOrden(String idOrden) throws OrdenNoEncontradaException, OrdenYaCanceladaException, OrdenNoCancelableException;

    Preference realizarPago(String idOrden) throws Exception;

    void recibirNotificacionMercadoPago(Map<String, Object> request);

    public List<ItemOrdenDTO> historialOrdenes(String idUsuario)throws CuentaNoEncontradaException, HistorialOrdenesVacionException;
}
