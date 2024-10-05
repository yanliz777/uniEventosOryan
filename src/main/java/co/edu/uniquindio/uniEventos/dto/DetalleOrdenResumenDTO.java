package co.edu.uniquindio.uniEventos.dto;

public record DetalleOrdenResumenDTO(
        String idEvento,
        String nombreEvento,
        String nombreLocalidad,
        int cantidad,
        float precio
) {}
