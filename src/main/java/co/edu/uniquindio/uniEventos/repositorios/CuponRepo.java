package co.edu.uniquindio.uniEventos.repositorios;


import co.edu.uniquindio.uniEventos.modelo.documentos.Cupon;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuponRepo extends MongoRepository<Cupon,String> {

    Optional<List<Cupon>> findAllByEstado(EstadoCupon estado);
    Optional<List<Cupon>> findAllByNombreRegexAndEstado(String nombre, EstadoCupon estado);
    Optional<List<Cupon>> findAllByDescuentoAndEstado(float descuento, EstadoCupon estado);
    Optional<List<Cupon>> findAllByFechaVencimientoAndEstado(LocalDateTime fechaVencimiento, EstadoCupon estado);
    Optional<List<Cupon>> findAllByEstadoAndTipo(EstadoCupon estado, TipoCupon tipoCupon);

    Optional<Cupon> findByCodigo(String codigo);

    @Query("{estado : DISPONIBLE}")
    Optional<List<Cupon>> buscarCuponesActivos();

    @Query("{fechaVencimiento: { $lt: ?0 }}")
    Optional<List<Cupon>> buscarCuponesPorExpirarAntesDe(java.util.Date fecha);

    Optional<List<Cupon>> findCuponsByTipo(TipoCupon tipo);

}
