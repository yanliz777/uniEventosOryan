package co.edu.uniquindio.uniEventos.modelo.vo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

//el paquete vo(value object) indica que las clases que estan allí no son docuemnto
// pero cmoplementan a estos

@Getter
@Setter
public class DetalleOrden {
    private String id;
    private ObjectId idEvento;
    private float precio;
    private String nombreLocalidad;
    private int cantidad;

}
