package co.edu.uniquindio.uniEventos.excepciones.orden;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class OrdenYaCanceladaException extends Exception {

    private final TipoError tipoError;

    public OrdenYaCanceladaException(String mensaje) {
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR; // Puedes usar el enum TipoError que ya tienes
    }

    public OrdenYaCanceladaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public OrdenYaCanceladaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError() {
        return tipoError;
    }
}

