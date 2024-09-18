package co.edu.uniquindio.proyecto.exception.account;

public class AccountRetrievalException extends RuntimeException {
  public AccountRetrievalException(String message) {
    super("Error al recuperar las cuentas: " + message);
  }
}
