package co.edu.uniquindio.proyecto.model.Image;

import co.edu.uniquindio.proyecto.service.Interfaces.ImagesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ImagesServiceTest {

    @Autowired
    private ImagesService imagesService;

    @Test
    public void testUploadImage() throws Exception {
        // Cargar un archivo de imagen de prueba desde los recursos de test
        ClassPathResource imgFile = new ClassPathResource("static/img.png");
        InputStream inputStream = new FileInputStream(imgFile.getFile());

        // Crear un MultipartFile simulado para enviar como parámetro
        MultipartFile multipartFile = new MockMultipartFile(
                "file", imgFile.getFilename(), "image/jpeg", inputStream
        );

        // Ejecutar el método uploadImage
        String url = imagesService.uploadImage(multipartFile);

        // Aserciones para verificar que la URL no sea nula y sea válida
        assertNotNull(url);
        System.out.println("URL de la imagen subida: " + url);
    }



}
