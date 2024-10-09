package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.configuracion.JWTUtils;
import co.edu.uniquindio.uniEventos.configuracion.PlantillasEmailConfig;
import co.edu.uniquindio.uniEventos.dto.*;
import co.edu.uniquindio.uniEventos.excepciones.cuenta.*;
import co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.uniEventos.modelo.enums.Rol;
import co.edu.uniquindio.uniEventos.modelo.vo.CodigoValidacion;
import co.edu.uniquindio.uniEventos.modelo.vo.Usuario;
import co.edu.uniquindio.uniEventos.repositorios.CuentaRepo;
import co.edu.uniquindio.uniEventos.servicios.interfaces.CuentaServicio;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


// @Service: mantiene la lógica de negocio y los métodos de llamada en la capa de repository.
@Service
@RequiredArgsConstructor//reemplaza al constructor que esta comentado
public class CuentaServicioImpl implements CuentaServicio {

    /*repositorio para cominicarnos con la BD y utilizar los métodos CRUDS.
    Es decir,para poder invocar sus métodos de acceso a la base de datos.
    Es una variable inmutable, algo que no va hacer modificado*/
    private final CuentaRepo cuentaRepo;
    private final EmailServicio emailService;
    private final JWTUtils jwtUtils;

    @Override
    public String activarCuenta(String email, String codigoValidacion) throws CuentaNoActivadaException {

        try {

            // 1. Buscar la cuenta por email.
            Optional<Cuenta> cuentaOptional = cuentaRepo.findByEmail(email);

            // 2. Verificar si la cuenta existe.
            if (cuentaOptional.isEmpty()) {
                throw new Exception("No se encontró una cuenta con ese email");
            }

            Cuenta cuenta = cuentaOptional.get();

            // 3. Verificar que la cuenta esté inactiva.
            if (cuenta.getEstado() != EstadoCuenta.INACTIVO) {
                throw new Exception("La cuenta ya está activa o ha sido eliminada");
            }

            // 4. Verificar si el código de validación es correcto y está dentro del tiempo límite.
            CodigoValidacion codigoValidacionCuenta = cuenta.getCodigoValidacionRegistro();

            if (codigoValidacionCuenta == null ||
                    !codigoValidacionCuenta.getCodigo().equals(codigoValidacion) ||
                    codigoValidacionCuenta.getFechaCreacion().plusMinutes(15).isBefore(LocalDateTime.now())) {

                throw new Exception("El código de validación es incorrecto o ha expirado");
            }

            // 5. Activar la cuenta cambiando el estado a ACTIVO.
            cuenta.setEstado(EstadoCuenta.ACTIVO);
            cuenta.setCodigoValidacionRegistro(null);  // Limpiar el código de validación después de activarla.

            // 6. Guardar los cambios en la base de datos.
            cuentaRepo.save(cuenta);

            return "La cuenta fue activada correctamente";

        }catch(Exception e){

            throw new CuentaNoActivadaException("Error al activar la cuenta." +e.getMessage());
        }
    }

    @Override
    public String crearCuenta(CrearCuentaDTO cuenta) throws CuentaNoCreadaException {
        try {
            if( existeEmail(cuenta.email()) ){
                throw new CuentaNoCreadaException("El email " + cuenta.email()+ " ya existe");
            }

            if( existeCedula(cuenta.cedula()) ){
                throw new CuentaNoCreadaException("La cédula " + cuenta.cedula()+ " ya existe");
            }

            String codigoAleatorio = generarCodigo();

            Cuenta nuevaCuenta = new Cuenta();
            //settiamos los datos del DTO a la nuevaCuenta:
            nuevaCuenta.setEmail(cuenta.email());
            nuevaCuenta.setPassword(encriptarPassword(cuenta.password()));
            nuevaCuenta.setRol(Rol.CLIENTE);
            nuevaCuenta.setFechaRegistro(LocalDateTime.now());
            nuevaCuenta.setUsuario(new Usuario(
                    cuenta.telefono(),
                    cuenta.direccion(),
                    cuenta.nombre(),
                    cuenta.cedula()
            ));
            nuevaCuenta.setEstado(EstadoCuenta.INACTIVO);
            nuevaCuenta.setCodigoValidacionRegistro(
                    new CodigoValidacion(
                            codigoAleatorio,
                            LocalDateTime.now()
                    )
            );
            cuentaRepo.save(nuevaCuenta);

            String body = PlantillasEmailConfig.bodyCreacionCuenta.replace("[Nombres]",
                    cuenta.nombre()).replace("[Codigo_Activacion]",codigoAleatorio);


            //toca enviar un email al usuario con el codigo de validación
            emailService.enviarCorreo( new EmailDTO("Creacion cuenta Unievento",  body, cuenta.email()) );

            return "Su cuenta se ha creado correctamente";

        } catch (Exception e){
            throw new CuentaNoCreadaException("Error al crear cuenta" + e.getMessage());
        }
    }



