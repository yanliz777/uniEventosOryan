package co.edu.uniquindio.uniEventos.repositorios;

import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepo extends MongoRepository<Cuenta,String> {
    //consulta para verificar un emial:
    @Query("{email: ?0 }")
    Optional<Cuenta> buscaEmail(String email);

    //consulta para verificar la cedula de un Usuario:
    @Query("{usuario.cedula: ?0 }")
    Optional<Cuenta> buscaCedula(String cedula);

    //consulta para verificar el id de una cuenta:
    @Query("{id: ?0}")
    Optional<Cuenta> findById(String id);

    @Query("{email:  ?0, password:  ?1}")
    Optional<Cuenta> validarDatosAutenticacion(@NotBlank @Email String correo, @NotBlank String password);
}

