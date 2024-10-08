package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.PasswordNoEditadaException;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuentaServicio;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacionControlador {

    /*
     Los métodos creados bajo en endpoint "/api/auth" deberían ser accedidos por cualquier
     persona. Puede ubicar aquí los servicios de activar cuenta y recuperar contraseña
    */

    private final CuentaServicio cuentaServicio;

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
        TokenDTO token = cuentaServicio.iniciarSesion(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }

    @PostMapping("/crear-cuenta")
    public ResponseEntity<MensajeDTO<String>> crearCuenta(@Valid @RequestBody CrearCuentaDTO cuenta) throws Exception{
        cuentaServicio.crearCuenta(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta creada exitosamente"));
    }

}
