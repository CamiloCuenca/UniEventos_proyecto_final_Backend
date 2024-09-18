package co.edu.uniquindio.proyecto.exception.account;

public class AccountNtFoundException extends RuntimeException {
  public AccountNtFoundException(String id) {
    super("No se encontró el usuario con el id " + id);
  }
}
