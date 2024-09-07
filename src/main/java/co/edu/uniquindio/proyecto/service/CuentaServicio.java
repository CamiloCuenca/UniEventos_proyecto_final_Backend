package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.dto.Account.*;

public interface CuentaServicio {

    String crearCuenta(CrearCuentaDTO cuenta) throws Exception;

    String editarCuenta(EditarCuentaDTO cuenta) throws Exception;

    String eliminarCuenta(String id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception;

    String enviarCodigoRecuperacionPassword(String correo) throws Exception;

    String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception;

    String iniciarSesion(LoginDTO loginDTO) throws Exception;

}
