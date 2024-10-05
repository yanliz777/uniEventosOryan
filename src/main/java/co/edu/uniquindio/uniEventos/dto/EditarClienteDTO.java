package co.edu.uniquindio.uniEventos.dto;

public record EditarClienteDTO(
        String id,
        String nombre,
        String telefono,
        String direccion,
        String password
) {
}
