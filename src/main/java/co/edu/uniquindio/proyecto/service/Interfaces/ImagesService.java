package co.edu.uniquindio.proyecto.service.Interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {

    /**
     * Subir una imagen al sistema de almacenamiento.
     *
     * @param imagen Objeto MultipartFile que contiene la imagen a subir.
     * @return String con la URL o nombre de la imagen subida.
     * @throws Exception Si ocurre un error durante la carga de la imagen.
     */
    String uploadImage(MultipartFile imagen) throws Exception;

    /**
     * Eliminar una imagen del sistema de almacenamiento.
     *
     * @param nombreImagen Nombre o identificador de la imagen que se desea eliminar.
     * @throws Exception Si ocurre un error durante la eliminación de la imagen.
     */
    void deleteImage(String nombreImagen) throws Exception;

    /**
     * Subir un código QR generado al sistema de almacenamiento.
     *
     * @param qrBytes Array de bytes que representa el código QR.
     * @param fileName Nombre del archivo que se usará para almacenar el código QR.
     * @return String con la URL o nombre del archivo del código QR subido.
     * @throws Exception Si ocurre un error durante la carga del código QR.
     */
    String uploadQR(byte[] qrBytes, String fileName) throws Exception;
}
