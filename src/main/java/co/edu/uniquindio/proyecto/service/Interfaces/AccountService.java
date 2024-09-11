package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.*;

import java.util.List;

public interface AccountService {

    String crearCuenta(CrearCuentaDTO cuenta) throws Exception;

    String editarCuenta(EditarCuentaDTO cuenta) throws Exception;

    String eliminarCuenta(String id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception;

    List<ItemCuentaDTO> listarCuentas();

    String enviarCodigoRecuperacionPassword(String correo) throws Exception;

    String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception;

    String iniciarSesion(LoginDTO loginDTO) throws Exception;



}
