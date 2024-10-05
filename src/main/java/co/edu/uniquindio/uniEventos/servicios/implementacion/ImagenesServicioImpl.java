package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.servicios.interfaces.ImagenesServicio;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import java.util.UUID;

/*
Esta clase proporciona métodos para subir y eliminar imágenes en Firebase Storage. El metodo
subirImagen() genera un nombre único para el archivo, lo sube al bucket y retorna su URL
pública. El metodo eliminarImagen() obtiene el blob correspondiente al nombre de la imagen y
lo elimina del bucket.

Un bucket es un contenedor de almacenamiento que organiza y guarda objetos (archivos y sus
metadatos) en un servicio de almacenamiento en la nube, en este caso Firebase.
 */
@Service
public class ImagenesServicioImpl implements ImagenesServicio {
    @Override
    public String subirImagen(MultipartFile multipartFile) throws Exception{
        Bucket bucket = StorageClient.getInstance().bucket();
        String fileName = String.format( "%s-%s", UUID.randomUUID().toString(),
                multipartFile.getOriginalFilename() );
        Blob blob = bucket.create( fileName, multipartFile.getInputStream(),
                multipartFile.getContentType() );
        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucket.getName(),
                blob.getName()
        );
    }

    @Override
    public void eliminarImagen(String nombreImagen) throws Exception{
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(nombreImagen);
        blob.delete();
    }
}

