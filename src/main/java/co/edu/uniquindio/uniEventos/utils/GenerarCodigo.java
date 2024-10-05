package co.edu.uniquindio.uniEventos.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GenerarCodigo {

    @Value("${cupones.promocionales}")
    private int longitudCupon;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String randomCodigoCupones(){
        StringBuilder builder = new StringBuilder();
        int longitud = longitudCupon;
        while(longitud-- != 0){
            int character = (int) ( Math.random() * ALPHA_NUMERIC_STRING.length() );
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
