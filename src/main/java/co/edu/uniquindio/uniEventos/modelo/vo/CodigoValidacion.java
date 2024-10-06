package co.edu.uniquindio.uniEventos.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class CodigoValidacion {
    private String codigo;
    private LocalDateTime fechaCreacion;

    public CodigoValidacion(String codigo, LocalDateTime fechaCreacion) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
    }
}
