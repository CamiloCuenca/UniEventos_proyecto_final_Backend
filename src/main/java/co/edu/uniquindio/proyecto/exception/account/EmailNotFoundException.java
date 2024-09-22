package co.edu.uniquindio.proyecto.exception.account;

public class EmailNotFoundException extends RuntimeException {
  public EmailNotFoundException(String email) {
    super("el email no existe: " + email);
  }
}
