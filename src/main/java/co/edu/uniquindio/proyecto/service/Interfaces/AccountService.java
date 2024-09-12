package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.*;

import java.util.List;

public interface AccountService {

    String crearCuenta(createAccountDTO cuenta) throws Exception;

    String editarCuenta(editAccountDTO cuenta) throws Exception;

    String eliminarCuenta(String id) throws Exception;

    dtoAccountInformation obtenerInformacionCuenta(String id) throws Exception;

    List<dtoAccountItem> listarCuentas();

    String enviarCodigoRecuperacionPassword(String correo) throws Exception;

    String cambiarPassword(changePasswordDTO changePasswordDTO) throws Exception;

    String iniciarSesion(LoginDTO loginDTO) throws Exception;



}
