package co.edu.uniquindio.uniEventos.repositorios;


import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepo extends MongoRepository<Evento,String> {
    @Query("{id: ?0}")
    Optional<Evento> findById(String id);
}
