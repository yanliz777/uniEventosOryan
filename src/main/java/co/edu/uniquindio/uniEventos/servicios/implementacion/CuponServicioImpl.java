package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.CrearCuponDTO;
import co.edu.uniquindio.uniEventos.dto.EditarCuponDTO;
import co.edu.uniquindio.uniEventos.dto.InformacionCuponDTO;
import co.edu.uniquindio.uniEventos.dto.ItemCuponDTO;
import co.edu.uniquindio.uniEventos.excepciones.*;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cupon;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;
import co.edu.uniquindio.uniEventos.repositorios.CuponRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuponServicio;
import co.edu.uniquindio.uniEventos.utils.GenerarCodigo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuponServicioImpl implements CuponServicio {

    private final CuponRepo cuponRepo;


    private final GenerarCodigo generarCodigo;

    @Override
    public String crearCupon(CrearCuponDTO cupon) throws CuponNoCreadoException {

        try {
            Cupon nuevoCupon = new Cupon();
            nuevoCupon.setNombre(cupon.nombre());
            nuevoCupon.setDescuento(cupon.descuento());
            nuevoCupon.setCodigo( generarCodigo.randomCodigoCupones() );
            nuevoCupon.setFechaVencimiento(cupon.fechaVencimiento());
            nuevoCupon.setTipo(cupon.tipoCupon());
            nuevoCupon.setEstado( EstadoCupon.DISPONIBLE );

            Cupon cuponGuardado = cuponRepo.save(nuevoCupon);

            if (cuponGuardado.getId() == null) {
                throw new CuponNoCreadoException("El cupón no pudo ser creado.");
            }

            return "Cupón creado correctamente.";

        } catch (Exception e) {
            throw new CuponNoCreadoException("Error al crear el cupón: " + e.getMessage());
        }
    }

    @Override
    public Cupon editarCupon(EditarCuponDTO cupon) throws CuponNoActualizadoException {

        try {
            Cupon cuponActualizar = obtenerCupon( cupon.id() );

            cuponActualizar.setNombre( cupon.nombre() );
            cuponActualizar.setDescuento( cupon.descuento() );
            cuponActualizar.setCodigo( cupon.codigo() );
            cuponActualizar.setTipo( cupon.tipoCupon() );

            return cuponRepo.save(cuponActualizar);

        } catch (Exception e){
            throw new CuponNoActualizadoException("El cupón no pudo ser actualizado " + e.getMessage() );
        }
    }

    @Override
    public String eliminarCupon(String id) throws CuponNoEliminadoException {

        try {
            Cupon cuponEliminado = obtenerCupon( id );

            cuponEliminado.setEstado( EstadoCupon.ELIMINADO );
            cuponRepo.save(cuponEliminado);

            return "El cupon fue eliminado correctamente.";
        } catch (Exception e){
            throw new CuponNoEliminadoException("El cupón no pudo ser eliminado " + e.getMessage() );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionCuponDTO obtenerInformacionCupon(String id) throws CuponNoEncontradoException {
        try {
            Cupon cupon = obtenerCupon(id);
            return new InformacionCuponDTO(
                    cupon.getNombre(),
                    cupon.getDescuento(),
                    cupon.getCodigo(),
                    cupon.getFechaVencimiento(),
                    cupon.getTipo(),
                    cupon.getEstado()
            );
        } catch (CuponNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la información del cupón", e);
        }
    }

    @Override
    public String enviarCodigoCuponPersonal(String correo) throws Exception {
        // TODO enviar correo de descuento por creación de cuenta en UniEventos
        return null;
    }

    @Override
    public String enviarCodigoCuponATodos() throws Exception {
        // TODO implementar en cuenta que permita obtener todos los correos activos de la base de datos
        return null;
    }

    @Override
    public boolean redimirCupon(String codigo) throws Exception {
        Cupon cupon = obtenerCupon(codigo);

        if (cupon.getFechaVencimiento().isBefore(LocalDateTime.now())) {
            throw new Exception("El cupón ha vencido.");
        }

        cupon.setEstado(EstadoCupon.ELIMINADO);
        cuponRepo.save(cupon);
        return true;
    }

    @Override
    public List<ItemCuponDTO> listarCupones() throws CuponesNoEncontradosException{

        try {
            List<Cupon> cupones = cuponRepo.findAll();

            List<ItemCuponDTO> items = new ArrayList<>();
            for( Cupon cupon : cupones ){
                items.add( new ItemCuponDTO(
                        cupon.getId(),
                        cupon.getNombre(),
                        cupon.getDescuento(),
                        cupon.getCodigo(),
                        cupon.getFechaVencimiento()
                ));
            }
            return items;
        }catch (Exception e){
            throw  new CuponesNoEncontradosException("No se encontraron cupones creados " + e);
        }
    }

    @Override
    public List<ItemCuponDTO> listarCuponesDisponibles() throws CuponesNoEncontradosException {

        Optional<List<Cupon>> cuponesDisponibles = cuponRepo.findAllByEstado( EstadoCupon.DISPONIBLE );

        return obtenerItemsCuponesDTO(cuponesDisponibles);
    }

    @Override
    public List<ItemCuponDTO> listarCuponesPorNombre(String nombre) throws CuponesNoEncontradosException {

        Optional<List<Cupon>> cuponesDisponibles = cuponRepo.findAllByNombreRegexAndEstado( nombre, EstadoCupon.DISPONIBLE );

        return obtenerItemsCuponesDTO(cuponesDisponibles);
    }

    @Override
    public List<ItemCuponDTO> listarCuponesPorDescuento(float descuento) throws CuponesNoEncontradosException {

        Optional<List<Cupon>> cuponesDisponibles = cuponRepo.findAllByDescuentoAndEstado( descuento, EstadoCupon.DISPONIBLE );

        return obtenerItemsCuponesDTO(cuponesDisponibles);
    }

    @Override
    public List<ItemCuponDTO> listarCuponesPorFechaVencimiento(LocalDateTime fechaVencimiento) throws CuponesNoEncontradosException {

        Optional<List<Cupon>> cuponesDisponibles = cuponRepo.findAllByFechaVencimientoAndEstado( fechaVencimiento, EstadoCupon.DISPONIBLE );

        return obtenerItemsCuponesDTO(cuponesDisponibles);
    }

    @Override
    public List<ItemCuponDTO> listarCuponesPorTipo(TipoCupon tipoCupon) throws CuponesNoEncontradosException {

        Optional<List<Cupon>> cuponesDisponibles = cuponRepo.findAllByEstadoAndTipo( EstadoCupon.DISPONIBLE, tipoCupon );

        return obtenerItemsCuponesDTO(cuponesDisponibles);
    }

    public Cupon obtenerCupon( String id ) throws CuponNoEncontradoException {
        Optional<Cupon> cupon = cuponRepo.findById( id );
        if( cupon.isEmpty() ){
            throw new CuponNoEncontradoException("El cupón con el id "+ id + " no fue encontrado");
        }

        return cupon.get();
    }

    private static List<ItemCuponDTO> obtenerItemsCuponesDTO(Optional<List<Cupon>> cuponesDisponibles) throws CuponesNoEncontradosException {
        if(cuponesDisponibles.isEmpty() || cuponesDisponibles.get().isEmpty()){
            throw new CuponesNoEncontradosException("No se encontraron cupones en estado Disponible con el filtro aplicado.");
        }

        List<Cupon> cupones = cuponesDisponibles.get();
        List<ItemCuponDTO> items = new ArrayList<>();
        for( Cupon cupon : cupones ){
            items.add( new ItemCuponDTO(
                    cupon.getId(),
                    cupon.getNombre(),
                    cupon.getDescuento(),
                    cupon.getCodigo(),
                    cupon.getFechaVencimiento()
            ));
        }
        return items;
    }
}

