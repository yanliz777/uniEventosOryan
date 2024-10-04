package co.edu.uniquindio.uniEventos.modelo.documentos;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.uniEventos.modelo.enums.Rol;
import co.edu.uniquindio.uniEventos.modelo.vo.CodigoValidacion;
import co.edu.uniquindio.uniEventos.modelo.vo.Usuario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("cuentas")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuenta {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private Rol rol;
    private String email;
    private CodigoValidacion codigoValidacionRegistro;
    private Usuario usuario;
    private LocalDateTime fechaRegistro;
    private String password;
    private EstadoCuenta estado;
    private CodigoValidacion codigoValidacionPassword;
}
