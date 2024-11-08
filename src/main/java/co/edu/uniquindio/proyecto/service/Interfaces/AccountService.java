package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;

import java.util.List;

public interface AccountService {

    /**
     * Crea una nueva cuenta de usuario.
     *
     * @param cuenta El objeto createAccountDTO que contiene la información de la cuenta a crear.
     * @return El ID de la cuenta creada como un String o un mensaje de éxito.
     * @throws Exception Si ocurre un error durante la creación de la cuenta.
     */
    String createAccount(createAccountDTO cuenta) throws Exception;

    /**
     * Edita la información de una cuenta existente.
     *
     * @param cuenta El objeto editAccountDTO con los datos actualizados de la cuenta.
     * @param id El ID de la cuenta que se desea modificar.
     * @return Un mensaje de confirmación indicando el éxito de la operación.
     * @throws Exception Si ocurre un error al intentar editar la cuenta.
     */
    String editAccount(editAccountDTO cuenta, String id) throws Exception;

    /**
     * Elimina una cuenta basada en su ID.
     *
     * @param id El ID de la cuenta que se desea eliminar.
     * @return Un mensaje de confirmación de la eliminación.
     * @throws Exception Si ocurre un error durante la eliminación de la cuenta.
     */
    String deleteAccount(String id,PasswordDTO passwordDTO) throws Exception;

    /**
     * Obtiene la información completa de una cuenta.
     *
     * @param id El ID de la cuenta.
     * @return Un objeto dtoAccountInformation con los detalles de la cuenta.
     * @throws Exception Si la cuenta no se encuentra o hay un error durante la consulta.
     */
    MessageDTOC obtainAccountInformation(String id) throws Exception;

    String updatePassword(updatePasswordDTO updatePasswordDTO, String id) throws Exception;

    /**
     * Lista todas las cuentas existentes.
     *
     * @return Una lista de objetos dtoAccountItem que contienen información resumida de las cuentas.
     */
    List<dtoAccountItem> listAccounts();

    /**
     * Envía un código de recuperación de contraseña al correo electrónico del usuario.
     *
     * @param correo El correo electrónico del usuario al que se enviará el código de recuperación.
     * @return Un mensaje de confirmación indicando que el código ha sido enviado.
     * @throws Exception Si ocurre un error al intentar enviar el código de recuperación.
     */
    String sendPasswordRecoveryCode(String correo) throws Exception;

    /**
     * Cambia la contraseña de una cuenta de usuario.
     *
     * @param changePasswordDTO El objeto que contiene la nueva contraseña y otros datos necesarios.
     * @param correo El correo electrónico del usuario que solicita el cambio.
     * @return Un mensaje de confirmación indicando que la contraseña ha sido cambiada.
     * @throws Exception Si ocurre un error al cambiar la contraseña.
     */
    String changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;

    /**
     * Inicia sesión en el sistema.
     *
     * @param loginDTO El objeto LoginDTO que contiene el correo y la contraseña del usuario.
     * @return Un objeto TokenDTO que contiene el token de autenticación.
     * @throws Exception Si las credenciales son incorrectas o si hay un error durante el inicio de sesión.
     */
    TokenDTO login(LoginDTO loginDTO) throws Exception;

    /**
     * Activa una cuenta de usuario.
     *
     * @param correo El correo electrónico del usuario cuya cuenta se va a activar.
     * @param code El código de activación necesario para activar la cuenta.
     * @return Un mensaje de confirmación indicando que la cuenta ha sido activada.
     * @throws Exception Si ocurre un error al activar la cuenta.
     */
    String activateAccount(String correo, String code) throws Exception;

}
