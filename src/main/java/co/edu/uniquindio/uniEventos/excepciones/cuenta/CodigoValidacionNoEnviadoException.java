package co.edu.uniquindio.uniEventos.excepciones.cuenta;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class CodigoValidacionNoEnviadoException extends Exception {
    private final TipoError tipoError;

    public CodigoValidacionNoEnviadoException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public CodigoValidacionNoEnviadoException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public CodigoValidacionNoEnviadoException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
