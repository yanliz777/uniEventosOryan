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

    Optional<Cuenta> findByEmail(String email);

    @Query("{ 'usuario': { $ne: null }, 'usuario.cedula': ?0 }")
    Optional<Cuenta> findByCedula(String cedula);

    //@Query("{email:  ?0, password:  ?1}")
    //Optional<Cuenta> validarDatosAutenticacion(@NotBlank @Email String correo, @NotBlank String password);
}

