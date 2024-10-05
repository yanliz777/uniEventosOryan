package co.edu.uniquindio.uniEventos.excepciones.orden;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoError;

public class HistorialOrdenesVacionException extends Exception {
    private final TipoError tipoError;

    public HistorialOrdenesVacionException(String mensaje) {
        super(mensaje);
        this.tipoError = TipoError.INVALID_DATA; // Puedes usar el TipoError que más se ajuste
    }

    public HistorialOrdenesVacionException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public HistorialOrdenesVacionException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError() {
        return tipoError;
    }
}
