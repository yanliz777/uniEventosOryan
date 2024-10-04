package co.edu.uniquindio.uniEventos.excepciones.dataError;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
