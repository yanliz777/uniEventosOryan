package co.edu.uniquindio.uniEventos.modelo.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Localidad {
    private float precio;
    private String nombre;
    private int entradasVendidas;
    private int capacidadMaxima;
}
