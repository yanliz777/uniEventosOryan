package co.edu.uniquindio.uniEventos.modelo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
    private String id;

    private String telefono;
    private String direccion;
    private String nombre;
    private String cedula;

    public Usuario(String telefono, String direccion, String nombre, String cedula) {
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombre = nombre;
        this.cedula = cedula;
    }

}
