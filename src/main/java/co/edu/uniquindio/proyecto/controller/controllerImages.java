package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imagenes")
public class controllerImages {

    private final ImagesService imagesService;

    @PostMapping("/upload-image")
    public ResponseEntity<MessageDTO<String>> uploadImage(@RequestParam("imagen") MultipartFile imagen) throws Exception{
        String respuesta = imagesService.uploadImage(imagen);
        return ResponseEntity.ok().body(new MessageDTO<>(false, respuesta));
    }

    @DeleteMapping("/remove-imagen")
    public ResponseEntity<MessageDTO<String>> eliminar(@RequestParam("idImagen") String idImagen)  throws Exception{
        imagesService.deleteImage( idImagen );
        return ResponseEntity.ok().body(new MessageDTO<>(false, "La imagen fue eliminada correctamente"));
    }

    @PostMapping("/upload-qr")
    public ResponseEntity<MessageDTO<String>> uploadQR(@RequestBody byte[] qrBytes, @RequestParam("fileName") String fileName) throws Exception {
        String qrUrl = imagesService.uploadQR(qrBytes, fileName);
        return ResponseEntity.ok().body(new MessageDTO<>(false, qrUrl));
    }


}


