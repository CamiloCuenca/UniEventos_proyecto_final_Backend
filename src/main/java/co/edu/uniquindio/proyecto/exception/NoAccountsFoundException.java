package co.edu.uniquindio.proyecto.exception;

public class NoAccountsFoundException extends RuntimeException {
  public NoAccountsFoundException() {
    super("No se encontraron cuentas en el sistema");
  }
}
