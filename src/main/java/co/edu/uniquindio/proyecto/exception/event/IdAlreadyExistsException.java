package co.edu.uniquindio.proyecto.exception.event;

public class IdAlreadyExistsException extends RuntimeException {
    public IdAlreadyExistsException(String id) {
      super("Ya se encuentra registrado el id:  " + id);
    }
}
