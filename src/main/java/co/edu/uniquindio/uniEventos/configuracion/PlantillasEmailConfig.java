package co.edu.uniquindio.uniEventos.configuracion;

import org.springframework.stereotype.Component;

@Component
public class PlantillasEmailConfig {

    public static final String bodyCreacionCuenta = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Bienvenido a UniEvento</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background-color: #e0f7fa; /* Fondo azul clarito */\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "        }\n" +
            "        .header {\n" +
            "            background-color: #19576e;\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 20px;\n" +
            "            font-size: 24px;\n" +
            "            border-radius: 10px;\n" +
            "        }\n" +
            "        .container {\n" +
            "            text-align: center;\n" +
            "            margin: 50px auto;\n" +
            "            padding: 20px;\n" +
            "            width: 80%;\n" +
            "            max-width: 600px;\n" +
            "            background-color: white;\n" +
            "            border-radius: 10px;\n" +
            "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
            "        }\n" +
            "        .welcome-message {\n" +
            "            font-size: 18px;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "        .activation-code {\n" +
            "            font-weight: bold;\n" +
            "            color: #19576e;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "    <div class=\"header\">\n" +
            "        Bienvenido a Unieventos!\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"container\">\n" +
            "        <p class=\"welcome-message\">[Nombres], Gracias por registrarte en nuestra página, \"UniEventos\".</p>\n" +
            "        <p>Para activar su cuenta, use el siguiente código:</p>\n" +
            "        <p class=\"activation-code\">Código de Activación: [Codigo_Activacion]</p>\n" +
            "    </div>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    public static final String bodyActualizarPassword = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Cambio contraseña</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background-color: #e0f7fa; /* Fondo azul clarito */\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "        }\n" +
            "        .header {\n" +
            "            background-color: #19576e;\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 20px;\n" +
            "            font-size: 24px;\n" +
            "            border-radius: 10px;\n" +
            "        }\n" +
            "        .container {\n" +
            "            text-align: center;\n" +
            "            margin: 50px auto;\n" +
            "            padding: 20px;\n" +
            "            width: 80%;\n" +
            "            max-width: 600px;\n" +
            "            background-color: white;\n" +
            "            border-radius: 10px;\n" +
            "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
            "        }\n" +
            "        .welcome-message {\n" +
            "            font-size: 18px;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "        .activation-code {\n" +
            "            font-weight: bold;\n" +
            "            color: #19576e;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "    <div class=\"header\">\n" +
            "        Cambio de Contraseña!\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"container\">\n" +
            "        <p class=\"welcome-message\">Solicitaste un cambio de contraseña en, \"Evento\".</p>\n" +
            "        <p>Para actualizar tu contraseña, use el siguiente código:</p>\n" +
            "        <p class=\"activation-code\">Código de Activación: [Codigo_Activacion]</p>\n" +
            "    </div>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
}

