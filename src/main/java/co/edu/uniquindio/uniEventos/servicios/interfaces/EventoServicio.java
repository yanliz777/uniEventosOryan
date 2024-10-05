package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;

import java.util.List;

public interface EventoServicio {

    Evento obtenerEvento(String string)throws Exception;
}
