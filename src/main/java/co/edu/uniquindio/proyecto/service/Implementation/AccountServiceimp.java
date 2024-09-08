package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Accounts.User;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.repository.UserRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
public class AccountServiceimp implements AccountService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @Override
    public String crearCuenta(CrearCuentaDTO cuentaDTO) throws Exception {
        // Crear el objeto User
        User user = User.builder()
                .identityDocument(cuentaDTO.cedula())
                .username(cuentaDTO.nombre())
                .address(cuentaDTO.direccion())
                .phoneNumber(cuentaDTO.telefono())
                .build();

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Crear la cuenta vinculada al ID del usuario
        Account account = Account.builder()
                .email(cuentaDTO.correo())
                .password(cuentaDTO.password())
                .registrationDate(LocalDateTime.now())
                .status(AccountStatus.ACTIVO)
                .idUser(savedUser.getId())
                .rol(Rol.CLIENTE)
                .build();

        // Guardar la cuenta en la base de datos
        Account savedAccount = accountRepository.save(account);

        // Verificar si la cuenta se guardó correctamente
        if (savedAccount != null) {
            return "Cuenta guardada correctamente";
        } else {
            throw new Exception("Error al crear la cuenta");
        }
    }

    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {

        return "";
    }

    @Override
    public String eliminarCuenta(String id) throws Exception {
        return "";
    }

    @Override
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {
        return null;
    }

    @Override
    public String enviarCodigoRecuperacionPassword(String correo) throws Exception {
        return "";
    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        return "";
    }

    @Override
    public String iniciarSesion(LoginDTO loginDTO) throws Exception {
        return "";
    }
}
