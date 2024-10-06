package co.edu.uniquindio.uniEventos.test;

import co.edu.uniquindio.uniEventos.configuracion.JWTUtils;
import co.edu.uniquindio.uniEventos.dto.CrearCuentaDTO;
import co.edu.uniquindio.uniEventos.dto.LoginDTO;
import co.edu.uniquindio.uniEventos.dto.TokenDTO;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CuentaNoCreadaException;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.SesionNoIniciadaException;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuentaServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CuentaServicioTest {

    @Autowired
    private CuentaServicio cuentaServicio;
    @Autowired
    private JWTUtils jwtUtils;

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

    /*
    Test para capturar el Token
     */
    @Test
    public void iniciarSesionTest() throws SesionNoIniciadaException {
        // Datos de la cuenta que ya están en MongoDB
        String email = "fc9294011@gmail.com"; // Asegúrate de que este correo existe en la base de datos
        String password = "12345678"; // Debe ser la contraseña correcta

        // Crear el objeto LoginDTO con los datos de inicio de sesión
        LoginDTO loginDTO = new LoginDTO(email, password);

        // Iniciar sesión
        TokenDTO token = cuentaServicio.iniciarSesion(loginDTO);

        // Comprobar que se devuelve un token y que no está vacío
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(token.token());
        Assertions.assertFalse(token.token().isEmpty());

        // Imprimir el token en la consola
        System.out.println("Token generado: " + token.token());
    }

}
