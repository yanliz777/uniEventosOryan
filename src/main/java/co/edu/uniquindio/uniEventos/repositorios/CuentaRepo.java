package co.edu.uniquindio.uniEventos.repositorios;

import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepo extends MongoRepository<Cuenta,String> {
}
