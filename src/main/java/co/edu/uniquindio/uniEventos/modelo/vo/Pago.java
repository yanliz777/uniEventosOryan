package co.edu.uniquindio.uniEventos.modelo.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pago {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String moneda;
    private String codigoAutorizacion;
    private String tipoPago;
    private String detalleEstado;
    private LocalDateTime fecha;
    private float valorTransaccion;
    private String estado;
}
