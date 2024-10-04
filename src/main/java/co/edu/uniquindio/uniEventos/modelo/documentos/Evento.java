package co.edu.uniquindio.uniEventos.modelo.documentos;

import co.edu.uniquindio.uniEventos.modelo.enums.EstadoEvento;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("eventos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Evento {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String descripcion;
    private String direccion;
    private String imagenPortada;
    private String imagenLocalidades;
    private String ciudad;
    private List<Localidad> localidades;
    private TipoEvento tipo;
    private LocalDateTime fechaEvento;
    private EstadoEvento estado;
}
