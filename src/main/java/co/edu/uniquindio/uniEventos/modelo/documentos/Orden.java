package co.edu.uniquindio.uniEventos.modelo.documentos;

import co.edu.uniquindio.uniEventos.modelo.vo.DetalleOrden;
import co.edu.uniquindio.uniEventos.modelo.vo.Pago;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("ordenes")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Orden {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private ObjectId idCliente;
    private LocalDateTime fecha;
    private String codigoPasarela;
    private ObjectId idCupon;
    private List<DetalleOrden> items;
    private float total;
    private Pago pago;

}
