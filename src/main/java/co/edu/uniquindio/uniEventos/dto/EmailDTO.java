package co.edu.uniquindio.uniEventos.dto;

public record EmailDTO(
        String asunto,
        String cuerpo,
        String destinatario
) {
}
