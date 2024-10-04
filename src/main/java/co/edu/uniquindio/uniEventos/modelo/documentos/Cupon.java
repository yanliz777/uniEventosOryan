package co.edu.uniquindio.uniEventos.modelo.documentos;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoCupon;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document("cupones")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cupon {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private float descuento;
    private String codigo;
    private LocalDateTime fechaVencimiento;
    private TipoCupon tipo;
    private EstadoCupon estado;
}
