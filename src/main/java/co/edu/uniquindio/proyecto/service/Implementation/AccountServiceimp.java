package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Accounts.User;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceimp implements AccountService {

    private final AccountRepository cuentaRepo;

    private boolean existeEmail(String email) {
        return cuentaRepo.findByEmail(email).isPresent();
    }

    private boolean existeCedula(String cedula) {
        return cuentaRepo.findByCedula(cedula).isPresent();
    }


    /**
     * Metodo encargado de crear una cuenta.
     *
     * @param cuenta
     * @return
     * @throws Exception
     */
    @Override
    public String crearCuenta(CrearCuentaDTO cuenta) throws Exception {

        if (existeEmail(cuenta.email())) {
            throw new Exception("El correo " + cuenta.email() + " ya está en uso");
        }

        if (existeCedula(cuenta.cedula())) {
            throw new Exception("La cédula " + cuenta.cedula() + " ya se encuentra registrada");
        }

        //Mapeamos (pasamos) los datos del DTO a un objeto de tipo Cuenta
        Account newAccount = new Account();
        newAccount.setEmail(cuenta.email());
        newAccount.setPassword(cuenta.password());
        newAccount.setRol(Rol.CLIENTE);
        newAccount.setRegistrationDate(LocalDateTime.now());
        newAccount.setUser(new User(
                cuenta.cedula(),
                cuenta.nombre(),
                cuenta.telefono(),
                cuenta.direccion()
        ));
        newAccount.setStatus(AccountStatus.INACTIVO);
        Account createdAccount = cuentaRepo.save(newAccount);
        return createdAccount.getId();
    }


    /**
     * Metodo para actualizarCuenta.
     *
     * @param cuenta
     * @return
     * @throws Exception
     */
    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {
        Optional<Account> optionalAccount = cuentaRepo.findById(cuenta.id());

        if (optionalAccount.isEmpty()) {
            throw new Exception("El usuario no existe");
        }

        Account cuentaModificada = optionalAccount.get();
        cuentaModificada.getUser().setNombre(cuenta.username());
        cuentaModificada.getUser().setTelefono(cuenta.phoneNumber());
        cuentaModificada.getUser().setDireccion(cuenta.address());
        cuentaModificada.setPassword(cuenta.password());
        cuentaRepo.save(cuentaModificada);
        return cuentaModificada.getId();
    }

    @Override
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);

        if (optionalCuenta.isEmpty()) {
            throw new Exception("No se encontró el usuario con el id " + id);
        }

        Account account = optionalCuenta.get();

        return new InformacionCuentaDTO(
                account.getId(),
                account.getUser().getCedula(),
                account.getUser().getTelefono(),
                account.getUser().getDireccion(),
                account.getEmail()
        );
    }

    /**
     * Metodo para listar cuentas.
     *
     * @return
     */
    @Override
    public List<ItemCuentaDTO> listarCuentas() {
        List<Account> cuentas = cuentaRepo.findAll();
        List<ItemCuentaDTO> items = new ArrayList<>();

        for (Account account : cuentas) {
            items.add(new ItemCuentaDTO(
                    account.getId(),
                    account.getUser().getNombre(),
                    account.getEmail(),
                    account.getUser().getTelefono()
            ));
        }
        return items;
    }

    /**
     * Metodo para eliminarCuenta.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String eliminarCuenta(String id) throws Exception {
        //Buscamos la cuenta del usuario que se quiere eliminar
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);
        if (optionalCuenta.isEmpty()) {
            throw new Exception("No se encontró el usuario con el id " + id);
        }
        Account cuenta = optionalCuenta.get();
        cuenta.setStatus(AccountStatus.ELIMINADO);
        cuentaRepo.save(cuenta);
        return cuenta.getId();
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
