package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.exception.account.*;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Accounts.User;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceimp implements AccountService {

    private final AccountRepository cuentaRepo;

    private boolean existsEmail(String email) {
        return cuentaRepo.findByEmail(email).isPresent();
    }

    private boolean idExists(String cedula) {
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
    public String createAccount(createAccountDTO cuenta) throws EmailAlreadyExistsException, CedulaAlreadyExistsException {

        if (existsEmail(cuenta.email())) {
            throw new EmailAlreadyExistsException(cuenta.email());
        }

        if (idExists(cuenta.idNumber())) {
            throw new CedulaAlreadyExistsException(cuenta.idNumber());
        }
        //Segmento del codigo que se encarga de encriptar el codigo.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(cuenta.password());

        //Mapeamos (pasamos) los datos del DTO a un objeto de tipo Cuenta
        Account newAccount = new Account();
        newAccount.setEmail(cuenta.email());
        //Clave encriptada.
        newAccount.setPassword(hashedPassword);
        newAccount.setRol(Rol.CLIENTE);
        newAccount.setRegistrationDate(LocalDateTime.now());
        newAccount.setUser(new User(
                cuenta.idNumber(),
                cuenta.name(),
                cuenta.phoneNumber(),
                cuenta.address()
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
    public String editAccount(editAccountDTO cuenta) throws AccountNtFoundException {
        Optional<Account> optionalAccount = cuentaRepo.findById(cuenta.id());

        if (optionalAccount.isEmpty()) {
            throw new AccountNtFoundException(cuenta.id());
        }

        Account cuentaModificada = optionalAccount.get();
        cuentaModificada.getUser().setName(cuenta.username());
        cuentaModificada.getUser().setPhoneNumber(cuenta.phoneNumber());
        cuentaModificada.getUser().setAddress(cuenta.address());
        cuentaModificada.setPassword(cuenta.password());
        //Segmento de codigo que se encarga de encriptar la clave.
        if (cuenta.password() != null && !cuenta.password().isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = bCryptPasswordEncoder.encode(cuenta.password());
            cuentaModificada.setPassword(hashedPassword);
        }
        cuentaRepo.save(cuentaModificada);
        return cuentaModificada.getId();
    }

    /**
     * Metodo encargado de obtener la informacion de la cuenta.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public dtoAccountInformation obtainAccountInformation(String id) throws AccountNotFoundException {
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);
        if (optionalCuenta.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        Account account = optionalCuenta.get();

        return new dtoAccountInformation(
                account.getId(),
                account.getUser().getIdNumber(),
                account.getUser().getPhoneNumber(),
                account.getUser().getAddress(),
                account.getEmail()
        );
    }

    /**
     * Metodo para listar cuentas.
     *
     * @return
     */
    @Override
    public List<dtoAccountItem> listAccounts() {
        try {
            List<Account> cuentas = cuentaRepo.findAll();
            if (cuentas.isEmpty()) {
                throw new NoAccountsFoundException();
            }
            List<dtoAccountItem> items = new ArrayList<>();

            for (Account account : cuentas) {
                items.add(new dtoAccountItem(
                        account.getId(),
                        account.getUser().getName(),
                        account.getEmail(),
                        account.getUser().getPhoneNumber()
                ));
            }
            return items;
        } catch (Exception e) {
            throw new AccountRetrievalException(e.getMessage());
        }
    }

    /**
     * Metodo para eliminarCuenta.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteAccount(String id) throws AccountNotFoundException {
        //Buscamos la cuenta del usuario que se quiere eliminar
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);
        if (optionalCuenta.isEmpty()) {
            throw new AccountNotFoundException(id);
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
    public String cambiarPassword(changePasswordDTO changePasswordDTO) throws Exception {
        return "";
    }

    @Override
    public String iniciarSesion(LoginDTO loginDTO) throws Exception {
        return "";
    }


}
