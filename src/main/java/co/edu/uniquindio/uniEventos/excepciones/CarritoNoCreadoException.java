package co.edu.uniquindio.uniEventos.excepciones;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class CarritoNoCreadoException extends Exception{

    private final TipoError tipoError;

    public CarritoNoCreadoException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public CarritoNoCreadoException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public CarritoNoCreadoException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
