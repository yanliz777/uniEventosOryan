package co.edu.uniquindio.uniEventos.servicios.interfaces;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.*;

import java.util.List;

/*
Aqí van los métodos que debemos programar. Todos estos métodos deberían notificar
al usuario en caso de que el usuario ingrese datos que no corresponde. Por lo tanto, debemos de
incluir las excepciones.
DTO tiene infromación es especifica. Los campos necesarios que se necesitan en x contxto
 */
public interface CuentaServicio {
    String activarCuenta(String email, String codigoValidacion) throws CuentaNoActivadaException;

    String crearCuenta(CrearCuentaDTO cuenta) throws CuentaNoCreadaException;

    String editarCuenta(EditarClienteDTO cuenta) throws CuentaNoEditadaException;

    String eliminarCuenta(String id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception;

    String enviarCodigoRecuperacionPassword(String correo) throws CodigoValidacionNoEnviadoException;

    String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws PasswordNoEditadaException;//necesito codigo, idUsuario y password nuevo

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws SesionNoIniciadaException;

    List<ItemCuentaDTO> listarCuentas();

}

