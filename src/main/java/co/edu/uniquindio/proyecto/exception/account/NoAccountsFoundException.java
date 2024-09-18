package co.edu.uniquindio.proyecto.exception.account;

public class NoAccountsFoundException extends RuntimeException {
  public NoAccountsFoundException() {
    super("No se encontraron cuentas en el sistema");
  }
}
