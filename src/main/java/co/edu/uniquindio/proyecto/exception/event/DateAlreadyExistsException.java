package co.edu.uniquindio.proyecto.exception.event;

import java.time.LocalDateTime;

public class DateAlreadyExistsException extends RuntimeException{
    public DateAlreadyExistsException(LocalDateTime dateTime){
        super("La fecha ya esta en uso"+ dateTime);
    }
}
