package co.edu.uniquindio.proyecto.exception.Cart;

public class CartNotFoundException extends RuntimeException {
  public CartNotFoundException(String accountId) {
    super("No existe un carrito para la cuenta con ID: " + accountId);
  }
}

