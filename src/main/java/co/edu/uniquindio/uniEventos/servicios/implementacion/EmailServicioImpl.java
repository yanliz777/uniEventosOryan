package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.EmailDTO;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailServicio {
    @Override
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

    }
}
