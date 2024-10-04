package co.edu.uniquindio.uniEventos.excepciones.orden;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class OrdenNoEncontradaException extends Exception {
    private final TipoError tipoError;

    public OrdenNoEncontradaException(String mensaje) {
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR; // Puedes cambiar el tipo de error según el caso
    }

    public OrdenNoEncontradaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public OrdenNoEncontradaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError() {
        return tipoError;
    }
}
