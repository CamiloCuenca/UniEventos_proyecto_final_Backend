package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;

import java.util.List;

public interface AccountService {

    // Método para crear una cuenta
// Parámetros: createAccountDTO con la información de la cuenta.
// Retorno: String con el ID de la cuenta creada o un mensaje de éxito.
// Excepción: Lanza una excepción si no se puede crear la cuenta.
    String createAccount(createAccountDTO cuenta) throws Exception;

    // Método para editar una cuenta
// Parámetros: editAccountDTO con la información de la cuenta a modificar.
// Retorno: String con un mensaje de éxito o confirmación.
// Excepción: Lanza una excepción si no se puede editar la cuenta.
    String editAccount(editAccountDTO cuenta, String id) throws Exception;

    // Método para eliminar una cuenta
// Parámetros: String con el ID de la cuenta a eliminar.
// Retorno: String con un mensaje de confirmación de eliminación.
// Excepción: Lanza una excepción si no se puede eliminar la cuenta.
    String deleteAccount(String id) throws Exception;

    // Método para obtener la información de una cuenta
// Parámetros: String con el ID de la cuenta.
// Retorno: dtoAccountInformation con los detalles de la cuenta.
// Excepción: Lanza una excepción si no se encuentra la cuenta.
    dtoAccountInformation obtainAccountInformation(String id) throws Exception;

    // Método para listar todas las cuentas
// Retorno: Lista de dtoAccountItem con información resumida de las cuentas.
    List<dtoAccountItem> listAccounts();

    // Método para enviar el código de recuperación de contraseña
// Parámetros: String con el correo electrónico del usuario.
// Retorno: String con un mensaje de confirmación.
// Excepción: Lanza una excepción si no se puede enviar el código de recuperación.
    String sendPasswordRecoveryCode(String correo) throws Exception;

    // Método para cambiar la contraseña de una cuenta
// Parámetros: changePasswordDTO con la nueva contraseña, String con el correo, y String con el código de verificación.
// Retorno: String con un mensaje de éxito.
// Excepción: Lanza una excepción si no se puede cambiar la contraseña.
    String changePassword(changePasswordDTO changePasswordDTO, String correo) throws Exception;

    // Método para iniciar sesión
// Parámetros: LoginDTO con la información de inicio de sesión (correo y contraseña).
// Retorno: TokenDTO con el token de autenticación.
// Excepción: Lanza una excepción si las credenciales son incorrectas o hay un error durante el inicio de sesión.
    TokenDTO login(LoginDTO loginDTO) throws Exception;

    // Método para activar una cuenta
// Parámetros: String con el correo electrónico del usuario y String con el código de activación.
// Retorno: String con un mensaje de confirmación de activación.
// Excepción: Lanza una excepción si no se puede activar la cuenta.
    String activateAccount(String correo, String code) throws Exception;


}
