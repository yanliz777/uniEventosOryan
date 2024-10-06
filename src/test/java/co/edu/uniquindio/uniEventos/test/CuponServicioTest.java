package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearCuponDTO;
import co.edu.uniquindio.uniEventos.dto.EditarCuponDTO;
import co.edu.uniquindio.uniEventos.dto.ItemCuponDTO;
import co.edu.uniquindio.uniEventos.excepciones.*;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cupon;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;
import co.edu.uniquindio.uniEventos.repositorios.CuponRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuponServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CuponServicioTest {

    @Autowired
    private CuponServicio cuponServicio;

    @Autowired
    private CuponRepo cuponRepo;

    @Test
    public void crearCuponTest() throws CuponNoCreadoException {

        CrearCuponDTO registroCuponDTO =  new CrearCuponDTO(
                "AMOR Y AMISTAD",
                20,
                LocalDateTime.of(2023, 9, 14, 15, 30, 0),
                TipoCupon.MULTIPLE
        );

        String codigo = cuponServicio.crearCupon(registroCuponDTO);
        Assertions.assertNotNull(codigo);
    }

    @Test
    public void actualizarCuponTest() throws CuponNoActualizadoException, CuponNoEncontradoException {

        EditarCuponDTO actualizarCuponDTO = new EditarCuponDTO(
                "66e5bc339693cf64dd977903", //se debe validar de acuerdo al id generado en el test1
                "AMOR Y AMISTAD2",
                20,
                "BERVWK7GWE4ELL26",
                LocalDateTime.of(2023, 9, 14, 15, 30, 0),
                TipoCupon.MULTIPLE,
                EstadoCupon.DISPONIBLE
        );

        Cupon cuponEditado = cuponServicio.editarCupon(actualizarCuponDTO);

        Assertions.assertEquals("AMOR Y AMISTAD2", cuponEditado.getNombre());
    }

    @Test
    public void eliminarCuponTest() throws CuponNoEliminadoException {
        cuponServicio.eliminarCupon( "66e5c4b6e1ef425df559016d" );

        Optional<Cupon> cupon = cuponRepo.findById( "66e5c4b6e1ef425df559016d" );

        Assertions.assertTrue(cupon.isPresent(), "El cupón cambio  su estado a eliminado");
        Assertions.assertEquals(EstadoCupon.ELIMINADO, cupon.get().getEstado());
    }

    @Test
    public void listarTodosLosCuponesTest() throws CuponesNoEncontradosException {

        List<ItemCuponDTO> listaTodosCupones = cuponServicio.listarCupones();
        listaTodosCupones.forEach(System.out::println);

        Assertions.assertEquals(2, listaTodosCupones.size() );
    }

    @Test
    public void listarCuponesDisponiblesTest() throws CuponesNoEncontradosException {

        List<ItemCuponDTO> listaCuponesDisponibles = cuponServicio.listarCuponesDisponibles();
        listaCuponesDisponibles.forEach(System.out::println);

        Assertions.assertEquals(1, listaCuponesDisponibles.size() );
    }

    @Test
    public void listarCuponesPorNombreTest() throws CuponesNoEncontradosException {

        List<ItemCuponDTO> listarCuponesPorNombre = cuponServicio.listarCuponesPorNombre("AMISTAD2");
        listarCuponesPorNombre.forEach(System.out::println);

        Assertions.assertEquals(1, listarCuponesPorNombre.size() );
    }

    @Test
    public void listarCuponesPorNombreFallidoTest() {
        try {
            List<ItemCuponDTO> listarCuponesPorNombre = cuponServicio.listarCuponesPorNombre("NAVIDA");
            listarCuponesPorNombre.forEach(System.out::println);

            Assertions.assertEquals(0, listarCuponesPorNombre.size());
        } catch (CuponesNoEncontradosException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void listarCuponesPorDescuentoTest() throws CuponesNoEncontradosException {

        List<ItemCuponDTO> listarCuponesPorDescuento = cuponServicio.listarCuponesPorDescuento(20);
        listarCuponesPorDescuento.forEach(System.out::println);

        Assertions.assertEquals(1, listarCuponesPorDescuento.size() );
    }

    @Test
    public void listarCuponesPorDescuentoFallidoTest() {
        try {
            List<ItemCuponDTO> listarCuponesPorDescuento = cuponServicio.listarCuponesPorDescuento(15);
            listarCuponesPorDescuento.forEach(System.out::println);

            Assertions.assertEquals(0, listarCuponesPorDescuento.size() );
        } catch (CuponesNoEncontradosException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void listarCuponesPorFechaVencimientoTest() throws CuponesNoEncontradosException {

        //TODO implmentación del test por fecha de vencimiento y estado
    }

    @Test
    public void listarCuponesPorTipoCuponTest() throws CuponesNoEncontradosException {

        List<ItemCuponDTO> listarCuponesPorTipo = cuponServicio.listarCuponesPorTipo( TipoCupon.MULTIPLE );
        listarCuponesPorTipo.forEach(System.out::println);

        Assertions.assertEquals(1, listarCuponesPorTipo.size() );
    }

    @Test
    public void listarCuponesPorTipoCuponFallidoTest() {
        try {
            List<ItemCuponDTO> listarCuponesPorTipo = cuponServicio.listarCuponesPorTipo( TipoCupon.UNICO );
            listarCuponesPorTipo.forEach(System.out::println);

            Assertions.assertEquals(7, listarCuponesPorTipo.size() );
        } catch (CuponesNoEncontradosException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void redimirCuponTest() {
        String codigoCupon = "CUPON2024";
        Assertions.assertDoesNotThrow(() -> {
            boolean resultado = cuponServicio.redimirCupon(codigoCupon);
            Assertions.assertTrue(resultado);
        });
    }

}

