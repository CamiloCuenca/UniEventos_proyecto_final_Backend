package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.*;

import java.util.List;

public interface AccountService {

    String createAccount(createAccountDTO cuenta) throws Exception;

    String editAccount(editAccountDTO cuenta) throws Exception;

    String deleteAccount(String id) throws Exception;

    dtoAccountInformation obtainAccountInformation(String id) throws Exception;

    List<dtoAccountItem> listAccounts();

    String enviarCodigoRecuperacionPassword(String correo) throws Exception;

    String cambiarPassword(changePasswordDTO changePasswordDTO) throws Exception;

    String iniciarSesion(LoginDTO loginDTO) throws Exception;



}
