package co.edu.uniquindio.uniEventos.modelo.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Pago {
    private String id;

    private String moneda;
    private String codigoAutorizacion;
    private String tipoPago;
    private String detalleEstado;
    private LocalDateTime fecha;
    private float valorTransaccion;
    private String estado;
}
