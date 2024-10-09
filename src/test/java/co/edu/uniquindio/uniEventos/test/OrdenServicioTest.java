package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.DetalleOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.ObtenerOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.PagoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CuponNoEncontradoException;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CuentaNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoCancelableException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenYaCanceladaException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.uniEventos.modelo.documentos.Evento;
import co.edu.uniquindio.uniEventos.modelo.documentos.Orden;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoOrden;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleOrden;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.vo.Pago;
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
    public void crearOrdenTest1() throws CuponNoEncontradoException, CuentaNoEncontradaException {
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
        Assertions.assertEquals(200.0f, ordenGuardada.get().getTotal());// Aquí se valida el total
    }


    @Test
    public void crearOrdenConCarritoInexistenteTest() {
        // Simular la creación de una orden con un carrito inexistente
        CrearOrdenDTO crearOrdenDTO = new CrearOrdenDTO(
                "COP",
                "cliente123",
                LocalDateTime.now(),
                "pasarela123",
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

    /*
    Explicación de la prueba:

    Creamos una orden simulada con un cliente, un pago, y un detalle de orden.
    Guardamos la orden en el repositorio de órdenes.
    Llamamos al método obtenerOrdenPorId y verificamos que la orden
    devuelta contenga los datos correctos.
     */
    @Test
    public void obtenerOrdenPorIdTest() throws OrdenNoEncontradaException {
        // Simular una orden guardada en la base de datos
        Orden orden = new Orden();
        orden.setId("orden123");
        orden.setCodigoCliente(new ObjectId("cliente123"));
        orden.setFecha(LocalDateTime.now());
        orden.setCodigoPasarela("pasarela123");
        orden.setTotal(200.0f);

        Pago pago = new Pago();
        pago.setMoneda("COP");
        pago.setCodigoAutorizacion("aut123");
        pago.setTipoPago("credit_card");
        pago.setDetalleEstado("aprobado");
        pago.setValorTransaccion(200.0f);
        pago.setEstado("aprobado");
        orden.setPago(pago);

        DetalleOrden detalle = new DetalleOrden();
        detalle.setCodigoEvento(new ObjectId("evento123"));
        detalle.setNombreLocalidad("VIP");
        detalle.setCantidad(2);
        detalle.setPrecio(100.0f);
        orden.setItems(List.of(detalle));

        ordenRepo.save(orden);

        // Llamar al metodo obtenerOrdenPorId
        ObtenerOrdenDTO ordenDTO = ordenServicio.obtenerOrden("orden123");

        // Verificar los datos de la orden
        Assertions.assertNotNull(ordenDTO);
        Assertions.assertEquals("orden123", ordenDTO.id());
        Assertions.assertEquals("cliente123", ordenDTO.idCliente());
        Assertions.assertEquals(200.0f, ordenDTO.total());
    }

    /*
    Explicación de la prueba:

    Simulamos una orden activa y un evento que ocurrirá en más de 2 días.
    Verificamos que la orden se cancela correctamente y que el
    estado de la orden se actualiza a CANCELADA.
    */
    @Test
    public void cancelarOrdenTest() throws OrdenYaCanceladaException, OrdenNoCancelableException, OrdenNoEncontradaException {
        // Simular una orden guardada en la base de datos
        Orden orden = new Orden();
        orden.setId("orden123");
        orden.setCodigoCliente(new ObjectId("cliente123"));
        orden.setFecha(LocalDateTime.now());
        orden.setEstado(EstadoOrden.ACTIVA);

        Pago pago = new Pago();
        pago.setMoneda("COP");
        pago.setCodigoAutorizacion("aut123");
        pago.setTipoPago("credit_card");
        pago.setDetalleEstado("aprobado");
        pago.setValorTransaccion(200.0f);
        pago.setEstado("aprobado");
        orden.setPago(pago);

        DetalleOrden detalle = new DetalleOrden();
        detalle.setCodigoEvento(new ObjectId("evento123"));
        detalle.setNombreLocalidad("VIP");
        detalle.setCantidad(2);
        detalle.setPrecio(100.0f);
        orden.setItems(List.of(detalle));

        ordenRepo.save(orden);

        // Simular el evento correspondiente a la orden
        Evento evento = new Evento();
        evento.setId("evento123");
        evento.setNombre("Concierto de Prueba");
        evento.setFechaEvento(LocalDateTime.now().plusDays(5));  // Evento con más de 2 días
        eventoRepo.save(evento);

        // Llamar al método cancelarOrden
        String resultado = ordenServicio.cancelarOrden("orden123");

        // Verificar que la orden se haya cancelado correctamente
        Assertions.assertNotNull(resultado);
        Assertions.assertTrue(resultado.contains("Orden cancelada con éxito"));

        // Verificar que el estado de la orden sea CANCELADA
        Optional<Orden> ordenCancelada = ordenRepo.findById("orden123");
        Assertions.assertTrue(ordenCancelada.isPresent());
        Assertions.assertEquals(EstadoOrden.CANCELADA, ordenCancelada.get().getEstado());
    }

    /*
    Explicación de la prueba:

    Simulamos un evento que ocurre en menos de 2 días y verificamos que
    no se permite cancelar la orden.
    Comprobamos que se lanza la excepción con el mensaje correcto.
     */
    @Test
    public void cancelarOrdenConEventoMuyProximoTest() {
        // Simular una orden con un evento que ocurre en menos de 2 días
        Evento evento = new Evento();
        evento.setId("eventoProximo");
        evento.setNombre("Concierto Proximo");
        evento.setFechaEvento(LocalDateTime.now().plusDays(1));  // Evento en menos de 2 días
        eventoRepo.save(evento);

        DetalleOrden detalle = new DetalleOrden();
        detalle.setCodigoEvento(new ObjectId("eventoProximo"));
        detalle.setNombreLocalidad("General");
        detalle.setCantidad(1);
        detalle.setPrecio(50.0f);

        Orden orden = new Orden();
        orden.setId("ordenProxima");
        orden.setCodigoCliente(new ObjectId("cliente123"));
        orden.setFecha(LocalDateTime.now());
        orden.setItems(List.of(detalle));
        orden.setEstado(EstadoOrden.ACTIVA);
        ordenRepo.save(orden);

        // Intentar cancelar la orden con el evento en menos de 2 días
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            ordenServicio.cancelarOrden("ordenProxima");
        });

        // Verificar que se arroje la excepción adecuada
        Assertions.assertEquals("No se puede cancelar la orden, faltan menos de dos días para el evento", thrown.getMessage());
    }

}

