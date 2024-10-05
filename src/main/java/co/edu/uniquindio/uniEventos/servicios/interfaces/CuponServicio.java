package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.CrearCuponDTO;
import co.edu.uniquindio.uniEventos.dto.EditarCuponDTO;
import co.edu.uniquindio.uniEventos.dto.InformacionCuponDTO;
import co.edu.uniquindio.uniEventos.dto.ItemCuponDTO;
import co.edu.uniquindio.uniEventos.excepciones.*;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cupon;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CuponServicio {

    String crearCupon(CrearCuponDTO cupon) throws CuponNoCreadoException;
    Cupon editarCupon(EditarCuponDTO cupon) throws CuponNoActualizadoException, CuponNoEncontradoException;
    String eliminarCupon(String id) throws CuponNoEliminadoException;
    InformacionCuponDTO obtenerInformacionCupon(String id) throws CuponNoEncontradoException;
    String enviarCodigoCuponPersonal(String correo) throws Exception;
    String enviarCodigoCuponATodos() throws Exception;
    List<ItemCuponDTO> listarCupones() throws CuponesNoEncontradosException;
    List<ItemCuponDTO> listarCuponesDisponibles() throws CuponesNoEncontradosException;
    List<ItemCuponDTO> listarCuponesPorNombre(String nombre) throws CuponesNoEncontradosException;
    List<ItemCuponDTO> listarCuponesPorDescuento(float descuento) throws CuponesNoEncontradosException;
    List<ItemCuponDTO> listarCuponesPorFechaVencimiento(LocalDateTime fechaVencimiento) throws CuponesNoEncontradosException;
    List<ItemCuponDTO> listarCuponesPorTipo(TipoCupon tipoCupon) throws CuponesNoEncontradosException;

}

