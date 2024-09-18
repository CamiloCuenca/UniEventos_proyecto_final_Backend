package co.edu.uniquindio.proyecto.exception.account;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String email) {
    super("el email ya existe: " + email);
  }
}
