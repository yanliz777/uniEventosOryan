package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.DetalleOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.PagoDTO;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import co.edu.uniquindio.uniEventos.modelo.documentos.Orden;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import co.edu.uniquindio.uniEventos.repositorios.CarritoRepo;
import co.edu.uniquindio.uniEventos.repositorios.CuentaRepo;
import co.edu.uniquindio.uniEventos.repositorios.EventoRepo;
import co.edu.uniquindio.uniEventos.repositorios.OrdenRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.OrdenServicio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrdenServicioTest {

    @Autowired
    private OrdenServicio ordenServicio;

    @Autowired
    private OrdenRepo ordenRepo;

    @Autowired
    private CarritoRepo carritoRepo;

    @Autowired
    private CuentaRepo cuentaRepo;

    @Autowired
    private EventoRepo eventoRepo;

    @Test
    public void crearOrdenTest() {
        // Simulación de los datos necesarios para crear la orden

        // 1. Crear el cliente (cuenta)
        Cuenta cuenta = new Cuenta();
        cuenta.setId("cliente123");
        cuenta.setEmail("cliente@correo.com");
        cuenta.setEstado(EstadoCuenta.ACTIVO);
        cuentaRepo.save(cuenta);

        // 2. Crear el evento y localidad
        Localidad localidad = new Localidad();
        localidad.setNombre("VIP");
        localidad.setCapacidadMaxima(50); // Suficiente capacidad
        localidad.setPrecio(100.0f);

        Evento evento = new Evento();
        evento.setId("evento123");
        evento.setNombre("Concierto de Prueba");
        evento.setFechaEvento(LocalDateTime.now().plusDays(5));
        evento.setLocalidades(List.of(localidad));
        eventoRepo.save(evento);

        // 3. Se Crea el carrito para el cliente
        DetalleCarrito detalleCarrito = new DetalleCarrito(new ObjectId(evento.getId()), 2, "VIP");
        Carrito carrito = new Carrito();
        carrito.setId("carrito123");
        carrito.setIdUsuario(new ObjectId(cuenta.getId()));
        carrito.setFecha(LocalDateTime.now());
        carrito.setItems(List.of(detalleCarrito));
        carritoRepo.save(carrito);

        // 4. Simular la creación de la orden
        CrearOrdenDTO crearOrdenDTO = new CrearOrdenDTO(
                "orden123",
                "COP",
                cuenta.getId(),
                LocalDateTime.now(),
                "pasarela123",
                null,  // No se utiliza cupón en esta prueba
                List.of(new DetalleOrdenDTO(evento.getId(), "VIP", 2, 100.0f)),
                200.0f,  // Total sin descuento
                new PagoDTO("COP", "aut123", "credit_card", "aprobado", 200.0f, "aprobado")
        );

        // Llamar al método crearOrden
        String resultado = ordenServicio.crearOrden(crearOrdenDTO);

        // Verificar que el resultado no sea nulo y que la orden se haya creado correctamente
        Assertions.assertNotNull(resultado);
        Assertions.assertTrue(resultado.contains("Orden creada con éxito"));

        // Verificar que la orden se haya guardado en la base de datos
        Optional<Orden> ordenGuardada = ordenRepo.findById("orden123");
        Assertions.assertTrue(ordenGuardada.isPresent());
        Assertions.assertEquals(200.0f, ordenGuardada.get().getTotal());
    }


    @Test
    public void crearOrdenConCarritoInexistenteTest() {
        // Simular la creación de una orden con un carrito inexistente
        CrearOrdenDTO crearOrdenDTO = new CrearOrdenDTO(
                "orden123",
                "COP",
                "cliente123",
                LocalDateTime.now(),
                "pasarela123",// ID de carrito que no existe
                null,
                List.of(new DetalleOrdenDTO("evento123", "VIP", 2, 100.0f)),
                200.0f,
                new PagoDTO("COP", "aut123", "credit_card", "aprobado", 200.0f, "aprobado")
        );

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            ordenServicio.crearOrden(crearOrdenDTO);
        });

        // Verificar el mensaje de error
        Assertions.assertEquals("Carrito no encontrado", thrown.getMessage());
    }

    @Test
    public void crearOrdenConEventoSinCapacidadTest() {
        // Simular un evento sin suficiente capacidad en la localidad
        Localidad localidad = new Localidad();
        localidad.setNombre("General");
        localidad.setCapacidadMaxima(1);  // Capacidad insuficiente
        localidad.setPrecio(50.0f);

        Evento evento = new Evento();
        evento.setId("eventoConCapacidadBaja");
        evento.setNombre("Evento con Baja Capacidad");
        evento.setFechaEvento(LocalDateTime.now().plusDays(5));
        evento.setLocalidades(List.of(localidad));
        eventoRepo.save(evento);

        DetalleCarrito detalleCarrito = new DetalleCarrito(new ObjectId(evento.getId()), 5, "General");
        Carrito carrito = new Carrito();
        carrito.setId("carrito1234");
        carrito.setIdUsuario(new ObjectId("cliente123"));
        carrito.setFecha(LocalDateTime.now());
        carrito.setItems(List.of(detalleCarrito));
        carritoRepo.save(carrito);

        CrearOrdenDTO crearOrdenDTO = new CrearOrdenDTO(
                "orden123",
                "COP",
                "cliente123",
                LocalDateTime.now(),
                "pasarela123",
                null,
                List.of(new DetalleOrdenDTO(evento.getId(), "General", 5, 50.0f)),
                250.0f,
                new PagoDTO("COP", "aut123", "credit_card", "aprobado", 250.0f, "aprobado")
        );

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            ordenServicio.crearOrden(crearOrdenDTO);
        });

        Assertions.assertEquals("No hay suficientes entradas disponibles en esta localidad", thrown.getMessage());
    }
}
