package co.edu.uniquindio.uniEventos.excepciones;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class EventoNoEncontradoException extends RuntimeException{

    private final TipoError tipoError;

    public EventoNoEncontradoException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public EventoNoEncontradoException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public EventoNoEncontradoException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
