package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CarritoServicio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CarritoServicioTest {

    @Autowired
    private CarritoServicio carritoServicio;
    @Test
    public void crearCarritoTest() throws CarritoNoCreadoException {

        List<DetalleCarrito> items = new ArrayList<>();
        ObjectId objectUsuario = new ObjectId("670229b4c4c96c516d6d0e02");
        ObjectId objectEvento = new ObjectId("6703256265acee27386a5926");
        DetalleCarrito detalle = new DetalleCarrito(objectEvento, 4, "VIP");
        items.add(detalle);
        CrearCarritoDTO carritoDTO = new CrearCarritoDTO(
                objectUsuario,
                LocalDateTime.of(2024, 9, 14, 15, 30)
        );

        String mensaje = carritoServicio.crearCarrito(carritoDTO);
        Assertions.assertEquals( "Carrito creado exitosamente.", mensaje);
    }
}
