package co.edu.uniquindio.proyecto.exception;

import co.edu.uniquindio.proyecto.dto.JWT.dtoMessage;
import co.edu.uniquindio.proyecto.dto.ValidacionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class ExcepcionesGlobales {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<dtoMessage<String>> generalException(Exception e){
        return ResponseEntity.internalServerError().body( new dtoMessage<>(true, e.getMessage()) );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<dtoMessage<List<ValidacionDTO>>> validationException( MethodArgumentNotValidException ex ) {
        List<ValidacionDTO> errores = new ArrayList<>();
        BindingResult results = ex.getBindingResult();


        for (FieldError e: results.getFieldErrors()) {
            errores.add( new ValidacionDTO(e.getField(), e.getDefaultMessage()) );
        }


        return ResponseEntity.badRequest().body( new dtoMessage<>(true, errores) );
    }


}
