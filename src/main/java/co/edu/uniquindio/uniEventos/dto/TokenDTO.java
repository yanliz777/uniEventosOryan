package co.edu.uniquindio.uniEventos.dto;

/*
Este record será útil para comunicar el backend y el frontend para
el paso de mensajes que contengan el token como respuesta o solicitud (como el login).
 */
public record TokenDTO(
        String token
) {
}
