package co.edu.uniquindio.uniEventos.repositorios;

import co.edu.uniquindio.uniEventos.modelo.documentos.Orden;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepo extends MongoRepository<Orden,String> {

    @Query("{ 'idCliente' : ?0 }")
    List<Orden> findByIdCliente(ObjectId idCliente);

    @Query("{ 'idCliente' : ?0 }")
    List<Orden> findAllByIdCliente(ObjectId idCliente);
}