    /*
    Metodo que me permite encriptar la contraseña
     */
    private String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }

    /*
    metodo para verificar que una cedula
    existe llamando un metodo implementado en la interface CuentaRepo
    */
    private boolean existeCedula(String cedula) {
        return cuentaRepo.findByCedula(cedula).isPresent();
    }

    /*
    metodo para verificar que un email
    existe llamando un metodo implementado en la interface CuentaRepo
    */
    private boolean existeEmail(String email) {

        return cuentaRepo.findByEmail(email).isPresent();
    }

    /**
     *Metodo que me permite generar un código aleatorio:
     */
    private String generarCodigo(){
        String caracteres = "ABCDEFGHIJKLMNOPQRST1234567890";
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int indice = (int)(caracteres.length() * Math.random());
            codigo.append(caracteres.charAt(indice));
        }
        return codigo.toString();
    }


    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws CuentaNoEditadaException {
        try{
            Cuenta cuentaModificada = obtenerCuenta(cuenta.id());

            cuentaModificada.getUsuario().setNombre( cuenta.nombre() );
            cuentaModificada.getUsuario().setTelefono( cuenta.telefono() );
            cuentaModificada.getUsuario().setDireccion( cuenta.direccion() );
            cuentaModificada.setPassword( cuenta.password() );
            //Como el objeto cuenta ya tiene un id, el save() no crea un nuevo registro sino que
            // actualiza el que ya existe
            cuentaRepo.save(cuentaModificada);
            return cuentaModificada.getId();

        } catch (Exception e){
            throw new CuentaNoEditadaException("Error al editar cuenta del profesional" + e.getMessage());
        }
    }

    /*
  Metodo que me permite obtener una cuenta por su id:
    */
    private Cuenta obtenerCuenta(String id) throws Exception {
        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(id);

        if(optionalCuenta.isEmpty()){
            throw new Exception("No se encontró la cuenta con el id "+id);
        }

        return optionalCuenta.get();
    }


    @Override
    public String eliminarCuenta(String id) throws Exception {
        //Buscamos la cuenta del usuario que se quiere eliminar
        Cuenta cuenta = obtenerCuenta(id);

        cuenta.setEstado(EstadoCuenta.ELIMINADO);
//Como el objeto cuenta ya tiene un id, el save() no crea un nuevo registro sino que
// actualiza el que ya existe
        cuentaRepo.save(cuenta);
        return cuenta.getId();
    }


    @Override
    @Transactional(readOnly = true)
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {
        //Buscamos la cuenta del usuario que se quiere obtener
        Cuenta cuenta = obtenerCuenta(id);

//Retornamos la información de la cuenta del usuario
        return new InformacionCuentaDTO(
                cuenta.getId(),
                cuenta.getUsuario().getCedula(),
                cuenta.getUsuario().getNombre(),
                cuenta.getUsuario().getTelefono(),
                cuenta.getUsuario().getDireccion(),
                cuenta.getEmail()
        );
    }

    @Override
    public String enviarCodigoRecuperacionPassword(String correo) throws CodigoValidacionNoEnviadoException {

        try{
            //Optional<Cuenta>  cuentaOptional: se usa para almacenar el resultado de la búsqueda, que puede
            //o no contener un valor (una cuenta encontrada o no).
            Optional<Cuenta>  cuentaOptional = cuentaRepo.findByEmail(correo);

            if(cuentaOptional.isEmpty()){
                throw new CodigoValidacionNoEnviadoException("No se encontró una cuenta con ese email ");
            }

            //Se usa cuentaOptional.get() para obtener la instancia de la clase Cuenta, que contiene los detalles de la cuenta:
            Cuenta cuenta = cuentaOptional.get();//get accede a lo que esta por dentro del opcional, por eso la validación
            String codigoValidacion = generarCodigo();

            //Almacena el código de validación y el momento de su generación en la cuenta del usuario.
            cuenta.setCodigoValidacionPassword(new CodigoValidacion(
                    codigoValidacion,
                    LocalDateTime.now()
            ));

            cuentaRepo.save(cuenta);

            //toca enviar un email al usuario con el codigo de validación
            emailService.enviarCorreo( new EmailDTO(correo, "Asunto mensaje", "Hola") );

            return "Se ha enviado un correo con el código de validación";

        } catch (Exception e){
            throw new CodigoValidacionNoEnviadoException("Error al editar cuenta del profesional" + e.getMessage());
        }
    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws PasswordNoEditadaException {

        try{
            Optional<Cuenta>  cuentaOptional = cuentaRepo.findByEmail(cambiarPasswordDTO.email());

            if (cuentaOptional.isEmpty()){
                throw new PasswordNoEditadaException("El correo dado no esta registrado");
            }
            Cuenta cuenta = cuentaOptional.get();//para obtener la instancia de la clase Cuenta Uitlizanod Opcional
            CodigoValidacion codigoValidacion = cuenta.getCodigoValidacionPassword();

            if (codigoValidacion.getCodigo().
                    equals(cambiarPasswordDTO.codigoVerificacion()))
            {
                if(codigoValidacion.getFechaCreacion().
                        plusMinutes(15).isAfter(LocalDateTime.now()))
                {
                    cuenta.setPassword(cambiarPasswordDTO.passwordNueva());
                    cuentaRepo.save(cuenta);
                }
                else{
                    throw new PasswordNoEditadaException("El código ya expiro");
                }
            }
            else{
                throw new PasswordNoEditadaException("El código de validación es incorrecto");
            }

            return "Se ha cambiado su contraseña";

        } catch (Exception e){
            throw new PasswordNoEditadaException("Error cambiar el password" + e.getMessage());
        }
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws SesionNoIniciadaException {
        try{
            Cuenta cuenta = obtenerPorEmail(loginDTO.correo());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if( !passwordEncoder.matches(loginDTO.password(), cuenta.getPassword()) ) {
                throw new SesionNoIniciadaException("La contraseña es incorrecta");
            }
            Map<String, Object> map = construirClaims(cuenta);
            return new TokenDTO( jwtUtils.generarToken(cuenta.getEmail(), map) );
        }catch(Exception e){
            throw new SesionNoIniciadaException("Sesión no fue iniciada. " + e.getMessage());
        }
    }

    private Cuenta obtenerPorEmail(String email) throws Exception{
        try {
            Optional<Cuenta> cuenta = cuentaRepo.findByEmail( email );

            if( cuenta.isEmpty() ){
                throw new Exception("Cuenta no encontrado.");
            }

            return cuenta.get();
        }catch(Exception e){
            throw new Exception("Error al buscar el correo." + e.getMessage());
        }
    }

    /*
    Este metodo construye los claims (o la carga útil) del token.
    En este caso se le está agregando el rol, nombre y el id de
    la cuenta, esta información se agrupa en un objeto de tipo Map.
     */
    private Map<String, Object> construirClaims(Cuenta cuenta) {
        return Map.of(
                "rol", cuenta.getRol(),
                "nombre", cuenta.getUsuario().getNombre(),
                "id", cuenta.getId()
        );
    }


    @Override
    public List<ItemCuentaDTO> listarCuentas() {
//Obtenemos todas las cuentas de los usuarios de la base de datos
        List<Cuenta> cuentas = cuentaRepo.findAll();
//Creamos una lista de DTOs
        List<ItemCuentaDTO> items = new ArrayList<>();
//Recorremos la lista de cuentas y por cada una creamos un DTO y lo agregamos a la lista
        for (Cuenta cuenta : cuentas) {
            items.add( new ItemCuentaDTO(
                    cuenta.getId(),
                    cuenta.getUsuario().getNombre(),
                    cuenta.getEmail(),
                    cuenta.getUsuario().getTelefono()
            ));
        }
        return items;
    }
}
