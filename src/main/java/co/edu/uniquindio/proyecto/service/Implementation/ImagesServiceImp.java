package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.service.Interfaces.ImagesService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
@Service
public class ImagesServiceImp implements ImagesService {
    /**
     * Metodo que se encarga de subir la imagen.
     * @param imagen
     * @return
     * @throws Exception
     */
    @Override
    public String uploadImage(MultipartFile imagen) throws Exception {

            Bucket bucket = StorageClient.getInstance().bucket();


            String fileName = String.format( "%s-%s", UUID.randomUUID().toString(), imagen.getOriginalFilename() );


            Blob blob = bucket.create( fileName, imagen.getInputStream(), imagen.getContentType() );


            return String.format(
                    "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                    bucket.getName(),
                    blob.getName()
            );

        }
    @Override
    public void deleteImage(String nombreImagen) throws Exception {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(nombreImagen);
        blob.delete();
    }
}
