package co.edu.uniquindio.uniEventos.repositorios;


import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepo extends MongoRepository<Evento,String> {
}
