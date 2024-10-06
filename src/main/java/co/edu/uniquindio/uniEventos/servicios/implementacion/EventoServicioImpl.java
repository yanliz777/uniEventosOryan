package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEditadoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEliminadoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoEncontradoException;
import co.edu.uniquindio.uniEventos.excepciones.EventoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import co.edu.uniquindio.uniEventos.repositorios.EventoRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServicioImpl implements EventoServicio {

    private final EventoRepo eventoRepo;

    @Override
    public String crearEvento(CrearEventoDTO crearEventoDTO) throws EventoNoCreadoException {

        if(crearEventoDTO.fechaEvento().isBefore(LocalDateTime.now())){
            throw new EventoNoCreadoException("La fecha ingresada para el evento debe ser mayor a la fecha actual");
        }

        if( existeEvento(crearEventoDTO.fechaEvento(), crearEventoDTO.nombre(), crearEventoDTO.ciudad())){

            throw new EventoNoCreadoException("Ya existe un evento registrado con el nombre " +
                    crearEventoDTO.nombre() + " para la fecha " + crearEventoDTO.fechaEvento());

        }

        Evento nuevoEvento = new Evento();
        nuevoEvento.setCiudad(crearEventoDTO.ciudad());
        nuevoEvento.setImagenPortada(crearEventoDTO.imagenImportada());
        nuevoEvento.setImagenLocalidades(crearEventoDTO.imagenLocalidades());
        nuevoEvento.setNombre(crearEventoDTO.nombre());
        nuevoEvento.setDescripcion(crearEventoDTO.descripcion());
        nuevoEvento.setDireccion(crearEventoDTO.direccion());
        nuevoEvento.setTipo(crearEventoDTO.tipoEvento());
        nuevoEvento.setFechaEvento(crearEventoDTO.fechaEvento());
        nuevoEvento.setLocalidades(crearEventoDTO.localidades());
        nuevoEvento.setEstado(EstadoEvento.ACTIVO);

        eventoRepo.save(nuevoEvento);

        return "El evento ha sido creado con éxito.";
    }

    @Override
    public String editarEvento(EditarEventoDTO editarEventoDTO) throws EventoNoEditadoException {

        Evento eventoModificado = obtenerEvento(editarEventoDTO.id());

        if(editarEventoDTO.fechaEvento().isBefore(LocalDateTime.now())){
            throw new EventoNoEditadoException("La nueva fecha ingresada para el evento debe ser mayor a la fecha actual");
        }

        eventoModificado.setImagenPortada(editarEventoDTO.imagenPortada());
        eventoModificado.setImagenLocalidades(editarEventoDTO.imagenLocalidades());
        eventoModificado.setDescripcion(editarEventoDTO.descripcion());
        eventoModificado.setEstado(editarEventoDTO.estado());
        eventoModificado.setDireccion(editarEventoDTO.direccion());
        eventoModificado.setCiudad(editarEventoDTO.ciudad());
        eventoModificado.setFechaEvento(editarEventoDTO.fechaEvento());
        eventoModificado.setNombre(editarEventoDTO.nombre());

        eventoRepo.save(eventoModificado);
        return eventoModificado.getId();
    }

    @Override
    public String eliminarEvento(String id) throws EventoNoEliminadoException {

        Evento evento = obtenerEvento(id);

        evento.setEstado(EstadoEvento.INACTIVO);

        eventoRepo.save(evento);

        return "El evento ha sido eliminado.";
    }

    @Override
    public InformacionEventoDTO obtenerInformacionEvento(String id) throws EventoNoEncontradoException {

        Evento evento = obtenerEvento(id);

        return new InformacionEventoDTO(
                id,
                evento.getImagenPortada(),
                evento.getDireccion(),
                evento.getCiudad(),
                evento.getFechaEvento(),
                evento.getTipo(),
                evento.getEstado(),
                evento.getDescripcion()
        );
    }

    @Override
    public List<ItemEventoDTO> listarEventos() throws EventoNoEncontradoException {

        List<Evento> eventos = eventoRepo.findAll();

        return eventos.stream()
                .map(evento -> new ItemEventoDTO(

                        evento.getImagenPortada(),
                        evento.getNombre(),
                        evento.getFechaEvento(),
                        evento.getCiudad()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemEventoDTO> filtrarEventos(FiltroEventoDTO filtroEventoDTO) throws EventoNoEncontradoException {

        List<Evento> eventos = eventoRepo.filtrarEventos(filtroEventoDTO.nombre(), filtroEventoDTO.tipo(), filtroEventoDTO.ciudad());

        return eventos.stream()
                .map(evento -> new ItemEventoDTO(
                        evento.getImagenPortada(),
                        evento.getNombre(),
                        evento.getFechaEvento(),
                        evento.getCiudad()))
                .collect(Collectors.toList());
    }

    @Override
    public Evento obtenerEvento(String id) throws EventoNoEncontradoException {


        Optional<Evento> eventoOptional = eventoRepo.findById(id);

        if(eventoOptional.isEmpty()){
            throw new EventoNoEncontradoException("No existe un evento registrado con el id " + id + ".");
        }

        return eventoOptional.get();

    }

    private boolean existeEvento(LocalDateTime fechaEvento, String nombre, String ciudad) {

        return eventoRepo.buscarEvento(nombre, fechaEvento, ciudad).isPresent();

    }
}

