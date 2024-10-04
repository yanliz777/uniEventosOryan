package co.edu.uniquindio.uniEventos.excepciones.cuenta;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class CuentaNoEditadaException extends Exception {
    private final TipoError tipoError;

    public CuentaNoEditadaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public CuentaNoEditadaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public CuentaNoEditadaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
