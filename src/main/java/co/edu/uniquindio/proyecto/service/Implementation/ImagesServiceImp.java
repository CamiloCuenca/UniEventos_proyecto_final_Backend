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

    /** Metodo que se encarga de subir la imagen.
     *
     * @param imagen Objeto MultipartFile que contiene la imagen a subir.
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

    /** Eliminar una imagen del sistema de almacenamiento.
     *
      * @param nombreImagen Nombre o identificador de la imagen que se desea eliminar.
     * @throws Exception
     */
    @Override
    public void deleteImage(String nombreImagen) throws Exception {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(nombreImagen);
        blob.delete();
    }

    /** Subir un código QR generado al sistema de almacenamiento.
     *
     * @param qrBytes Array de bytes que representa el código QR.
     * @param fileName Nombre del archivo que se usará para almacenar el código QR.
     * @return
     * @throws Exception
     */
    @Override
    public String uploadQR(byte[] qrBytes, String fileName) throws Exception {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(fileName, qrBytes, "image/png");

        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucket.getName(),
                blob.getName()
        );
    }
}
