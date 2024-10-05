package co.edu.uniquindio.uniEventos.repositorios;


import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepo extends MongoRepository<Evento,String> {
    @Query("{nombre : nombreEvento, fechaEvento: fechaEvento, ciudad :  ciudad}")
    Optional<Evento> buscarEvento(String nombreEvento, LocalDateTime fechaEvento, String ciudad);

    @Query("{nombre : nombreEvento, tipo: tipo, ciudad :  ciudad}")
    List<Evento> filtrarEventos(String nombreEvento, TipoEvento tipo, String ciudad);
}
