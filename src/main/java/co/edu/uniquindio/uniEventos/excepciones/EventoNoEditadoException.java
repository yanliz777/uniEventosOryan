package co.edu.uniquindio.uniEventos.excepciones;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class EventoNoEditadoException extends RuntimeException{

    private final TipoError tipoError;

    public EventoNoEditadoException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public EventoNoEditadoException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public EventoNoEditadoException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
