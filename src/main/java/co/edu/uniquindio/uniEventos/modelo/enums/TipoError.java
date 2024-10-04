package co.edu.uniquindio.uniEventos.modelo.enums;

import lombok.Getter;

@Getter
public enum TipoError {

    FILE_NOT_FOUND("El archivo no fue encontrado"),
    INVALID_DATA("La información suministrada es incorrecta"),
    CONNECTION_ERROR("Hubo un problema relacionado a la conexión de red"),
    UNKNOWN_ERROR("Se ha presentado un error desconocido, acción incompleta");

    private final String value;
    TipoError(String value){
        this.value = value;
    }
}
