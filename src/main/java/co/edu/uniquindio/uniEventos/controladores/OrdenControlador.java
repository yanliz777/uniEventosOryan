package co.edu.uniquindio.uniEventos.controladores;

import co.edu.uniquindio.uniEventos.dto.MensajeDTO;
import co.edu.uniquindio.uniEventos.servicios.interfaces.OrdenServicio;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orden")
public class OrdenControlador {

    private final OrdenServicio ordenServicio;

    @PostMapping("/notificacion-pago")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> requestBody) {
        ordenServicio.recibirNotificacionMercadoPago(requestBody);
    }
}
