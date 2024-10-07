package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.CodigoValidacionNoEnviadoException;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.PasswordNoEditadaException;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuentaServicio;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*endpoint: @RequestMapping("/api/cuenta")
 Esto significa que cuando se pida la url http://localhost:8080/api/cuenta
 será el controlador REST que estamos programando quien responda ante las peticiones realizadas.
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cuenta")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    /*
   cada metodo del controlador es equivalente a los métodos del servicio de la
   cuenta del usuario(tanto en su retorno,su nombre,sus excepciones y sus parámetros).

   Algunos métodos necesitan información que puede venir por la url o en el cuerpo de la petición.
 Para esos casos se debe indicar explícitamente como va a llegar dicha información.
 Si es por la url se debe usar la anotación @PathVariable y si es dentro del cuerpo
 de la petición se usa @RequestBody, para este último caso se recomienda que vaya acompañado
 de la anotación @Valid(para que tenga en cuenta las anotaciones de validación
 de los atributos de los DTO)

 Para el caso de @PathVariable,el nombre del atributo debe coincidir con el nombre que
 está entre llaves en la url del mapping. Por ejemplo, si tenemos @PathVariable int id,
 entonces la url sería"/{id
     */

    @Operation(summary = "Cambiar Contraseña", description = "Permite cambiar la contraseña")
    @PostMapping("/cambiar-password")
    public ResponseEntity<MensajeDTO<String>> cambiarPassword(@RequestBody CambiarPasswordDTO cambiarPasswordDTO) throws PasswordNoEditadaException {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, cuentaServicio.cambiarPassword(cambiarPasswordDTO)) );
    }

    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDTO<String>> editarCuenta(@Valid @RequestBody EditarCuentaDTO cuenta) throws Exception{
        cuentaServicio.editarCuenta(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta editada exitosamente"));
    }

    @Operation(summary = "Enviar codigo", description = "Permite enviar codigo para cambiar la contraseña")
    @PostMapping("/enviar-codigo/{correo}")
    public ResponseEntity<MensajeDTO<String>> enviarCodigoCambioPassword(@PathVariable String correo) throws CodigoValidacionNoEnviadoException {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, cuentaServicio.enviarCodigoRecuperacionPassword(correo)) );
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable String id) throws Exception{
        cuentaServicio.eliminarCuenta(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta eliminada exitosamente"));
    }


    @GetMapping("/obtener/{id}")
    public ResponseEntity<MensajeDTO<InformacionCuentaDTO>> obtenerInformacionCuenta(@PathVariable String id) throws Exception{
        InformacionCuentaDTO info = cuentaServicio.obtenerInformacionCuenta(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @GetMapping("/listar-todo")
    public ResponseEntity<MensajeDTO<List<ItemCuentaDTO>>> listarCuentas(){
        List<ItemCuentaDTO> lista = cuentaServicio.listarCuentas();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }


}
