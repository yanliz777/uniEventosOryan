package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearCarritoDTO;
import co.edu.uniquindio.uniEventos.dto.DetalleCarritoDTO;
import co.edu.uniquindio.uniEventos.excepciones.CarritoNoCreadoException;
import co.edu.uniquindio.uniEventos.modelo.documentos.Carrito;
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

        ObjectId objectUsuario = new ObjectId("670229b4c4c96c516d6d0e02");

        String mensaje = carritoServicio.crearCarrito(String.valueOf(objectUsuario));
        Assertions.assertEquals( "Carrito creado exitosamente.", mensaje);
    }

    @Test
    public void agregarItemTest() throws Exception {

        ObjectId idCarrito = new ObjectId("670229b4c4c96c516d6d0e02");

        DetalleCarritoDTO detalleCarritoDTO = new DetalleCarritoDTO(
                2,
                "General",
                new ObjectId("670229b4c4c96c516d6d0e03")
        );

        carritoServicio.agregarItem(idCarrito.toString(), detalleCarritoDTO);

        Carrito carrito = carritoServicio.obtenerCarritoUsuario(idCarrito.toString());
        boolean itemEncontrado = carrito.getItems().stream()
                .anyMatch(item -> item.getIdEvento().toString().equals(detalleCarritoDTO.idEvento().toString()));

        Assertions.assertTrue(itemEncontrado, "El ítem fue agregado correctamente.");
    }

    @Test
    public void eliminarItemTest() throws Exception {
        ObjectId idCarrito = new ObjectId("670229b4c4c96c516d6d0e02");
        ObjectId idEvento = new ObjectId("670229b4c4c96c516d6d0e03");

        String mensaje = carritoServicio.eliminarItem(idCarrito.toString(), idEvento.toString());

        Assertions.assertEquals("Item eliminado correctamente", mensaje);

        Carrito carrito = carritoServicio.obtenerCarritoUsuario(idCarrito.toString());
        boolean itemEncontrado = carrito.getItems().stream()
                .anyMatch(item -> item.getIdEvento().toString().equals(idEvento.toString()));

        Assertions.assertFalse(itemEncontrado, "El ítem fue eliminado correctamente.");
    }

    @Test
    public void editarItemTest() throws Exception {
        ObjectId idCarrito = new ObjectId("670229b4c4c96c516d6d0e02");
        ObjectId idEvento = new ObjectId("670229b4c4c96c516d6d0e03");

        DetalleCarritoDTO detalleActualizado = new DetalleCarritoDTO(

                5,
                "VIP",
                idEvento
        );

        String mensaje = carritoServicio.editarItem(idCarrito.toString(), idEvento.toString(), detalleActualizado);

        Assertions.assertEquals("Item actualizado correctamente", mensaje);

        Carrito carrito = carritoServicio.obtenerCarritoUsuario(idCarrito.toString());
        DetalleCarrito itemActualizado = carrito.getItems().stream()
                .filter(item -> item.getIdEvento().toString().equals(idEvento.toString()))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(itemActualizado, "El ítem fue encontrado.");
        Assertions.assertEquals(5, itemActualizado.getCantidad(), "La cantidad fue actualizada correctamente.");
        Assertions.assertEquals("VIP", itemActualizado.getNombreLocalidad(), "La localidad fue actualizada correctamente.");
    }


}
