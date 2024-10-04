package co.edu.uniquindio.uniEventos.excepciones.dataError;

public class EmailNotFound extends RuntimeException{
    public EmailNotFound(String message) {
        super(message);
    }
}
