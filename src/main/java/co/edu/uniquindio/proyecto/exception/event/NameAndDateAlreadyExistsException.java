package co.edu.uniquindio.proyecto.exception.event;

import java.time.LocalDateTime;

public class NameAndDateAlreadyExistsException extends RuntimeException{
    public NameAndDateAlreadyExistsException(String name, LocalDateTime dateTime){
        super("El nombre y la fecha ya esta en uso.");
    }
}
