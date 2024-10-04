package co.edu.uniquindio.uniEventos.repositorios;

import co.edu.uniquindio.uniEventos.modelo.documentos.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepo extends MongoRepository<Orden,String> {
}
