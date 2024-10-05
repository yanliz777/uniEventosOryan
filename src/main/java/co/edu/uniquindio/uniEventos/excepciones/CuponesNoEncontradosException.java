package co.edu.uniquindio.uniEventos.excepciones;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class CuponesNoEncontradosException extends Exception{

    private final TipoError tipoError;

    public CuponesNoEncontradosException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public CuponesNoEncontradosException(String mensaje, TipoError tipoError){
        super(mensaje);
        this.tipoError = tipoError;
    }

    public CuponesNoEncontradosException(String mensaje, TipoError tipoError, Throwable causa){
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
