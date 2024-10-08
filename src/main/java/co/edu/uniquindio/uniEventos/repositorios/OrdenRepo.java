package co.edu.uniquindio.uniEventos.repositorios;

import co.edu.uniquindio.uniEventos.modelo.documentos.Orden;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepo extends MongoRepository<Orden,String> {

    Optional<List<Orden>> findByCodigoCliente(String codigoCliente);

    @Query("{ 'idCliente' : ?0 }")
    Optional<List<Orden>> findAllByIdCliente(ObjectId idCliente);
}
