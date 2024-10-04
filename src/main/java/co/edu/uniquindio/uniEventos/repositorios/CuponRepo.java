package co.edu.uniquindio.uniEventos.repositorios;


import co.edu.uniquindio.uniEventos.modelo.documentos.Cupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponRepo extends MongoRepository<Cupon,String> {
}
