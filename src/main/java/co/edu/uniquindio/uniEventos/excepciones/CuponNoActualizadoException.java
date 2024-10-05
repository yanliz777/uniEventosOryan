package co.edu.uniquindio.uniEventos.excepciones;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class CuponNoActualizadoException extends Exception{

    private TipoError tipoError;

    public CuponNoActualizadoException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public CuponNoActualizadoException(String mensaje, TipoError tipoError){
        super(mensaje);
        this.tipoError = tipoError;
    }

    public CuponNoActualizadoException(String mensaje, TipoError tipoError, Throwable causa){
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
