package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;

import java.util.List;

public interface AccountService {

    String createAccount(createAccountDTO cuenta) throws Exception;

    String editAccount(editAccountDTO cuenta) throws Exception;

    String deleteAccount(String id) throws Exception;

    dtoAccountInformation obtainAccountInformation(String id) throws Exception;

    List<dtoAccountItem> listAccounts();

    String sendPasswordRecoveryCode(String correo) throws Exception;

    String changePassword(changePasswordDTO changePasswordDTO, String corre, String code) throws Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;

    String activateAccount(String correo, String code) throws Exception;


}
