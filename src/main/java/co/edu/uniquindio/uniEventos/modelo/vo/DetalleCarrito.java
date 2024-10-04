package co.edu.uniquindio.uniEventos.modelo.vo;

import lombok.*;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetalleCarrito {
    private ObjectId idEvento;
    private int cantidad;
    private String nombreLocalidad;
}
