package co.edu.uniquindio.uniEventos.dto;

public record CambiarPasswordDTO(
        String email,
        String codigoVerificacion,
        String passwordNueva
) {
}
