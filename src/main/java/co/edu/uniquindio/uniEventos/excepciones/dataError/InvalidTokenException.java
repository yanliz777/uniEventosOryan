package co.edu.uniquindio.uniEventos.excepciones.dataError;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}