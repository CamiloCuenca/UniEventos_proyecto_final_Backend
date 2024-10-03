package co.edu.uniquindio.proyecto.service.Interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    String uploadImage(MultipartFile imagen) throws Exception;
    void deleteImage(String nombreImagen) throws Exception;
    String uploadQR(byte[] qrBytes, String fileName) throws Exception;

}

