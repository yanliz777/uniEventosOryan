package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.CrearEventoDTO;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import co.edu.uniquindio.uniEventos.repositorios.EventoRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoServicioImpl implements EventoServicio {

    private final EventoRepo eventoRepo;




    public Evento obtenerEvento(String id) throws Exception {

        Optional<Evento> eventoOptional = eventoRepo.findById(id);

        if(eventoOptional.isEmpty()){
            throw new Exception("No existe un evento registrado con el id " + id + ".");
        }

        return eventoOptional.get();

    }
}
