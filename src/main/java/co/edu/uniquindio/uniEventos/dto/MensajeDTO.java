package co.edu.uniquindio.uniEventos.dto;

/*
Este record lo usaremos para la transferencia de mensajes
tanto para respuestas válidas como para respuestas erróneas
en la API que será desarrollada próximamente.
 */
public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
