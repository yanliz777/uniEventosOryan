package co.edu.uniquindio.uniEventos.servicios.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenesServicio {

    String subirImagen(MultipartFile imagen) throws Exception;
    void eliminarImagen(String nombreImagen) throws Exception;
}
