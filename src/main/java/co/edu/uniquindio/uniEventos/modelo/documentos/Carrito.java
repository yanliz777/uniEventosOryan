package co.edu.uniquindio.uniEventos.modelo.documentos;

import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document("carritos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carrito {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private ObjectId idUsuario;
    private LocalDateTime fecha;
    private List<DetalleCarrito> items;
}
