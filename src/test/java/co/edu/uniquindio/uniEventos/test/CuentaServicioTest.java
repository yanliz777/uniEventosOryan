package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.dto.CrearCuentaDTO;
import co.edu.uniquindio.uniEventos.dto.ItemCuentaDTO;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CuentaNoCreadaException;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CuentaNoEncontradaException;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuentaServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;

import java.util.List;

@SpringBootTest
public class CuentaServicioTest {

    @Autowired
    private CuentaServicio cuentaServicio;

    @Test
    public void crearCuentaTest() throws CuentaNoCreadaException {
        CrearCuentaDTO registrarCuenta = new CrearCuentaDTO(
                "1094958632",
                "Yan",
                "3152684521",
                "Tebaida",
                "fc9294011@gmail.com",
                "12345678"
        );

        String cuentaCreada = cuentaServicio.crearCuenta(registrarCuenta);
        Assertions.assertEquals("Su cuenta se ha creado correctamente", cuentaCreada);
    }

    @Test
    public void listarCuentasTest() throws CuentaNoEncontradaException {
        List<ItemCuentaDTO> mostrarCuentas = cuentaServicio.listarCuentas();

        Assertions.assertNotNull(mostrarCuentas);

        mostrarCuentas.forEach(System.out::println);
    }
}
