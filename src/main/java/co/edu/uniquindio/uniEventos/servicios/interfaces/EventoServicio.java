package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.*;

import co.edu.uniquindio.uniEventos.excepciones.EventoNoCreadoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEditadoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEliminadoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEncontradoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;

import java.util.List;

public interface EventoServicio {

    String crearEvento(CrearEventoDTO crearEventoDTO) throws EventoNoCreadoException;

    String editarEvento(EditarEventoDTO editarEventoDTO) throws EventoNoEditadoException;

    String eliminarEvento(String id) throws EventoNoEliminadoException;

    InformacionEventoDTO obtenerInformacionEvento(String id) throws EventoNoEncontradoException;

    List<ItemEventoDTO> listarEventos() throws EventoNoEncontradoException;

    List<ItemEventoDTO> filtrarEventos(FiltroEventoDTO filtroEventoDTO) throws EventoNoEncontradoException;

    Evento obtenerEvento(String string) throws EventoNoEncontradoException;

}
